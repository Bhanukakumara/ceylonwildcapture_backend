package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.OrderStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Order management operations.
 * Defines business logic for order creation, status updates, payment processing,
 * and buyer order history.
 */
public interface OrderService {

    /**
     * Create a new order.
     *
     * @param order the order entity to create
     * @return the created order
     * @throws IllegalArgumentException if order data is invalid
     */
    Order createOrder(Order order);

    /**
     * Create order from cart items.
     *
     * @param buyerId the buyer ID
     * @param cartItemIds the list of cart item IDs
     * @param billingInfo the billing information (DTO placeholder)
     * @return the created order
     * @throws IllegalArgumentException if cart items invalid or buyer not found
     */
    Order createOrderFromCart(Long buyerId, List<Long> cartItemIds, Object billingInfo);

    /**
     * Create order with items.
     *
     * @param buyerId the buyer ID
     * @param orderItems the list of order items (DTO placeholder)
     * @param billingInfo the billing information (DTO placeholder)
     * @return the created order
     * @throws IllegalArgumentException if data is invalid
     */
    Order createOrderWithItems(Long buyerId, List<Object> orderItems, Object billingInfo);

    /**
     * Update order.
     *
     * @param orderId the order ID
     * @param order the updated order data
     * @return the updated order
     * @throws IllegalArgumentException if order not found
     */
    Order updateOrder(Long orderId, Order order);

    /**
     * Update order status.
     *
     * @param orderId the order ID
     * @param status the new order status
     * @return the updated order
     * @throws IllegalArgumentException if order not found or status transition invalid
     */
    Order updateOrderStatus(Long orderId, OrderStatus status);

    /**
     * Get order by ID.
     *
     * @param orderId the order ID
     * @return Optional containing the order if found
     */
    Optional<Order> getOrderById(Long orderId);

    /**
     * Get order by order number.
     *
     * @param orderNumber the order number
     * @return Optional containing the order if found
     */
    Optional<Order> getOrderByOrderNumber(String orderNumber);

    /**
     * Get order with buyer details.
     *
     * @param orderId the order ID
     * @return Optional containing the order if found
     */
    Optional<Order> getOrderWithBuyer(Long orderId);

    /**
     * Get order with order items.
     *
     * @param orderId the order ID
     * @return Optional containing the order if found
     */
    Optional<Order> getOrderWithItems(Long orderId);

    /**
     * Get all orders with pagination.
     *
     * @param pageable pagination information
     * @return page of orders
     */
    Page<Order> getAllOrders(Pageable pageable);

    /**
     * Get orders by buyer.
     *
     * @param buyerId the buyer ID
     * @param pageable pagination information
     * @return page of buyer's orders
     */
    Page<Order> getOrdersByBuyer(Long buyerId, Pageable pageable);

    /**
     * Get orders by buyer and status.
     *
     * @param buyerId the buyer ID
     * @param status the order status
     * @param pageable pagination information
     * @return page of orders
     */
    Page<Order> getOrdersByBuyerAndStatus(Long buyerId, OrderStatus status, Pageable pageable);

    /**
     * Get completed orders by buyer.
     *
     * @param buyerId the buyer ID
     * @param pageable pagination information
     * @return page of completed orders
     */
    Page<Order> getCompletedOrdersByBuyer(Long buyerId, Pageable pageable);

    /**
     * Get pending orders by buyer.
     *
     * @param buyerId the buyer ID
     * @return list of pending orders
     */
    List<Order> getPendingOrdersByBuyer(Long buyerId);

    /**
     * Get orders by status.
     *
     * @param status the order status
     * @param pageable pagination information
     * @return page of orders
     */
    Page<Order> getOrdersByStatus(OrderStatus status, Pageable pageable);

    /**
     * Get recent orders.
     *
     * @param pageable pagination information
     * @return page of recent orders
     */
    Page<Order> getRecentOrders(Pageable pageable);

    /**
     * Get orders within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of orders
     */
    Page<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Get orders by buyer within date range.
     *
     * @param buyerId the buyer ID
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of orders
     */
    Page<Order> getOrdersByBuyerAndDateRange(Long buyerId, LocalDateTime startDate,
                                             LocalDateTime endDate, Pageable pageable);

