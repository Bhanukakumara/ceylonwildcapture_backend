package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service.impl;

import jakarta.validation.Valid;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.OrderStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.*;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.Order;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.OrderItem;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.repository.OrderItemRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.repository.OrderRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service.OrderService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Photo;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.repository.PhotoRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;

    @Override
    public Order createOrder(Order order) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Order createOrderFromCart(Long buyerId, List<Long> cartItemIds, Object billingInfo) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Order createOrderWithItems(Long buyerId, List<Object> orderItems, Object billingInfo) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Order updateOrder(Long orderId, Order order) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public Optional<Order> getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    @Override
    public Optional<Order> getOrderWithBuyer(Long orderId) {
        return orderRepository.findByIdWithBuyer(orderId);
    }

    @Override
    public Optional<Order> getOrderWithItems(Long orderId) {
        return orderRepository.findByIdWithOrderItems(orderId);
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> getOrdersByBuyer(Long buyerId, Pageable pageable) {
        return orderRepository.findByBuyerId(buyerId, pageable);
    }

    @Override
    public Page<Order> getOrdersByBuyerAndStatus(Long buyerId, OrderStatus status, Pageable pageable) {
        return orderRepository.findByBuyerIdAndStatus(buyerId, status, pageable);
    }

    @Override
    public Page<Order> getCompletedOrdersByBuyer(Long buyerId, Pageable pageable) {
        return orderRepository.findCompletedOrdersByBuyer(buyerId, OrderStatus.COMPLETED, pageable);
    }

    @Override
    public List<Order> getPendingOrdersByBuyer(Long buyerId) {
        return orderRepository.findByBuyerIdAndStatus(buyerId, OrderStatus.PENDING);
    }

    @Override
    public Page<Order> getOrdersByStatus(OrderStatus status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable);
    }

    @Override
    public Page<Order> getRecentOrders(Pageable pageable) {
        return orderRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Override
    public Page<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return orderRepository.findByCreatedAtBetween(startDate, endDate, pageable);
    }

    @Override
    public Page<Order> getOrdersByBuyerAndDateRange(Long buyerId, LocalDateTime startDate, LocalDateTime endDate,
            Pageable pageable) {
        return orderRepository.findOrdersByBuyerAndDateRange(buyerId, startDate, endDate, pageable);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order cancelOrder(Long orderId, String reason) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Order markOrderAsPaid(Long orderId, String paymentId, String transactionId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Order markOrderAsFailed(Long orderId, String reason) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Order refundOrder(Long orderId, BigDecimal refundAmount, String reason) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public BigDecimal calculateOrderTotal(List<Object> orderItems, String couponCode) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Order applyCoupon(Long orderId, String couponCode) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Object validateOrder(Long orderId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public long countOrdersByBuyer(Long buyerId) {
        return orderRepository.countByBuyerId(buyerId);
    }

    @Override
    public long countCompletedOrdersByBuyer(Long buyerId) {
        return orderRepository.countByBuyerIdAndStatus(buyerId, OrderStatus.COMPLETED);
    }

    @Override
    public long countOrdersByStatus(OrderStatus status) {
        return orderRepository.countByStatus(status);
    }

    @Override
    public BigDecimal calculateTotalSales() {
        return orderRepository.calculateTotalSales(OrderStatus.COMPLETED);
    }

    @Override
    public BigDecimal calculateTotalSpentByBuyer(Long buyerId) {
        return orderRepository.calculateTotalSpentByBuyer(buyerId, OrderStatus.COMPLETED);
    }

    @Override
    public Page<Object[]> getTopBuyersByOrderCount(Pageable pageable) {
        return orderRepository.findTopBuyersByOrderCount(OrderStatus.COMPLETED, pageable);
    }

    @Override
    public Page<Object[]> getTopBuyersBySpending(Pageable pageable) {
        return orderRepository.findTopBuyersBySpending(OrderStatus.COMPLETED, pageable);
    }

    @Override
    public boolean canBuyerPurchasePhoto(Long buyerId, Long photoId) {
        return !orderItemRepository.existsByBuyerIdAndPhotoId(buyerId, photoId);
    }

    @Override
    public Page<Order> getOrdersByPhoto(Long photoId, Pageable pageable) {
        return orderRepository.findOrdersByPhotoId(photoId, pageable);
    }

    @Override
    public Order processOrderPayment(Long orderId, Object paymentDetails) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public OrderResponseDto createOrderFromRequest(@Valid CreateOrderRequestDto requestDto, Long userId) {
        log.info("Creating order for user ID: {}", userId);

        User buyer = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String orderNumber = generateOrderNumber();

        BigDecimal subtotal = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        Order order = Order.builder()
                .orderNumber(orderNumber)
                .buyer(buyer)
                .status(OrderStatus.PENDING)
                .billingName(requestDto.getBillingInfo().getBillingName())
                .billingEmail(requestDto.getBillingInfo().getBillingEmail())
                .billingAddress(requestDto.getBillingInfo().getBillingAddress())
                .billingCity(requestDto.getBillingInfo().getBillingCity())
                .billingState(requestDto.getBillingInfo().getBillingState())
                .billingCountry(requestDto.getBillingInfo().getBillingCountry())
                .billingZip(requestDto.getBillingInfo().getBillingZip())
                .paymentMethod("STRIPE")
                .build();

        for (OrderItemRequestDto itemReq : requestDto.getItems()) {
            Photo photo = photoRepository.findById(itemReq.getPhotoId())
                    .orElseThrow(() -> new RuntimeException("Photo not found: " + itemReq.getPhotoId()));

            BigDecimal price = photo.getBasePrice();
            subtotal = subtotal.add(price);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .photo(photo)
                    .licenseType(itemReq.getLicenseType())
                    .price(price)
                    .finalPrice(price)
                    .photographerEarnings(price.multiply(new BigDecimal("0.8")))
                    .platformCommission(price.multiply(new BigDecimal("0.2")))
                    .build();
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setSubtotal(subtotal);
        order.setTaxAmount(subtotal.multiply(new BigDecimal("0.1")));
        order.setTotalAmount(order.getSubtotal().add(order.getTaxAmount()));

        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully: {}", savedOrder.getOrderNumber());

        return mapToResponseDto(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderDtoById(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToResponseDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderDtoByNumber(String orderNumber, Long userId) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToResponseDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderSummaryDto> getUserOrders(Long userId, Pageable pageable) {
        Page<Order> orders = orderRepository.findByBuyerId(userId, pageable);
        return orders.map(this::mapToSummaryDto);
    }

    @Override
    public Page<OrderResponseDto> getUserOrdersByStatus(Long userId, OrderStatus status, Pageable pageable) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Page<OrderResponseDto> getUserCompletedOrders(Long userId, Pageable pageable) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public OrderResponseDto updateStatus(Long orderId, @Valid UpdateOrderStatusRequestDto statusDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public OrderResponseDto cancel(Long orderId, Long userId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public OrderResponseDto handlePaymentSuccess(Long orderId, String paymentId, String transactionId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.COMPLETED);
        order.setPaymentId(paymentId);
        order.setTransactionId(transactionId);
        order.setCompletedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);
        return mapToResponseDto(savedOrder);
    }

    @Override
    public OrderResponseDto handlePaymentFailure(Long orderId, String reason) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Page<OrderResponseDto> searchOrders(OrderSearchCriteria criteria, Pageable pageable) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Page<OrderResponseDto> getUserOrdersByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate,
            Pageable pageable) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public long countUserOrders(Long userId) {
        return orderRepository.countByBuyerId(userId);
    }

    @Override
    public boolean validateForPayment(Long orderId, Long userId) {
        return true;
    }

    private OrderResponseDto mapToResponseDto(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .buyerId(order.getBuyer().getId())
                .buyerName(order.getBuyer().getFirstName() + " " + order.getBuyer().getLastName())
                .buyerEmail(order.getBuyer().getEmail())
                .totalAmount(order.getTotalAmount())
                .subtotal(order.getSubtotal())
                .taxAmount(order.getTaxAmount())
                .status(order.getStatus())
                .paymentMethod(order.getPaymentMethod())
                .paymentId(order.getPaymentId())
                .transactionId(order.getTransactionId())
                .billingInfo(mapToBillingInfoDto(order))
                .createdAt(order.getCreatedAt())
                .itemCount(order.getOrderItems().size())
                .items(order.getOrderItems().stream().map(this::mapToOrderItemResponseDto).collect(Collectors.toList()))
                .build();
    }

    private BillingInfoDto mapToBillingInfoDto(Order order) {
        return BillingInfoDto.builder()
                .billingName(order.getBillingName())
                .billingEmail(order.getBillingEmail())
                .billingAddress(order.getBillingAddress())
                .billingCity(order.getBillingCity())
                .billingState(order.getBillingState())
                .billingCountry(order.getBillingCountry())
                .billingZip(order.getBillingZip())
                .build();
    }

    private OrderItemResponseDto mapToOrderItemResponseDto(OrderItem item) {
        OrderItemResponseDto.OrderItemResponseDtoBuilder builder = OrderItemResponseDto.builder()
                .id(item.getId())
                .photoId(item.getPhoto().getId())
                .photoTitle(item.getPhoto().getTitle())
                .photoThumbnailUrl(item.getPhoto().getThumbnailUrl())
                .photographerId(item.getPhoto().getPhotographer().getId())
                .photographerName(item.getPhoto().getPhotographer().getFirstName() + " "
                        + item.getPhoto().getPhotographer().getLastName())
                .licenseType(item.getLicenseType())
                .price(item.getPrice())
                .finalPrice(item.getFinalPrice());

        if (item.getOrder().getStatus() == OrderStatus.COMPLETED) {
            builder.photoOriginalUrl(item.getPhoto().getImageUrl());
        }

        return builder.build();
    }

    private OrderSummaryDto mapToSummaryDto(Order order) {
        String firstPhotoThumbnail = null;
        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            firstPhotoThumbnail = order.getOrderItems().get(0).getPhoto().getThumbnailUrl();
        }

        return OrderSummaryDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .buyerName(order.getBillingName())
                .buyerEmail(order.getBillingEmail())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .itemCount(order.getOrderItems() != null ? order.getOrderItems().size() : 0)
                .paymentMethod(order.getPaymentMethod())
                .transactionId(order.getTransactionId())
                .firstPhotoThumbnail(firstPhotoThumbnail)
                .createdAt(order.getCreatedAt())
                .build();
    }
}
