package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.DashboardAnalyticsDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.DashboardAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * REST controller for dashboard analytics.
 */
@RestController
@RequestMapping("/api/v1/admin/analytics")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class DashboardAnalyticsController {

    private final DashboardAnalyticsService analyticsService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardAnalyticsDto> getDashboardAnalytics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        DashboardAnalyticsDto analytics = analyticsService.getDashboardAnalytics(startDate, endDate);
        return ResponseEntity.ok(analytics);
    }

    @GetMapping("/user-statistics")
    public ResponseEntity<Map<String, Object>> getUserStatistics() {
        Map<String, Object> stats = analyticsService.getUserStatistics();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/sales-statistics")
    public ResponseEntity<Map<String, Object>> getSalesStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<String, Object> stats = analyticsService.getSalesStatistics(startDate, endDate);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/photo-statistics")
    public ResponseEntity<Map<String, Object>> getPhotoStatistics() {
        Map<String, Object> stats = analyticsService.getPhotoStatistics();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/revenue-statistics")
    public ResponseEntity<Map<String, Object>> getRevenueStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<String, Object> stats = analyticsService.getRevenueStatistics(startDate, endDate);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/payout-statistics")
    public ResponseEntity<Map<String, Object>> getPayoutStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<String, Object> stats = analyticsService.getPayoutStatistics(startDate, endDate);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/growth-metrics")
    public ResponseEntity<Map<String, Object>> getGrowthMetrics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<String, Object> metrics = analyticsService.getGrowthMetrics(startDate, endDate);
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/daily-trends")
    public ResponseEntity<Map<String, Object>> getDailyTrends(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<String, Object> trends = analyticsService.getDailyTrends(startDate, endDate);
        return ResponseEntity.ok(trends);
    }

    @GetMapping("/category-performance")
    public ResponseEntity<Map<String, Object>> getCategoryPerformance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<String, Object> performance = analyticsService.getCategoryPerformance(startDate, endDate);
        return ResponseEntity.ok(performance);
    }
}
