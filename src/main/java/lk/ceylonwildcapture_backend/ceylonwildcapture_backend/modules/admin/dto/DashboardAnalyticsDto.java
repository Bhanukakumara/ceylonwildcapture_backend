package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * DTO for admin dashboard analytics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardAnalyticsDto {

    // User Statistics
    private Long totalUsers;
    private Long activeUsers;
    private Long totalPhotographers;
    private Long verifiedPhotographers;
    private Long totalCustomers;

    // Photo Statistics
    private Long totalPhotos;
    private Long pendingPhotos;
    private Long approvedPhotos;
    private Long rejectedPhotos;

    // Sales Statistics
    private Long totalOrders;
    private BigDecimal totalRevenue;
    private BigDecimal dailyRevenue;
    private BigDecimal weeklyRevenue;
    private BigDecimal monthlyRevenue;
    private BigDecimal yearlyRevenue;

    // Platform Earnings
    private BigDecimal platformEarnings;
    private BigDecimal photographerEarnings;
    private BigDecimal pendingPayouts;

    // Top Performers
    private List<TopSellingPhoto> topSellingPhotos;
    private List<TopPhotographer> topPhotographers;
    private List<TopCustomer> topCustomers;

    // Recent Activity
    private Long newUsersToday;
    private Long newPhotosToday;
    private Long ordersToday;

    // Time-based metrics
    private Map<String, BigDecimal> revenueByMonth;
    private Map<String, Long> ordersByMonth;

    private LocalDateTime generatedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopSellingPhoto {
        private Long photoId;
        private String title;
        private String photographerName;
        private Long totalSales;
        private BigDecimal totalRevenue;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopPhotographer {
        private Long photographerId;
        private String name;
        private Long totalSales;
        private BigDecimal totalEarnings;
        private Double rating;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopCustomer {
        private Long customerId;
        private String name;
        private Long totalOrders;
        private BigDecimal totalSpent;
    }
}
