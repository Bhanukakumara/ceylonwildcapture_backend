package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.controller;

import jakarta.validation.Valid;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.OrderStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.*;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * REST controller for order management operations.
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Create a new order.
     *
     * @param requestDto order creation request
     * @param userId     authenticated user ID
     * @return created order response
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<OrderResponseDto> createOrder(
            @Valid @RequestBody CreateOrderRequestDto requestDto,
            @RequestAttribute("userId") Long userId) {
        OrderResponseDto response = orderService.createOrderFromRequest(requestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get order by ID.
     *
     * @param orderId order ID
     * @param userId  authenticated user ID
     * @return order response
     */
    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<OrderResponseDto> getOrder(
            @PathVariable Long orderId,
            @RequestAttribute("userId") Long userId) {
        OrderResponseDto response = orderService.getOrderDtoById(orderId, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get order by order number.
     *
     * @param orderNumber order number
     * @param userId      authenticated user ID
     * @return order response
     */
    @GetMapping("/number/{orderNumber}")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<OrderResponseDto> getOrderByNumber(
            @PathVariable String orderNumber,
            @RequestAttribute("userId") Long userId) {
        OrderResponseDto response = orderService.getOrderDtoByNumber(orderNumber, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get current user's orders.
     *
     * @param userId   authenticated user ID
     * @param pageable pagination parameters
     * @return page of order summaries
     */
    @GetMapping("/my-orders")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<Page<OrderSummaryDto>> getMyOrders(
            @RequestAttribute("userId") Long userId,
            Pageable pageable) {
        Page<OrderSummaryDto> orders = orderService.getUserOrders(userId, pageable);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get current user's orders by status.
     *
     * @param userId   authenticated user ID
     * @param status   order status
     * @param pageable pagination parameters
     * @return page of orders
     */
    @GetMapping("/my-orders/status/{status}")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<Page<OrderResponseDto>> getMyOrdersByStatus(
            @RequestAttribute("userId") Long userId,
            @PathVariable OrderStatus status,
            Pageable pageable) {
        Page<OrderResponseDto> orders = orderService.getUserOrdersByStatus(userId, status, pageable);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get current user's completed orders.
     *
     * @param userId   authenticated user ID
     * @param pageable pagination parameters
     * @return page of completed orders
     */
    @GetMapping("/my-orders/completed")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<Page<OrderResponseDto>> getMyCompletedOrders(
            @RequestAttribute("userId") Long userId,
            Pageable pageable) {
        Page<OrderResponseDto> orders = orderService.getUserCompletedOrders(userId, pageable);
        return ResponseEntity.ok(orders);
    }

    /**
     * Update order status.
     *
     * @param orderId   order ID
     * @param statusDto status update request
     * @return updated order
     */
    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequestDto statusDto) {
        OrderResponseDto response = orderService.updateStatus(orderId, statusDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Cancel an order.
     *
     * @param orderId order ID
     * @param userId  authenticated user ID
     * @return cancelled order
     */
    @PostMapping("/{orderId}/cancel")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<OrderResponseDto> cancelOrder(
            @PathVariable Long orderId,
            @RequestAttribute("userId") Long userId) {
        OrderResponseDto response = orderService.cancel(orderId, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Process payment success callback.
     *
     * @param orderId       order ID
     * @param paymentId     payment ID
     * @param transactionId transaction ID
     * @return updated order
     */
    @PostMapping("/{orderId}/payment-success")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER')")
    public ResponseEntity<OrderResponseDto> processPaymentSuccess(
            @PathVariable Long orderId,
            @RequestParam String paymentId,
            @RequestParam String transactionId) {
        OrderResponseDto response = orderService.handlePaymentSuccess(orderId, paymentId, transactionId);
        return ResponseEntity.ok(response);
    }

    /**
     * Process payment failure callback.
     *
     * @param orderId order ID
     * @param reason  failure reason
     * @return updated order
     */
    @PostMapping("/{orderId}/payment-failure")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER')")
    public ResponseEntity<OrderResponseDto> processPaymentFailure(
            @PathVariable Long orderId,
            @RequestParam String reason) {
        OrderResponseDto response = orderService.handlePaymentFailure(orderId, reason);
        return ResponseEntity.ok(response);
    }

    /**
     * Search orders (admin only).
     *
     * @param criteria search criteria
     * @param pageable pagination parameters
     * @return page of orders
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderResponseDto>> searchOrders(
            @ModelAttribute OrderSearchCriteria criteria,
            Pageable pageable) {
        Page<OrderResponseDto> orders = orderService.searchOrders(criteria, pageable);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get orders by date range.
     *
     * @param userId    authenticated user ID
     * @param startDate start date
     * @param endDate   end date
     * @param pageable  pagination parameters
     * @return page of orders
     */
    @GetMapping("/my-orders/date-range")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<Page<OrderResponseDto>> getOrdersByDateRange(
            @RequestAttribute("userId") Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable) {
        Page<OrderResponseDto> orders = orderService.getUserOrdersByDateRange(userId, startDate, endDate, pageable);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get order count for current user.
     *
     * @param userId authenticated user ID
     * @return order count
     */
    @GetMapping("/my-orders/count")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<Long> getMyOrderCount(@RequestAttribute("userId") Long userId) {
        long count = orderService.countUserOrders(userId);
        return ResponseEntity.ok(count);
    }

    /**
     * Validate order before payment.
     *
     * @param orderId order ID
     * @param userId  authenticated user ID
     * @return validation result
     */
    @GetMapping("/{orderId}/validate")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER')")
    public ResponseEntity<Boolean> validateOrder(
            @PathVariable Long orderId,
            @RequestAttribute("userId") Long userId) {
        boolean isValid = orderService.validateForPayment(orderId, userId);
        return ResponseEntity.ok(isValid);
    }
}
