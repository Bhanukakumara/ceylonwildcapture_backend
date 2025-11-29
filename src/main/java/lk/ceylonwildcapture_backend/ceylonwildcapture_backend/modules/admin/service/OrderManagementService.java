package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.OrderManagementDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Service interface for admin order management operations.
 */
public interface OrderManagementService {

    /**
     * Manage an order (cancel, refund, etc.).
     *
     * @param managementDto order management details
     * @param adminId ID of the admin
     * @return updated order
     */
    Order manageOrder(OrderManagementDto managementDto, Long adminId);

    /**
     * Cancel an order.
     *
     * @param orderId ID of the order
     * @param adminId ID of the admin
     * @return cancelled order
     */
    Order cancelOrder(Long orderId, Long adminId);

    /**
     * Process full refund.
     *
     * @param orderId ID of the order
     * @param reason refund reason
     * @param adminId ID of the admin
     * @return refunded order
     */
    Order processFullRefund(Long orderId, String reason, Long adminId);

    /**
     * Process partial refund.
     *
     * @param orderId ID of the order
     * @param amount refund amount
     * @param reason refund reason
     * @param adminId ID of the admin
     * @return refunded order
     */
    Order processPartialRefund(Long orderId, BigDecimal amount, String reason, Long adminId);

    /**
     * Mark order as completed.
     *
     * @param orderId ID of the order
     * @param adminId ID of the admin
     * @return completed order
     */
    Order markAsCompleted(Long orderId, Long adminId);

    /**
     * Mark order as disputed.
     *
     * @param orderId ID of the order
     * @param adminId ID of the admin
     * @return disputed order
     */
    Order markAsDisputed(Long orderId, Long adminId);

    /**
     * Resolve a dispute.
     *
     * @param orderId ID of the order
     * @param resolution resolution notes
     * @param adminId ID of the admin
     * @return resolved order
     */
    Order resolveDispute(Long orderId, String resolution, Long adminId);

    /**
     * Get all orders with pagination.
     *
     * @param pageable pagination parameters
     * @return page of orders
     */
    Page<Order> getAllOrders(Pageable pageable);

    /**
     * Get orders by status.
     *
     * @param status order status
     * @param pageable pagination parameters
     * @return page of orders
     */
    Page<Order> getOrdersByStatus(String status, Pageable pageable);

    /**
     * Get orders by date range.
     *
     * @param startDate start date
     * @param endDate end date
     * @param pageable pagination parameters
     * @return page of orders
     */
    Page<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Get disputed orders.
     *
     * @param pageable pagination parameters
     * @return page of disputed orders
     */
    Page<Order> getDisputedOrders(Pageable pageable);

    /**
     * Get refunded orders.
     *
     * @param pageable pagination parameters
     * @return page of refunded orders
     */
    Page<Order> getRefundedOrders(Pageable pageable);

    /**
     * Get orders for a specific customer.
     *
     * @param customerId ID of the customer
     * @param pageable pagination parameters
     * @return page of customer's orders
     */
    Page<Order> getCustomerOrders(Long customerId, Pageable pageable);

    /**
     * Get order details.
     *
     * @param orderId ID of the order
     * @return order details
     */
    Order getOrderDetails(Long orderId);

    /**
     * Search orders.
     *
     * @param searchTerm search term
     * @param pageable pagination parameters
     * @return page of orders
     */
    Page<Order> searchOrders(String searchTerm, Pageable pageable);
}
