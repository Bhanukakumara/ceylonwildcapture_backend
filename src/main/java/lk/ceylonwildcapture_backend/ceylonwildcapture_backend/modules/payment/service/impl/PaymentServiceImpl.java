package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.OrderStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.Order;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.Payment;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.repository.OrderRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.*;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentProvider;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.repository.PaymentRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Value("${stripe.return-url}")
    private String stripeReturnUrl;

    @Value("${stripe.cancel-url}")
    private String stripeCancelUrl;

    @Override
    @Transactional
    public PaymentIntentResponseDto createPaymentIntent(CreatePaymentIntentRequestDto requestDto, Long userId) {
        Stripe.apiKey = stripeSecretKey;

        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(stripeReturnUrl + "?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl(stripeCancelUrl)
                    .setCustomerEmail(order.getBillingEmail())
                    .setClientReferenceId(order.getId().toString())
                    .addAllLineItem(order.getOrderItems().stream()
                            .map(item -> SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency(requestDto.getCurrency() != null
                                                    ? requestDto.getCurrency().toLowerCase()
                                                    : "usd")
                                            .setUnitAmount(item.getPrice().multiply(new BigDecimal(100)).longValue())
                                            .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                    .setName(item.getPhoto().getTitle())
                                                    .build())
                                            .build())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();

            Session session = Session.create(params);

            Payment payment = Payment.builder()
                    .order(order)
                    .paymentMethod("CARD")
                    .paymentProvider(PaymentProvider.STRIPE.name())
                    .paymentId(session.getId())
                    .amount(order.getTotalAmount())
                    .currency(requestDto.getCurrency() != null ? requestDto.getCurrency() : "USD")
                    .status(PaymentStatus.PENDING.name())
                    .build();

            paymentRepository.save(payment);

            return PaymentIntentResponseDto.builder()
                    .paymentId(payment.getId())
                    .providerPaymentId(session.getId())
                    .provider(PaymentProvider.STRIPE)
                    .amount(payment.getAmount())
                    .currency(payment.getCurrency())
                    .status(PaymentStatus.PENDING)
                    .redirectUrl(session.getUrl())
                    .orderId(order.getId())
                    .orderNumber(order.getOrderNumber())
                    .build();

        } catch (StripeException e) {
            throw new RuntimeException("Stripe error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Payment attachOrderToPayment(Long paymentId, Long orderId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        payment.setOrder(order);
        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public PaymentResponseDto markPaymentSuccess(String paymentId, String transactionId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.SUCCESS.name());
        payment.setTransactionId(transactionId);
        payment.setPaidAt(LocalDateTime.now());
        paymentRepository.save(payment);

        Order order = payment.getOrder();
        order.setStatus(OrderStatus.COMPLETED);
        order.setPaymentId(paymentId);
        order.setTransactionId(transactionId);
        order.setCompletedAt(LocalDateTime.now());
        orderRepository.save(order);

        return mapToResponseDto(payment);
    }

    @Override
    @Transactional
    public PaymentResponseDto markPaymentFailed(String paymentId, String errorMessage) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.FAILED.name());
        payment.setErrorMessage(errorMessage);
        paymentRepository.save(payment);

        Order order = payment.getOrder();
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        return mapToResponseDto(payment);
    }

    @Override
    @Transactional
    public PaymentResponseDto updatePaymentStatus(String paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(status.name());
        return mapToResponseDto(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponseDto getPaymentById(Long paymentId, Long userId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return mapToResponseDto(payment);
    }

    @Override
    public PaymentResponseDto getPaymentByProviderPaymentId(String providerPaymentId) {
        Payment payment = paymentRepository.findByPaymentId(providerPaymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return mapToResponseDto(payment);
    }

    @Override
    public PaymentResponseDto getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return mapToResponseDto(payment);
    }

    @Override
    public Page<PaymentResponseDto> getUserPayments(Long userId, Pageable pageable) {
        return paymentRepository.findByBuyerId(userId, pageable).map(this::mapToResponseDto);
    }

    @Override
    public Page<PaymentResponseDto> getPaymentsByStatus(PaymentStatus status, Pageable pageable) {
        return paymentRepository.findByStatus(status.name(), pageable).map(this::mapToResponseDto);
    }

    @Override
    public Page<PaymentResponseDto> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate,
            Pageable pageable) {
        return paymentRepository.findByCreatedAtBetween(startDate, endDate, pageable).map(this::mapToResponseDto);
    }

    @Override
    @Transactional
    public PaymentResponseDto processRefund(RefundRequestDto refundRequest) {
        Payment payment = paymentRepository.findById(refundRequest.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(PaymentStatus.REFUNDED.name());
        payment.setRefundAmount(refundRequest.getAmount());
        payment.setRefundReason(refundRequest.getReason());
        payment.setRefundedAt(LocalDateTime.now());

        Order order = payment.getOrder();
        order.setStatus(OrderStatus.REFUNDED);
        order.setRefundedAt(LocalDateTime.now());
        orderRepository.save(order);

        return mapToResponseDto(paymentRepository.save(payment));
    }

    @Override
    @Transactional
    public PaymentResponseDto cancelPayment(Long paymentId, Long userId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(PaymentStatus.CANCELLED.name());

        Order order = payment.getOrder();
        order.setStatus(OrderStatus.CANCELLED);
        order.setCancelledAt(LocalDateTime.now());
        orderRepository.save(order);

        return mapToResponseDto(paymentRepository.save(payment));
    }

    @Override
    public Payment getPaymentEntity(Long paymentId) {
        return paymentRepository.findById(paymentId).orElse(null);
    }

    @Override
    public Optional<Payment> findByProviderPaymentId(String providerPaymentId) {
        return paymentRepository.findByPaymentId(providerPaymentId);
    }

    @Override
    public Page<PaymentResponseDto> searchPayments(String searchTerm, Pageable pageable) {
        return paymentRepository.searchPayments(searchTerm, pageable).map(this::mapToResponseDto);
    }

    @Override
    public long countUserPayments(Long userId) {
        return paymentRepository.countByBuyerId(userId);
    }

    private PaymentResponseDto mapToResponseDto(Payment payment) {
        return PaymentResponseDto.builder()
                .id(payment.getId())
                .orderId(payment.getOrder().getId())
                .orderNumber(payment.getOrder().getOrderNumber())
                .paymentMethod(payment.getPaymentMethod())
                .paymentProvider(
                        payment.getPaymentProvider() != null ? PaymentProvider.valueOf(payment.getPaymentProvider())
                                : null)
                .transactionId(payment.getTransactionId())
                .paymentId(payment.getPaymentId())
                .amount(payment.getAmount())
                .fee(payment.getFee())
                .netAmount(payment.getNetAmount())
                .currency(payment.getCurrency())
                .status(PaymentStatus.valueOf(payment.getStatus()))
                .payerEmail(payment.getPayerEmail())
                .payerName(payment.getPayerName())
                .cardLastFour(payment.getCardLastFour())
                .cardBrand(payment.getCardBrand())
                .errorMessage(payment.getErrorMessage())
                .paidAt(payment.getPaidAt())
                .refundedAt(payment.getRefundedAt())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
