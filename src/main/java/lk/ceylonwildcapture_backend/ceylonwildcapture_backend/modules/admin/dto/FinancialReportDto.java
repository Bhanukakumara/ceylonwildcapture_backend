package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * DTO for financial reports.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialReportDto {

    private LocalDate startDate;
    private LocalDate endDate;

    // Revenue
    private BigDecimal totalRevenue;
    private BigDecimal platformRevenue;
    private BigDecimal photographerRevenue;

    // Orders
    private Long totalOrders;
    private Long completedOrders;
    private Long cancelledOrders;
    private Long refundedOrders;

    // Payouts
    private BigDecimal totalPayouts;
    private BigDecimal pendingPayouts;
    private BigDecimal completedPayouts;

    // Refunds
    private BigDecimal totalRefunds;
    private Long refundCount;

    // Breakdown by category
    private Map<String, BigDecimal> revenueByCategory;
    private Map<String, Long> salesByCategory;

    // Breakdown by photographer
    private List<PhotographerEarning> topEarningPhotographers;

    // Daily breakdown
    private List<DailyRevenue> dailyRevenueData;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhotographerEarning {
        private Long photographerId;
        private String photographerName;
        private BigDecimal totalEarnings;
        private BigDecimal platformFee;
        private Long totalSales;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyRevenue {
        private LocalDate date;
        private BigDecimal revenue;
        private Long orders;
    }
}
