package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.OrderStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.UserRole;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.DashboardAnalyticsDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.DashboardAnalyticsService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.Order;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.repository.OrderRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.repository.PhotoRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardAnalyticsServiceImpl implements DashboardAnalyticsService {

    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final OrderRepository orderRepository;

    @Override
    public DashboardAnalyticsDto getDashboardAnalytics() {
        log.info("Fetching complete dashboard analytics");

        // User Statistics
        long totalUsers = userRepository.count();
        long totalPhotographers = userRepository.countByRole(UserRole.PHOTOGRAPHER);
        long totalBuyers = userRepository.countByRole(UserRole.BUYER);

        // Photo Statistics
        long totalPhotos = photoRepository.count();
        long pendingPhotos = photoRepository.countByIsApproved(false);
        long approvedPhotos = photoRepository.countByIsApproved(true);

        // Order Statistics
        long totalOrders = orderRepository.count();

        // Revenue Statistics
        BigDecimal totalRevenue = orderRepository.calculateTotalSales(OrderStatus.COMPLETED);
        if (totalRevenue == null) {
            totalRevenue = BigDecimal.ZERO;
        }

        return DashboardAnalyticsDto.builder()
                .totalUsers(totalUsers)
                .totalPhotographers(totalPhotographers)
                .totalCustomers(totalBuyers)
                .totalPhotos(totalPhotos)
                .pendingPhotos(pendingPhotos)
                .approvedPhotos(approvedPhotos)
                .totalOrders(totalOrders)
                .totalRevenue(totalRevenue)
                .generatedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public DashboardAnalyticsDto getDashboardAnalytics(LocalDate startDate, LocalDate endDate) {
        log.info("Fetching dashboard analytics for date range: {} to {}", startDate, endDate);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        // User Statistics (all time)
        long totalUsers = userRepository.count();
        long totalPhotographers = userRepository.countByRole(UserRole.PHOTOGRAPHER);
        long totalBuyers = userRepository.countByRole(UserRole.BUYER);

        // Photo Statistics (all time)
        long totalPhotos = photoRepository.count();
        long pendingPhotos = photoRepository.countByIsApproved(false);
        long approvedPhotos = photoRepository.countByIsApproved(true);

        // Order Statistics (filtered by date range)
        long totalOrders = orderRepository
                .findByCreatedAtBetween(startDateTime, endDateTime, PageRequest.of(0, Integer.MAX_VALUE))
                .getTotalElements();
        long pendingOrders = orderRepository
                .findByStatusOrderByCreatedAtDesc(OrderStatus.PENDING, PageRequest.of(0, Integer.MAX_VALUE))
                .getTotalElements();
        long completedOrders = orderRepository
                .findByStatusOrderByCreatedAtDesc(OrderStatus.COMPLETED, PageRequest.of(0, Integer.MAX_VALUE))
                .getTotalElements();

        // Revenue Statistics (filtered by date range)
        BigDecimal totalRevenue = orderRepository
                .findByCreatedAtBetween(startDateTime, endDateTime, PageRequest.of(0, Integer.MAX_VALUE))
                .stream()
                .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return DashboardAnalyticsDto.builder()
                .totalUsers(totalUsers)
                .totalPhotographers(totalPhotographers)
                .totalCustomers(totalBuyers)
                .totalPhotos(totalPhotos)
                .pendingPhotos(pendingPhotos)
                .approvedPhotos(approvedPhotos)
                .totalOrders(totalOrders)
                .totalRevenue(totalRevenue)
                .generatedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public Map<String, Object> getUserStatistics() {
        // TODO: Implement actual business logic
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> getPhotoStatistics() {
        // TODO: Implement actual business logic
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> getSalesStatistics() {
        // TODO: Implement actual business logic
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> getRevenueStatistics() {
        // TODO: Implement actual business logic
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> getEarningsBreakdown() {
        // TODO: Implement actual business logic
        return new HashMap<>();
    }

    @Override
    public List<DashboardAnalyticsDto.TopSellingPhoto> getTopSellingPhotos(int limit) {
        // TODO: Implement actual business logic
        return Collections.emptyList();
    }

    @Override
    public List<DashboardAnalyticsDto.TopPhotographer> getTopPhotographers(int limit) {
        // TODO: Implement actual business logic
        return Collections.emptyList();
    }

    @Override
    public List<DashboardAnalyticsDto.TopCustomer> getTopCustomers(int limit) {
        // TODO: Implement actual business logic
        return Collections.emptyList();
    }

    @Override
    public Map<String, BigDecimal> getRevenueByMonth(int months) {
        // TODO: Implement actual business logic
        return new HashMap<>();
    }

    @Override
    public Map<String, Long> getOrdersByMonth(int months) {
        // TODO: Implement actual business logic
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> getSalesStatistics(LocalDate startDate, LocalDate endDate) {
        // TODO: Implement actual business logic
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> getRevenueStatistics(LocalDate startDate, LocalDate endDate) {
        // TODO: Implement actual business logic
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> getPayoutStatistics(LocalDate startDate, LocalDate endDate) {
        // TODO: Implement actual business logic
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> getGrowthMetrics(LocalDate startDate, LocalDate endDate) {
        // TODO: Implement actual business logic
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> getDailyTrends(LocalDate startDate, LocalDate endDate) {
        // TODO: Implement actual business logic
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> getCategoryPerformance(LocalDate startDate, LocalDate endDate) {
        // TODO: Implement actual business logic
        return new HashMap<>();
    }

    @Override
    public List<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.CategoryPerformanceDto> getCategoryPerformance(
            int limit) {
        log.info("Fetching category performance data with limit: {}", limit);

        // Get all categories
        List<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category> categories = photoRepository
                .findAll().stream()
                .flatMap(photo -> photo.getCategories().stream())
                .distinct()
                .limit(limit)
                .collect(java.util.stream.Collectors.toList());

        return categories.stream()
                .map(category -> {
                    // Count photos in this category
                    long photoCount = photoRepository.findAll().stream()
                            .filter(photo -> photo.getCategories().contains(category))
                            .count();

                    // Count sales (orders containing photos from this category)
                    long salesCount = orderRepository.findAll().stream()
                            .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
                            .flatMap(order -> order.getOrderItems().stream())
                            .filter(item -> item.getPhoto().getCategories().contains(category))
                            .count();

                    // Calculate revenue from this category
                    BigDecimal revenue = orderRepository.findAll().stream()
                            .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
                            .flatMap(order -> order.getOrderItems().stream())
                            .filter(item -> item.getPhoto().getCategories().contains(category))
                            .map(item -> item.getPrice())
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.CategoryPerformanceDto
                            .builder()
                            .categoryId(category.getId())
                            .categoryName(category.getName())
                            .categorySlug(category.getSlug())
                            .photoCount(photoCount)
                            .salesCount(salesCount)
                            .totalRevenue(revenue)
                            .build();
                })
                .sorted((a, b) -> b.getTotalRevenue().compareTo(a.getTotalRevenue()))
                .limit(limit)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.RecentActivityDto> getRecentActivity(
            int limit) {
        log.info("Fetching recent activity with limit: {}", limit);

        List<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.RecentActivityDto> activities = new ArrayList<>();

        // Get recent photos
        photoRepository.findAll().stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .limit(limit / 3)
                .forEach(photo -> {
                    activities.add(
                            lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.RecentActivityDto
                                    .builder()
                                    .type("photo")
                                    .action("New photo uploaded: " + photo.getTitle())
                                    .userName(photo.getPhotographer().getFirstName() + " "
                                            + photo.getPhotographer().getLastName())
                                    .userId(photo.getPhotographer().getId())
                                    .timestamp(photo.getCreatedAt())
                                    .timeAgo(getTimeAgo(photo.getCreatedAt()))
                                    .build());
                });

        // Get recent orders
        orderRepository.findAll().stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .limit(limit / 3)
                .forEach(order -> {
                    String action = order.getStatus() == OrderStatus.COMPLETED ? "Order completed" : "New order placed";
                    activities.add(
                            lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.RecentActivityDto
                                    .builder()
                                    .type("order")
                                    .action(action)
                                    .userName(order.getBuyer().getFirstName() + " " + order.getBuyer().getLastName())
                                    .userId(order.getBuyer().getId())
                                    .timestamp(order.getCreatedAt())
                                    .timeAgo(getTimeAgo(order.getCreatedAt()))
                                    .build());
                });

        // Get recent users
        userRepository.findAll().stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .limit(limit / 3)
                .forEach(user -> {
                    String action = user.getRole() == UserRole.PHOTOGRAPHER ? "New photographer registered"
                            : "New user registered";
                    activities.add(
                            lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.RecentActivityDto
                                    .builder()
                                    .type("user")
                                    .action(action)
                                    .userName(user.getFirstName() + " " + user.getLastName())
                                    .userId(user.getId())
                                    .timestamp(user.getCreatedAt())
                                    .timeAgo(getTimeAgo(user.getCreatedAt()))
                                    .build());
                });

        // Sort all activities by timestamp and limit
        return activities.stream()
                .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
                .limit(limit)
                .collect(java.util.stream.Collectors.toList());
    }

    private String getTimeAgo(LocalDateTime timestamp) {
        long minutes = java.time.Duration.between(timestamp, LocalDateTime.now()).toMinutes();
        if (minutes < 1)
            return "Just now";
        if (minutes < 60)
            return minutes + " minute" + (minutes > 1 ? "s" : "") + " ago";

        long hours = minutes / 60;
        if (hours < 24)
            return hours + " hour" + (hours > 1 ? "s" : "") + " ago";

        long days = hours / 24;
        if (days < 30)
            return days + " day" + (days > 1 ? "s" : "") + " ago";

        long months = days / 30;
        return months + " month" + (months > 1 ? "s" : "") + " ago";
    }
}
