package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.OrderManagementDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.OrderManagementService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * REST controller for order management operations.
 */
@RestController
@RequestMapping("/api/v1/admin/orders")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class OrderManagementController {

    private final OrderManagementService orderManagementService;

    @PostMapping("/manage")
    public ResponseEntity<Order> manageOrder(
            @Valid @RequestBody OrderManagementDto managementDto,
            @RequestAttribute("userId") Long adminId) {
        Order order = orderManagementService.manageOrder(managementDto, adminId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(
            @PathVariable Long orderId,
            @RequestAttribute("userId") Long adminId) {
        Order cancelledOrder = orderManagementService.cancelOrder(orderId, adminId);
        return ResponseEntity.ok(cancelledOrder);
    }

    @PostMapping("/{orderId}/refund/full")
    public ResponseEntity<Order> processFullRefund(
            @PathVariable Long orderId,
            @RequestParam String reason,
            @RequestAttribute("userId") Long adminId) {
        Order refundedOrder = orderManagementService.processFullRefund(orderId, reason, adminId);
        return ResponseEntity.ok(refundedOrder);
    }

    @PostMapping("/{orderId}/refund/partial")
    public ResponseEntity<Order> processPartialRefund(
            @PathVariable Long orderId,
            @RequestParam BigDecimal amount,
            @RequestParam String reason,
            @RequestAttribute("userId") Long adminId) {
        Order refundedOrder = orderManagementService.processPartialRefund(orderId, amount, reason, adminId);
        return ResponseEntity.ok(refundedOrder);
    }

    @PostMapping("/{orderId}/complete")
    public ResponseEntity<Order> markAsCompleted(
            @PathVariable Long orderId,
            @RequestAttribute("userId") Long adminId) {
        Order completedOrder = orderManagementService.markAsCompleted(orderId, adminId);
        return ResponseEntity.ok(completedOrder);
    }

    @PostMapping("/{orderId}/dispute")
    public ResponseEntity<Order> markAsDisputed(
            @PathVariable Long orderId,
            @RequestAttribute("userId") Long adminId) {
        Order disputedOrder = orderManagementService.markAsDisputed(orderId, adminId);
        return ResponseEntity.ok(disputedOrder);
    }

    @PostMapping("/{orderId}/resolve-dispute")
    public ResponseEntity<Order> resolveDispute(
            @PathVariable Long orderId,
            @RequestParam String resolution,
            @RequestAttribute("userId") Long adminId) {
        Order resolvedOrder = orderManagementService.resolveDispute(orderId, resolution, adminId);
        return ResponseEntity.ok(resolvedOrder);
    }

    @GetMapping
    public ResponseEntity<Page<Order>> getAllOrders(Pageable pageable) {
        Page<Order> orders = orderManagementService.getAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<Order>> getOrdersByStatus(
            @PathVariable String status,
            Pageable pageable) {
        Page<Order> orders = orderManagementService.getOrdersByStatus(status, pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/date-range")
    public ResponseEntity<Page<Order>> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable) {
        Page<Order> orders = orderManagementService.getOrdersByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/disputed")
    public ResponseEntity<Page<Order>> getDisputedOrders(Pageable pageable) {
        Page<Order> disputedOrders = orderManagementService.getDisputedOrders(pageable);
        return ResponseEntity.ok(disputedOrders);
    }

    @GetMapping("/refunded")
    public ResponseEntity<Page<Order>> getRefundedOrders(Pageable pageable) {
        Page<Order> refundedOrders = orderManagementService.getRefundedOrders(pageable);
        return ResponseEntity.ok(refundedOrders);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<Order>> getCustomerOrders(
            @PathVariable Long customerId,
            Pageable pageable) {
        Page<Order> orders = orderManagementService.getCustomerOrders(customerId, pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Long orderId) {
        Order order = orderManagementService.getOrderDetails(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Order>> searchOrders(
            @RequestParam String searchTerm,
            Pageable pageable) {
        Page<Order> orders = orderManagementService.searchOrders(searchTerm, pageable);
        return ResponseEntity.ok(orders);
    }
}
