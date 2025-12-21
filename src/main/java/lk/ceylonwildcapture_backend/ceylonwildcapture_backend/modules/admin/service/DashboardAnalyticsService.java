package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.DashboardAnalyticsDto;

import java.time.LocalDate;
import java.util.Map;

/**
 * Service interface for admin dashboard analytics.
 */
public interface DashboardAnalyticsService {

    /**
     * Get complete dashboard analytics.
     *
     * @return dashboard analytics DTO
     */
    DashboardAnalyticsDto getDashboardAnalytics();

    /**
     * Get dashboard analytics for a specific date range.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return dashboard analytics DTO
     */
    DashboardAnalyticsDto getDashboardAnalytics(LocalDate startDate, LocalDate endDate);

    /**
     * Get user statistics.
     *
     * @return user statistics map
     */
    java.util.Map<String, Object> getUserStatistics();

    /**
     * Get photo statistics.
     *
     * @return photo statistics map
     */
    java.util.Map<String, Object> getPhotoStatistics();

    /**
     * Get sales statistics.
     *
     * @return sales statistics map
     */
    java.util.Map<String, Object> getSalesStatistics();

    /**
     * Get revenue statistics.
     *
     * @return revenue statistics map
     */
    java.util.Map<String, Object> getRevenueStatistics();

    /**
     * Get platform earnings breakdown.
     *
     * @return earnings breakdown map
     */
    java.util.Map<String, Object> getEarningsBreakdown();

    /**
     * Get top selling photos.
     *
     * @param limit number of photos to retrieve
     * @return list of top selling photos
     */
    java.util.List<DashboardAnalyticsDto.TopSellingPhoto> getTopSellingPhotos(int limit);

    /**
     * Get top photographers by earnings.
     *
     * @param limit number of photographers to retrieve
     * @return list of top photographers
     */
    java.util.List<DashboardAnalyticsDto.TopPhotographer> getTopPhotographers(int limit);

    /**
     * Get top customers by spending.
     *
     * @param limit number of customers to retrieve
     * @return list of top customers
     */
    java.util.List<DashboardAnalyticsDto.TopCustomer> getTopCustomers(int limit);

    /**
     * Get revenue trend by month.
     *
     * @param months number of months to retrieve
     * @return revenue by month map
     */
    java.util.Map<String, java.math.BigDecimal> getRevenueByMonth(int months);

    /**
     * Get orders trend by month.
     *
     * @param months number of months to retrieve
     * @return orders by month map
     */
    java.util.Map<String, Long> getOrdersByMonth(int months);

    Map<String, Object> getSalesStatistics(LocalDate startDate, LocalDate endDate);

    Map<String, Object> getRevenueStatistics(LocalDate startDate, LocalDate endDate);

    Map<String, Object> getPayoutStatistics(LocalDate startDate, LocalDate endDate);

    Map<String, Object> getGrowthMetrics(LocalDate startDate, LocalDate endDate);

    Map<String, Object> getDailyTrends(LocalDate startDate, LocalDate endDate);

    Map<String, Object> getCategoryPerformance(LocalDate startDate, LocalDate endDate);

    /**
     * Get category performance data.
     *
     * @param limit number of categories to retrieve
     * @return list of category performance data
     */
    java.util.List<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.CategoryPerformanceDto> getCategoryPerformance(
            int limit);

    /**
     * Get recent activity.
     *
     * @param limit number of activities to retrieve
     * @return list of recent activities
     */
    java.util.List<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.RecentActivityDto> getRecentActivity(
            int limit);
}
