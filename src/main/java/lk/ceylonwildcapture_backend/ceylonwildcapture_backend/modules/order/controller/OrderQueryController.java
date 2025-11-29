package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.OrderStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderSearchCriteria;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderSummaryDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * REST controller for order query and reporting operations.
 */
@RestController
@RequestMapping("/api/v1/orders/query")
@RequiredArgsConstructor
public class OrderQueryController {

    private final OrderQueryService orderQueryService;

    /**
     * Get all orders (admin only).
     *
     * @param pageable pagination parameters
     * @return page of orders
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderSummaryDto>> getAllOrders(Pageable pageable) {
        Page<OrderSummaryDto> orders = orderQueryService.getAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get orders by status (admin only).
     *
     * @param status order status
     * @param pageable pagination parameters
     * @return page of orders
     */
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderResponseDto>> getOrdersByStatus(
            @PathVariable OrderStatus status,
            Pageable pageable) {
        Page<OrderResponseDto> orders = orderQueryService.getOrdersByStatus(status, pageable);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get orders by buyer (admin only).
     *
     * @param buyerId buyer ID
     * @param pageable pagination parameters
     * @return page of orders
     */
    @GetMapping("/buyer/{buyerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderResponseDto>> getOrdersByBuyer(
            @PathVariable Long buyerId,
            Pageable pageable) {
        Page<OrderResponseDto> orders = orderQueryService.getOrdersByBuyer(buyerId, pageable);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get orders by photographer (admin only).
     *
     * @param photographerId photographer ID
     * @param pageable pagination parameters
     * @return page of orders
     */
    @GetMapping("/photographer/{photographerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderResponseDto>> getOrdersByPhotographer(
            @PathVariable Long photographerId,
            Pageable pageable) {
        Page<OrderResponseDto> orders = orderQueryService.getOrdersByPhotographer(photographerId, pageable);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get orders by photo (admin only).
     *
     * @param photoId photo ID
     * @param pageable pagination parameters
     * @return page of orders
     */
    @GetMapping("/photo/{photoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderResponseDto>> getOrdersByPhoto(
            @PathVariable Long photoId,
            Pageable pageable) {
        Page<OrderResponseDto> orders = orderQueryService.getOrdersByPhoto(photoId, pageable);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get orders by date range (admin only).
     *
     * @param startDate start date
     * @param endDate end date
     * @param pageable pagination parameters
     * @return page of orders
     */
    @GetMapping("/date-range")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderResponseDto>> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable) {
        Page<OrderResponseDto> orders = orderQueryService.getOrdersByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(orders);
    }

    /**
     * Search orders with criteria (admin only).
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
        Page<OrderResponseDto> orders = orderQueryService.searchOrders(criteria, pageable);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get recent orders (admin only).
     *
     * @param pageable pagination parameters
     * @return page of recent orders
     */
    @GetMapping("/recent")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderSummaryDto>> getRecentOrders(Pageable pageable) {
        Page<OrderSummaryDto> orders = orderQueryService.getRecentOrders(pageable);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get order statistics (admin only).
     *
     * @return order statistics map
     */
    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getOrderStatistics() {
        Map<String, Object> statistics = orderQueryService.getOrderStatistics();
        return ResponseEntity.ok(statistics);
    }

    /**
     * Get total sales (admin only).
     *
     * @return total sales amount
     */
    @GetMapping("/total-sales")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BigDecimal> getTotalSales() {
        BigDecimal totalSales = orderQueryService.getTotalSales();
        return ResponseEntity.ok(totalSales);
    }

    /**
     * Get buyer statistics (admin only).
     *
     * @param buyerId buyer ID
     * @return buyer statistics map
     */
    @GetMapping("/buyer/{buyerId}/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getBuyerStatistics(@PathVariable Long buyerId) {
        Map<String, Object> statistics = orderQueryService.getBuyerStatistics(buyerId);
        return ResponseEntity.ok(statistics);
    }

    /**
     * Get top buyers by order count (admin only).
     *
     * @param pageable pagination parameters
     * @return page of top buyers
     */
    @GetMapping("/top-buyers/by-orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Map<String, Object>>> getTopBuyersByOrderCount(Pageable pageable) {
        Page<Map<String, Object>> topBuyers = orderQueryService.getTopBuyersByOrderCount(pageable);
        return ResponseEntity.ok(topBuyers);
    }

    /**
     * Get top buyers by spending (admin only).
     *
     * @param pageable pagination parameters
     * @return page of top buyers
     */
    @GetMapping("/top-buyers/by-spending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Map<String, Object>>> getTopBuyersBySpending(Pageable pageable) {
        Page<Map<String, Object>> topBuyers = orderQueryService.getTopBuyersBySpending(pageable);
        return ResponseEntity.ok(topBuyers);
    }

    /**
     * Count orders by status (admin only).
     *
     * @param status order status
     * @return order count
     */
    @GetMapping("/count/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> countOrdersByStatus(@PathVariable OrderStatus status) {
        long count = orderQueryService.countOrdersByStatus(status);
        return ResponseEntity.ok(count);
    }

    /**
     * Count total orders (admin only).
     *
     * @return total order count
     */
    @GetMapping("/count/total")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> countTotalOrders() {
        long count = orderQueryService.countTotalOrders();
        return ResponseEntity.ok(count);
    }
}
