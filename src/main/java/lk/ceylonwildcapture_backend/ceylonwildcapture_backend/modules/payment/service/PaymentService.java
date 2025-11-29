package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.Payment;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.*;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service interface for payment operations.
 */
public interface PaymentService {

    /**
     * Create a payment intent.
     *
     * @param requestDto payment intent request
     * @param userId user ID
     * @return payment intent response
     */
    PaymentIntentResponseDto createPaymentIntent(CreatePaymentIntentRequestDto requestDto, Long userId);

    /**
     * Attach order to payment.
     *
     * @param paymentId payment ID
     * @param orderId order ID
     * @return updated payment
     */
    Payment attachOrderToPayment(Long paymentId, Long orderId);

    /**
     * Mark payment as success.
     *
     * @param paymentId payment ID
     * @param transactionId transaction ID
     * @return updated payment response
     */
    PaymentResponseDto markPaymentSuccess(String paymentId, String transactionId);

    /**
     * Mark payment as failed.
     *
     * @param paymentId payment ID
     * @param errorMessage error message
     * @return updated payment response
     */
    PaymentResponseDto markPaymentFailed(String paymentId, String errorMessage);

    /**
     * Update payment status.
     *
     * @param paymentId payment ID
     * @param status new status
     * @return updated payment response
     */
    PaymentResponseDto updatePaymentStatus(String paymentId, PaymentStatus status);

    /**
     * Get payment by ID.
     *
     * @param paymentId payment ID
     * @param userId requesting user ID
     * @return payment response
     */
    PaymentResponseDto getPaymentById(Long paymentId, Long userId);

    /**
     * Get payment by provider payment ID.
     *
     * @param providerPaymentId provider payment ID
     * @return payment response
     */
    PaymentResponseDto getPaymentByProviderPaymentId(String providerPaymentId);

    /**
     * Get payment by order ID.
     *
     * @param orderId order ID
     * @return payment response
     */
    PaymentResponseDto getPaymentByOrderId(Long orderId);

    /**
     * Get user payments.
     *
     * @param userId user ID
     * @param pageable pagination parameters
     * @return page of payment responses
     */
    Page<PaymentResponseDto> getUserPayments(Long userId, Pageable pageable);

    /**
     * Get payments by status.
     *
     * @param status payment status
     * @param pageable pagination parameters
     * @return page of payment responses
     */
    Page<PaymentResponseDto> getPaymentsByStatus(PaymentStatus status, Pageable pageable);

    /**
     * Get payments by date range.
     *
     * @param startDate start date
     * @param endDate end date
     * @param pageable pagination parameters
     * @return page of payment responses
     */
    Page<PaymentResponseDto> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Process refund.
     *
     * @param refundRequest refund request
     * @return updated payment response
     */
    PaymentResponseDto processRefund(RefundRequestDto refundRequest);

    /**
     * Cancel payment.
     *
     * @param paymentId payment ID
     * @param userId user ID
     * @return updated payment response
     */
    PaymentResponseDto cancelPayment(Long paymentId, Long userId);

    /**
     * Get payment entity (for internal use).
     *
     * @param paymentId payment ID
     * @return payment entity
     */
    Payment getPaymentEntity(Long paymentId);

    /**
     * Find payment by provider payment ID (for internal use).
     *
     * @param providerPaymentId provider payment ID
     * @return optional payment entity
     */
    Optional<Payment> findByProviderPaymentId(String providerPaymentId);

    /**
     * Search payments.
     *
     * @param searchTerm search term
     * @param pageable pagination parameters
     * @return page of payment responses
     */
    Page<PaymentResponseDto> searchPayments(String searchTerm, Pageable pageable);

    /**
     * Count user payments.
     *
     * @param userId user ID
     * @return payment count
     */
    long countUserPayments(Long userId);
}
