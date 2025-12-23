package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.OrderStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.PayoutStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.FinancialReportDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.FinancialReportService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.Order;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.repository.OrderRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.entity.Payout;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.repository.PayoutRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of FinancialReportService.
 * Generates comprehensive financial reports with revenue, orders, payouts, and
 * breakdowns.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FinancialReportServiceImpl implements FinancialReportService {

    private final OrderRepository orderRepository;
    private final PayoutRepository payoutRepository;

    // Platform commission rate (10%)
    private static final BigDecimal PLATFORM_COMMISSION = new BigDecimal("0.10");

    @Override
    public FinancialReportDto generateFinancialReport(LocalDate startDate, LocalDate endDate) {
        log.info("Generating financial report from {} to {}", startDate, endDate);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        // Get all completed orders in date range
        List<Order> orders = orderRepository.findByCreatedAtBetweenAndStatus(
                startDateTime, endDateTime, OrderStatus.COMPLETED);

        // Get all payouts in date range
        List<Payout> payouts = payoutRepository.findByCreatedAtBetween(
                startDateTime, endDateTime, org.springframework.data.domain.Pageable.unpaged()).getContent();

        // Get cancelled and refunded orders
        List<Order> cancelledOrders = orderRepository.findByCreatedAtBetweenAndStatus(
                startDateTime, endDateTime, OrderStatus.CANCELLED);
        List<Order> refundedOrders = orderRepository.findByCreatedAtBetweenAndStatus(
                startDateTime, endDateTime, OrderStatus.REFUNDED);

        return buildFinancialReport(startDate, endDate, orders, payouts, cancelledOrders, refundedOrders);
    }

    @Override
    public FinancialReportDto generateMonthlyReport(int year, int month) {
        log.info("Generating monthly report for {}-{}", year, month);

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        return generateFinancialReport(startDate, endDate);
    }

    @Override
    public FinancialReportDto generateYearlyReport(int year) {
        log.info("Generating yearly report for {}", year);

        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        return generateFinancialReport(startDate, endDate);
    }

    @Override
    public byte[] exportToPdf(FinancialReportDto report) {
        // TODO: Implement PDF export using iText or similar library
        log.warn("PDF export not yet implemented");

        // For now, return a simple text-based representation
        String content = generateTextReport(report);
        return content.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] exportToExcel(FinancialReportDto report) {
        // TODO: Implement Excel export using Apache POI
        log.warn("Excel export not yet implemented");

        // For now, return CSV format
        return exportToCsv(report);
    }

    @Override
    public byte[] exportToCsv(FinancialReportDto report) {
        log.info("Exporting financial report to CSV");

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            StringBuilder csv = new StringBuilder();

            // Header
            csv.append("Financial Report\n");
            csv.append("Period: ").append(report.getStartDate()).append(" to ").append(report.getEndDate())
                    .append("\n\n");

            // Summary Section
            csv.append("SUMMARY\n");
            csv.append("Metric,Amount\n");
            csv.append("Total Revenue,").append(report.getTotalRevenue()).append("\n");
            csv.append("Platform Revenue,").append(report.getPlatformRevenue()).append("\n");
            csv.append("Photographer Revenue,").append(report.getPhotographerRevenue()).append("\n");
            csv.append("Total Orders,").append(report.getTotalOrders()).append("\n");
            csv.append("Completed Orders,").append(report.getCompletedOrders()).append("\n");
            csv.append("Cancelled Orders,").append(report.getCancelledOrders()).append("\n");
            csv.append("Refunded Orders,").append(report.getRefundedOrders()).append("\n");
            csv.append("Total Payouts,").append(report.getTotalPayouts()).append("\n");
            csv.append("Total Refunds,").append(report.getTotalRefunds()).append("\n\n");

            // Revenue by Category
            if (report.getRevenueByCategory() != null && !report.getRevenueByCategory().isEmpty()) {
                csv.append("REVENUE BY CATEGORY\n");
                csv.append("Category,Revenue,Sales Count\n");
                report.getRevenueByCategory().forEach((category, revenue) -> {
                    Long sales = report.getSalesByCategory().getOrDefault(category, 0L);
                    csv.append(category).append(",").append(revenue).append(",").append(sales).append("\n");
                });
                csv.append("\n");
            }

            // Top Photographers
            if (report.getTopEarningPhotographers() != null && !report.getTopEarningPhotographers().isEmpty()) {
                csv.append("TOP EARNING PHOTOGRAPHERS\n");
                csv.append("Photographer ID,Name,Total Earnings,Platform Fee,Total Sales\n");
                report.getTopEarningPhotographers().forEach(p -> {
                    csv.append(p.getPhotographerId()).append(",")
                            .append(p.getPhotographerName() != null ? p.getPhotographerName() : "N/A").append(",")
                            .append(p.getTotalEarnings()).append(",")
                            .append(p.getPlatformFee()).append(",")
                            .append(p.getTotalSales()).append("\n");
                });
                csv.append("\n");
            }

            // Daily Revenue
            if (report.getDailyRevenueData() != null && !report.getDailyRevenueData().isEmpty()) {
                csv.append("DAILY REVENUE\n");
                csv.append("Date,Revenue,Orders\n");
                report.getDailyRevenueData().forEach(d -> {
                    csv.append(d.getDate()).append(",")
                            .append(d.getRevenue()).append(",")
                            .append(d.getOrders()).append("\n");
                });
            }

            baos.write(csv.toString().getBytes(StandardCharsets.UTF_8));
            return baos.toByteArray();

        } catch (Exception e) {
            log.error("Failed to export CSV", e);
            return new byte[0];
        }
    }

    // Helper Methods

    private FinancialReportDto buildFinancialReport(
            LocalDate startDate,
            LocalDate endDate,
            List<Order> completedOrders,
            List<Payout> payouts,
            List<Order> cancelledOrders,
            List<Order> refundedOrders) {

        // Calculate total revenue from completed orders
        BigDecimal totalRevenue = completedOrders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate platform revenue (10% commission)
        BigDecimal platformRevenue = totalRevenue.multiply(PLATFORM_COMMISSION)
                .setScale(2, RoundingMode.HALF_UP);

        // Calculate photographer revenue (90%)
        BigDecimal photographerRevenue = totalRevenue.subtract(platformRevenue)
                .setScale(2, RoundingMode.HALF_UP);

        // Calculate payout totals
        BigDecimal totalPayouts = payouts.stream()
                .map(Payout::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal completedPayouts = payouts.stream()
                .filter(p -> p.getStatus() == PayoutStatus.COMPLETED)
                .map(Payout::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal pendingPayouts = payouts.stream()
                .filter(p -> p.getStatus() == PayoutStatus.PENDING || p.getStatus() == PayoutStatus.PROCESSING)
                .map(Payout::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate refunds
        BigDecimal totalRefunds = refundedOrders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Build revenue by category
        Map<String, BigDecimal> revenueByCategory = calculateRevenueByCategory(completedOrders);
        Map<String, Long> salesByCategory = calculateSalesByCategory(completedOrders);

        // Build top photographers
        List<FinancialReportDto.PhotographerEarning> topPhotographers = calculateTopPhotographers(completedOrders);

        // Build daily revenue
        List<FinancialReportDto.DailyRevenue> dailyRevenue = calculateDailyRevenue(completedOrders, startDate, endDate);

        return FinancialReportDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .totalRevenue(totalRevenue)
                .platformRevenue(platformRevenue)
                .photographerRevenue(photographerRevenue)
                .totalOrders((long) (completedOrders.size() + cancelledOrders.size() + refundedOrders.size()))
                .completedOrders((long) completedOrders.size())
                .cancelledOrders((long) cancelledOrders.size())
                .refundedOrders((long) refundedOrders.size())
                .totalPayouts(totalPayouts)
                .pendingPayouts(pendingPayouts)
                .completedPayouts(completedPayouts)
                .totalRefunds(totalRefunds)
                .refundCount((long) refundedOrders.size())
                .revenueByCategory(revenueByCategory)
                .salesByCategory(salesByCategory)
                .topEarningPhotographers(topPhotographers)
                .dailyRevenueData(dailyRevenue)
                .build();
    }

    private Map<String, BigDecimal> calculateRevenueByCategory(List<Order> orders) {
        Map<String, BigDecimal> revenueByCategory = new HashMap<>();

        for (Order order : orders) {
            order.getOrderItems().forEach(item -> {
                if (item.getPhoto() != null && !item.getPhoto().getCategories().isEmpty()) {
                    item.getPhoto().getCategories().forEach(category -> {
                        String categoryName = category.getName();
                        BigDecimal itemRevenue = item.getPrice();
                        revenueByCategory.merge(categoryName, itemRevenue, BigDecimal::add);
                    });
                } else {
                    // Uncategorized
                    revenueByCategory.merge("Uncategorized", item.getPrice(), BigDecimal::add);
                }
            });
        }

        return revenueByCategory;
    }

    private Map<String, Long> calculateSalesByCategory(List<Order> orders) {
        Map<String, Long> salesByCategory = new HashMap<>();

        for (Order order : orders) {
            order.getOrderItems().forEach(item -> {
                if (item.getPhoto() != null && !item.getPhoto().getCategories().isEmpty()) {
                    item.getPhoto().getCategories().forEach(category -> {
                        String categoryName = category.getName();
                        salesByCategory.merge(categoryName, 1L, Long::sum);
                    });
                } else {
                    salesByCategory.merge("Uncategorized", 1L, Long::sum);
                }
            });
        }

        return salesByCategory;
    }

    private List<FinancialReportDto.PhotographerEarning> calculateTopPhotographers(List<Order> orders) {
        Map<Long, PhotographerData> photographerMap = new HashMap<>();

        for (Order order : orders) {
            order.getOrderItems().forEach(item -> {
                if (item.getPhoto() != null && item.getPhoto().getPhotographer() != null) {
                    Long photographerId = item.getPhoto().getPhotographer().getId();
                    String photographerName = item.getPhoto().getPhotographer().getFirstName() + " " +
                            item.getPhoto().getPhotographer().getLastName();

                    BigDecimal itemPrice = item.getPrice();
                    BigDecimal photographerEarning = itemPrice.multiply(BigDecimal.ONE.subtract(PLATFORM_COMMISSION));
                    BigDecimal platformFee = itemPrice.multiply(PLATFORM_COMMISSION);

                    photographerMap.compute(photographerId, (id, data) -> {
                        if (data == null) {
                            data = new PhotographerData();
                            data.id = photographerId;
                            data.name = photographerName;
                        }
                        data.totalEarnings = data.totalEarnings.add(photographerEarning);
                        data.platformFee = data.platformFee.add(platformFee);
                        data.salesCount++;
                        return data;
                    });
                }
            });
        }

        return photographerMap.values().stream()
                .sorted((a, b) -> b.totalEarnings.compareTo(a.totalEarnings))
                .limit(10)
                .map(data -> FinancialReportDto.PhotographerEarning.builder()
                        .photographerId(data.id)
                        .photographerName(data.name)
                        .totalEarnings(data.totalEarnings.setScale(2, RoundingMode.HALF_UP))
                        .platformFee(data.platformFee.setScale(2, RoundingMode.HALF_UP))
                        .totalSales(data.salesCount)
                        .build())
                .collect(Collectors.toList());
    }

    private List<FinancialReportDto.DailyRevenue> calculateDailyRevenue(
            List<Order> orders, LocalDate startDate, LocalDate endDate) {

        Map<LocalDate, DailyData> dailyMap = new HashMap<>();

        // Initialize all dates in range
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            dailyMap.put(current, new DailyData());
            current = current.plusDays(1);
        }

        // Aggregate order data
        for (Order order : orders) {
            LocalDate orderDate = order.getCreatedAt().toLocalDate();
            if (!orderDate.isBefore(startDate) && !orderDate.isAfter(endDate)) {
                DailyData data = dailyMap.get(orderDate);
                if (data != null) {
                    data.revenue = data.revenue.add(order.getTotalAmount());
                    data.orderCount++;
                }
            }
        }

        return dailyMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> FinancialReportDto.DailyRevenue.builder()
                        .date(entry.getKey())
                        .revenue(entry.getValue().revenue.setScale(2, RoundingMode.HALF_UP))
                        .orders(entry.getValue().orderCount)
                        .build())
                .collect(Collectors.toList());
    }

    private String generateTextReport(FinancialReportDto report) {
        StringBuilder sb = new StringBuilder();
        sb.append("FINANCIAL REPORT\n");
        sb.append("================\n\n");
        sb.append("Period: ").append(report.getStartDate()).append(" to ").append(report.getEndDate()).append("\n\n");
        sb.append("REVENUE SUMMARY\n");
        sb.append("Total Revenue: $").append(report.getTotalRevenue()).append("\n");
        sb.append("Platform Revenue: $").append(report.getPlatformRevenue()).append("\n");
        sb.append("Photographer Revenue: $").append(report.getPhotographerRevenue()).append("\n\n");
        sb.append("ORDERS\n");
        sb.append("Total Orders: ").append(report.getTotalOrders()).append("\n");
        sb.append("Completed: ").append(report.getCompletedOrders()).append("\n");
        sb.append("Cancelled: ").append(report.getCancelledOrders()).append("\n");
        sb.append("Refunded: ").append(report.getRefundedOrders()).append("\n\n");
        sb.append("PAYOUTS\n");
        sb.append("Total Payouts: $").append(report.getTotalPayouts()).append("\n");
        sb.append("Completed: $").append(report.getCompletedPayouts()).append("\n");
        sb.append("Pending: $").append(report.getPendingPayouts()).append("\n");
        return sb.toString();
    }

    // Helper classes for data aggregation
    private static class PhotographerData {
        Long id;
        String name;
        BigDecimal totalEarnings = BigDecimal.ZERO;
        BigDecimal platformFee = BigDecimal.ZERO;
        Long salesCount = 0L;
    }

    private static class DailyData {
        BigDecimal revenue = BigDecimal.ZERO;
        Long orderCount = 0L;
    }
}
