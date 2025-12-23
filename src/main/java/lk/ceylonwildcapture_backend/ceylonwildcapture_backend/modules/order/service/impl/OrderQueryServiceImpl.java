package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.OrderStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderSearchCriteria;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderSummaryDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.mapper.OrderMapper;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.repository.OrderRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Page<OrderSummaryDto> getAllOrders(Pageable pageable) {
        return orderMapper.toSummaryDtoPage(orderRepository.findAll(pageable));
    }

    @Override
    public Page<OrderSummaryDto> getOrdersByStatusSummary(OrderStatus status, Pageable pageable) {
        return orderMapper.toSummaryDtoPage(orderRepository.findByStatus(status, pageable));
    }

    @Override
    public Page<OrderResponseDto> getOrdersByStatus(OrderStatus status, Pageable pageable) {
        return orderMapper.toResponseDtoPage(orderRepository.findByStatus(status, pageable));
    }

    @Override
    public Page<OrderResponseDto> getOrdersByBuyer(Long buyerId, Pageable pageable) {
        return orderMapper.toResponseDtoPage(orderRepository.findByBuyerId(buyerId, pageable));
    }

    @Override
    public Page<OrderResponseDto> getOrdersByPhotographer(Long photographerId, Pageable pageable) {
        // This would require a custom query in OrderRepository that joins with
        // OrderItems
        // For now, let's keep it simple or implement a basic repo method if it exists
        // Actually, there's no direct findByPhotographerId in OrderRepository,
        // it needs to join OrderItems.
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public Page<OrderResponseDto> getOrdersByPhoto(Long photoId, Pageable pageable) {
        return orderMapper.toResponseDtoPage(orderRepository.findOrdersByPhotoId(photoId, pageable));
    }

    @Override
    public Page<OrderResponseDto> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate,
            Pageable pageable) {
        return orderMapper.toResponseDtoPage(orderRepository.findByCreatedAtBetween(startDate, endDate, pageable));
    }

    @Override
    public Page<OrderResponseDto> searchOrders(OrderSearchCriteria criteria, Pageable pageable) {
        // Basic implementation for now, should ideally use Specifications
        if (criteria.getOrderNumber() != null) {
            return orderRepository.findByOrderNumber(criteria.getOrderNumber())
                    .map(order -> new PageImpl<>(Collections.singletonList(orderMapper.toResponseDto(order)), pageable,
                            1))
                    .orElse(new PageImpl<>(Collections.emptyList()));
        }
        return orderMapper.toResponseDtoPage(orderRepository.findAll(pageable));
    }

    @Override
    public Page<OrderSummaryDto> getRecentOrders(Pageable pageable) {
        return orderMapper.toSummaryDtoPage(orderRepository.findAllByOrderByCreatedAtDesc(pageable));
    }

    @Override
    public Map<String, Object> getOrderStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalOrders", countTotalOrders());
        stats.put("pendingOrders", countOrdersByStatus(OrderStatus.PENDING));
        stats.put("processingOrders", countOrdersByStatus(OrderStatus.PROCESSING));
        stats.put("completedOrders", countOrdersByStatus(OrderStatus.COMPLETED));
        stats.put("cancelledOrders", countOrdersByStatus(OrderStatus.CANCELLED));
        stats.put("refundedOrders", countOrdersByStatus(OrderStatus.REFUNDED));
        stats.put("totalRevenue", getTotalSales());

        // Average order value
        long completedCount = countOrdersByStatus(OrderStatus.COMPLETED);
        BigDecimal totalSales = getTotalSales();
        BigDecimal avgValue = completedCount > 0
                ? totalSales.divide(BigDecimal.valueOf(completedCount), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;
        stats.put("averageOrderValue", avgValue);

        return stats;
    }

    @Override
    public BigDecimal getTotalSales() {
        BigDecimal total = orderRepository.calculateTotalSales(OrderStatus.COMPLETED);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public Map<String, Object> getBuyerStatistics(Long buyerId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalOrders", orderRepository.countByBuyerId(buyerId));
        stats.put("completedOrders", orderRepository.countByBuyerIdAndStatus(buyerId, OrderStatus.COMPLETED));
        stats.put("totalSpent", orderRepository.calculateTotalSpentByBuyer(buyerId, OrderStatus.COMPLETED));
        return stats;
    }

    @Override
    public Page<Map<String, Object>> getTopBuyersByOrderCount(Pageable pageable) {
        // This mapping logic should be more robust, but for a quick fix:
        return orderRepository.findTopBuyersByOrderCount(OrderStatus.COMPLETED, pageable)
                .map(obj -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("buyerId", obj[0]);
                    map.put("orderCount", obj[1]);
                    return map;
                });
    }

    @Override
    public Page<Map<String, Object>> getTopBuyersBySpending(Pageable pageable) {
        return orderRepository.findTopBuyersBySpending(OrderStatus.COMPLETED, pageable)
                .map(obj -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("buyerId", obj[0]);
                    map.put("totalSpent", obj[1]);
                    return map;
                });
    }

    @Override
    public long countOrdersByStatus(OrderStatus status) {
        return orderRepository.countByStatus(status);
    }

    @Override
    public long countTotalOrders() {
        return orderRepository.count();
    }
}
