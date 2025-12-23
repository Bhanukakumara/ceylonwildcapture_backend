package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.OrderStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.exception.OrderNotFoundException;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.OrderManagementDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.AuditLogService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.OrderManagementService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.Order;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Implementation of OrderManagementService.
 * Handles admin operations for order management including cancellations,
 * refunds, and disputes.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderManagementServiceImpl implements OrderManagementService {

    private final OrderRepository orderRepository;
    private final AuditLogService auditLogService;

    @Override
    public Order manageOrder(OrderManagementDto managementDto, Long adminId) {
        log.info("Admin {} managing order {} with action {}", adminId, managementDto.getOrderId(),
                managementDto.getAction());

        Order order = orderRepository.findById(managementDto.getOrderId())
                .orElseThrow(
                        () -> new OrderNotFoundException("Order not found with ID: " + managementDto.getOrderId()));

        switch (managementDto.getAction()) {
            case CANCEL:
                return cancelOrderInternal(order, adminId);
            case REFUND_FULL:
                return processFullRefundInternal(order, managementDto.getRefundReason(), adminId);
            case REFUND_PARTIAL:
                return processPartialRefundInternal(order, managementDto.getRefundAmount(),
                        managementDto.getRefundReason(), adminId);
            case MARK_COMPLETED:
                return markAsCompletedInternal(order, adminId);
            case MARK_DISPUTED:
                return markAsDisputedInternal(order, adminId);
            case RESOLVE_DISPUTE:
                return resolveDisputeInternal(order, managementDto.getAdminNotes(), adminId);
            default:
                throw new IllegalArgumentException("Unknown order action: " + managementDto.getAction());
        }
    }

    @Override
    public Order cancelOrder(Long orderId, Long adminId) {
        log.info("Admin {} cancelling order {}", adminId, orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        return cancelOrderInternal(order, adminId);
    }

    @Override
    public Order processFullRefund(Long orderId, String reason, Long adminId) {
        log.info("Admin {} processing full refund for order {}", adminId, orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        return processFullRefundInternal(order, reason, adminId);
    }

    @Override
    public Order processPartialRefund(Long orderId, BigDecimal amount, String reason, Long adminId) {
        log.info("Admin {} processing partial refund of {} for order {}", adminId, amount, orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        return processPartialRefundInternal(order, amount, reason, adminId);
    }

    @Override
    public Order markAsCompleted(Long orderId, Long adminId) {
        log.info("Admin {} marking order {} as completed", adminId, orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        return markAsCompletedInternal(order, adminId);
    }

    @Override
    public Order markAsDisputed(Long orderId, Long adminId) {
        log.info("Admin {} marking order {} as disputed", adminId, orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        return markAsDisputedInternal(order, adminId);
    }

    @Override
    public Order resolveDispute(Long orderId, String resolution, Long adminId) {
        log.info("Admin {} resolving dispute for order {}", adminId, orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        return resolveDisputeInternal(order, resolution, adminId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> getOrdersByStatus(String status, Pageable pageable) {
        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        return orderRepository.findByStatus(orderStatus, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return orderRepository.findByCreatedAtBetween(startDate, endDate, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> getDisputedOrders(Pageable pageable) {
        return orderRepository.findByStatus(OrderStatus.DISPUTED, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> getRefundedOrders(Pageable pageable) {
        return orderRepository.findRefundedOrders(OrderStatus.REFUNDED, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> getCustomerOrders(Long customerId, Pageable pageable) {
        return orderRepository.findByBuyerId(customerId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderDetails(Long orderId) {
        return orderRepository.findByIdWithOrderItems(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> searchOrders(String searchTerm, Pageable pageable) {
        // Search by order number or buyer email
        return orderRepository.findByBillingEmailContainingIgnoreCase(searchTerm, pageable);
    }

    // Internal Helper Methods

    private Order cancelOrderInternal(Order order, Long adminId) {
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order is already cancelled");
        }

        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a completed order. Use refund instead.");
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setCancelledAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        // Create audit log
        auditLogService.createAuditLog(
                "ORDER_CANCELLED",
                "ORDER",
                order.getId(),
                adminId,
                "CANCEL",
                "Order cancelled by admin",
                null);

        return savedOrder;
    }

    private Order processFullRefundInternal(Order order, String reason, Long adminId) {
        if (order.getStatus() != OrderStatus.COMPLETED && order.getStatus() != OrderStatus.PROCESSING) {
            throw new IllegalStateException("Cannot refund order with status: " + order.getStatus());
        }

        order.setStatus(OrderStatus.REFUNDED);
        order.setRefundedAt(LocalDateTime.now());
        order.setRefundAmount(order.getTotalAmount());
        order.setRefundReason(reason);
        Order savedOrder = orderRepository.save(order);

        // Create audit log
        auditLogService.createAuditLog(
                "ORDER_REFUNDED",
                "ORDER",
                order.getId(),
                adminId,
                "REFUND",
                "Full refund processed: " + reason,
                "Refund Amount: " + order.getTotalAmount());

        return savedOrder;
    }

    private Order processPartialRefundInternal(Order order, BigDecimal amount, String reason, Long adminId) {
        if (order.getStatus() != OrderStatus.COMPLETED && order.getStatus() != OrderStatus.PROCESSING) {
            throw new IllegalStateException("Cannot refund order with status: " + order.getStatus());
        }

        if (amount.compareTo(order.getTotalAmount()) > 0) {
            throw new IllegalArgumentException("Refund amount cannot exceed order total");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Refund amount must be greater than zero");
        }

        // For partial refund, keep status as REFUNDED but track the partial amount
        order.setStatus(OrderStatus.REFUNDED);
        order.setRefundedAt(LocalDateTime.now());
        order.setRefundAmount(amount);
        order.setRefundReason(reason);
        Order savedOrder = orderRepository.save(order);

        // Create audit log
        auditLogService.createAuditLog(
                "ORDER_PARTIAL_REFUND",
                "ORDER",
                order.getId(),
                adminId,
                "REFUND",
                "Partial refund processed: " + reason,
                "Refund Amount: " + amount + " of " + order.getTotalAmount());

        return savedOrder;
    }

    private Order markAsCompletedInternal(Order order, Long adminId) {
        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Order is already completed");
        }

        if (order.getStatus() != OrderStatus.PROCESSING && order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Cannot complete order with status: " + order.getStatus());
        }

        order.setStatus(OrderStatus.COMPLETED);
        order.setCompletedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        // Create audit log
        auditLogService.createAuditLog(
                "ORDER_COMPLETED",
                "ORDER",
                order.getId(),
                adminId,
                "COMPLETE",
                "Order marked as completed by admin",
                null);

        return savedOrder;
    }

    private Order markAsDisputedInternal(Order order, Long adminId) {
        if (order.getStatus() == OrderStatus.DISPUTED) {
            throw new IllegalStateException("Order is already disputed");
        }

        order.setStatus(OrderStatus.DISPUTED);
        Order savedOrder = orderRepository.save(order);

        // Create audit log
        auditLogService.createAuditLog(
                "ORDER_DISPUTED",
                "ORDER",
                order.getId(),
                adminId,
                "DISPUTE",
                "Order marked as disputed",
                null);

        return savedOrder;
    }

    private Order resolveDisputeInternal(Order order, String resolution, Long adminId) {
        if (order.getStatus() != OrderStatus.DISPUTED) {
            throw new IllegalStateException("Order is not in disputed status");
        }

        // After resolving dispute, mark as completed
        order.setStatus(OrderStatus.COMPLETED);
        order.setCompletedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        // Create audit log
        auditLogService.createAuditLog(
                "ORDER_DISPUTE_RESOLVED",
                "ORDER",
                order.getId(),
                adminId,
                "RESOLVE",
                "Dispute resolved: " + resolution,
                null);

        return savedOrder;
    }
}
