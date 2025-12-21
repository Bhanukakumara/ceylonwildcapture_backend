package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.OrderStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderSearchCriteria;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Service interface for order query and reporting operations.
 */
public interface OrderQueryService {

    /**
     * Get all orders with pagination.
     *
     * @param pageable pagination parameters
     * @return page of order summaries
     */
    Page<OrderSummaryDto> getAllOrders(Pageable pageable);

    /**
     * Get order summaries by status.
     *
     * @param status   order status
     * @param pageable pagination parameters
     * @return page of order summaries
     */
    Page<OrderSummaryDto> getOrdersByStatusSummary(OrderStatus status, Pageable pageable);

    /**
     * Get orders by status.
     *
     * @param status   order status
     * @param pageable pagination parameters
     * @return page of orders
     */
    Page<OrderResponseDto> getOrdersByStatus(OrderStatus status, Pageable pageable);

    /**
     * Get orders by buyer.
     *
     * @param buyerId  buyer ID
     * @param pageable pagination parameters
     * @return page of orders
     */
    Page<OrderResponseDto> getOrdersByBuyer(Long buyerId, Pageable pageable);

    /**
     * Get orders by photographer.
     *
     * @param photographerId photographer ID
     * @param pageable       pagination parameters
     * @return page of orders
     */
    Page<OrderResponseDto> getOrdersByPhotographer(Long photographerId, Pageable pageable);

    /**
     * Get orders by photo.
     *
     * @param photoId  photo ID
     * @param pageable pagination parameters
     * @return page of orders
     */
    Page<OrderResponseDto> getOrdersByPhoto(Long photoId, Pageable pageable);

    /**
     * Get orders by date range.
     *
     * @param startDate start date
     * @param endDate   end date
     * @param pageable  pagination parameters
     * @return page of orders
     */
    Page<OrderResponseDto> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Search orders with criteria.
     *
     * @param criteria search criteria
     * @param pageable pagination parameters
     * @return page of orders
     */
    Page<OrderResponseDto> searchOrders(OrderSearchCriteria criteria, Pageable pageable);

    /**
     * Get recent orders.
     *
     * @param pageable pagination parameters
     * @return page of recent orders
     */
    Page<OrderSummaryDto> getRecentOrders(Pageable pageable);

    /**
     * Get order statistics.
     *
     * @return order statistics map
     */
    Map<String, Object> getOrderStatistics();

    /**
     * Get total sales amount.
     *
     * @return total sales amount
     */
    BigDecimal getTotalSales();

    /**
     * Get buyer statistics.
     *
     * @param buyerId buyer ID
     * @return buyer statistics map
     */
    Map<String, Object> getBuyerStatistics(Long buyerId);

    /**
     * Get top buyers by order count.
     *
     * @param pageable pagination parameters
     * @return page of top buyers
     */
    Page<Map<String, Object>> getTopBuyersByOrderCount(Pageable pageable);

    /**
     * Get top buyers by spending.
     *
     * @param pageable pagination parameters
     * @return page of top buyers
     */
    Page<Map<String, Object>> getTopBuyersBySpending(Pageable pageable);

    /**
     * Count orders by status.
     *
     * @param status order status
     * @return order count
     */
    long countOrdersByStatus(OrderStatus status);

    /**
     * Count total orders.
     *
     * @return total order count
     */
    long countTotalOrders();
}