    /**
     * Delete order.
     *
     * @param orderId the order ID
     * @throws IllegalArgumentException if order not found or cannot be deleted
     */
    void deleteOrder(Long orderId);

    /**
     * Cancel order.
     *
     * @param orderId the order ID
     * @param reason the cancellation reason
     * @return the cancelled order
     * @throws IllegalArgumentException if order not found or cannot be cancelled
     */
    Order cancelOrder(Long orderId, String reason);

    /**
     * Mark order as paid/completed.
     *
     * @param orderId the order ID
     * @param paymentId the payment ID
     * @param transactionId the transaction ID
     * @return the completed order
     * @throws IllegalArgumentException if order not found
     */
    Order markOrderAsPaid(Long orderId, String paymentId, String transactionId);

    /**
     * Mark order as failed.
     *
     * @param orderId the order ID
     * @param reason the failure reason
     * @return the failed order
     * @throws IllegalArgumentException if order not found
     */
    Order markOrderAsFailed(Long orderId, String reason);

    /**
     * Process refund for order.
     *
     * @param orderId the order ID
     * @param refundAmount the refund amount
     * @param reason the refund reason
     * @return the refunded order
     * @throws IllegalArgumentException if order not found or cannot be refunded
     */
    Order refundOrder(Long orderId, BigDecimal refundAmount, String reason);

    /**
     * Calculate order total.
     *
     * @param orderItems the list of order items (DTO placeholder)
     * @param couponCode the optional coupon code
     * @return the calculated total amount
     */
    BigDecimal calculateOrderTotal(List<Object> orderItems, String couponCode);

    /**
     * Apply coupon to order.
     *
     * @param orderId the order ID
     * @param couponCode the coupon code
     * @return the updated order
     * @throws IllegalArgumentException if order not found or coupon invalid
     */
    Order applyCoupon(Long orderId, String couponCode);

    /**
     * Validate order before payment.
     *
     * @param orderId the order ID
     * @return validation result with any errors
     */
    Object validateOrder(Long orderId);

    /**
     * Generate unique order number.
     *
     * @return the generated order number
     */
    String generateOrderNumber();

    /**
     * Count orders by buyer.
     *
     * @param buyerId the buyer ID
     * @return count of orders
     */
    long countOrdersByBuyer(Long buyerId);

    /**
     * Count completed orders by buyer.
     *
     * @param buyerId the buyer ID
     * @return count of completed orders
     */
    long countCompletedOrdersByBuyer(Long buyerId);

    /**
     * Count orders by status.
     *
     * @param status the order status
     * @return count of orders
     */
    long countOrdersByStatus(OrderStatus status);

    /**
     * Calculate total sales amount.
     *
     * @return total sales amount for completed orders
     */
    BigDecimal calculateTotalSales();

    /**
     * Calculate total spent by buyer.
     *
     * @param buyerId the buyer ID
     * @return total amount spent
     */
    BigDecimal calculateTotalSpentByBuyer(Long buyerId);

    /**
     * Get top buyers by order count.
     *
     * @param pageable pagination information
     * @return page of top buyers with order counts
     */
    Page<Object[]> getTopBuyersByOrderCount(Pageable pageable);

    /**
     * Get top buyers by spending.
     *
     * @param pageable pagination information
     * @return page of top buyers with total spending
     */
    Page<Object[]> getTopBuyersBySpending(Pageable pageable);

    /**
     * Check if buyer can purchase photo.
     *
     * @param buyerId the buyer ID
     * @param photoId the photo ID
     * @return true if buyer can purchase (doesn't already own license)
     */
    boolean canBuyerPurchasePhoto(Long buyerId, Long photoId);

    /**
     * Get orders containing specific photo.
     *
     * @param photoId the photo ID
     * @param pageable pagination information
     * @return page of orders
     */
    Page<Order> getOrdersByPhoto(Long photoId, Pageable pageable);

    /**
     * Process order after successful payment.
     *
     * @param orderId the order ID
     * @param paymentDetails the payment details (DTO placeholder)
     * @return the processed order
     * @throws IllegalArgumentException if order not found
     */
    Order processOrderPayment(Long orderId, Object paymentDetails);
}
