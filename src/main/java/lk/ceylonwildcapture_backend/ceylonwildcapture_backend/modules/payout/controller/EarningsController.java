package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.EarningsResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.service.EarningsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * REST controller for earnings operations.
 * Handles earnings calculations and summaries.
 */
@RestController
@RequestMapping("/api/v1/earnings")
@RequiredArgsConstructor
@CrossOrigin
public class EarningsController {

    private final EarningsService earningsService;

    // ------------------------------
    // Get Earnings Summary
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/summary")
    public ResponseEntity<EarningsResponseDto> getEarningsSummary(@PathVariable Long photographerId) {
        return ResponseEntity.ok(earningsService.getEarningsSummary(photographerId));
    }

    // ------------------------------
    // Get Earnings History
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/history")
    public ResponseEntity<Page<EarningsResponseDto>> getEarningsHistory(
            @PathVariable Long photographerId,
            Pageable pageable) {
        return ResponseEntity.ok(earningsService.getEarningsHistory(photographerId, pageable));
    }

    // ------------------------------
    // Get Pending Earnings
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/pending")
    public ResponseEntity<Map<String, Object>> getPendingEarnings(@PathVariable Long photographerId) {
        BigDecimal pendingEarnings = earningsService.calculatePendingEarnings(photographerId);
        return ResponseEntity.ok(Map.of(
                "photographerId", photographerId,
                "pendingEarnings", pendingEarnings
        ));
    }

    // ------------------------------
    // Get Total Earnings
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/total")
    public ResponseEntity<Map<String, Object>> getTotalEarnings(@PathVariable Long photographerId) {
        BigDecimal totalEarnings = earningsService.calculateTotalEarnings(photographerId);
        return ResponseEntity.ok(Map.of(
                "photographerId", photographerId,
                "totalEarnings", totalEarnings
        ));
    }

    // ------------------------------
    // Get Paid Earnings
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/paid")
    public ResponseEntity<Map<String, Object>> getPaidEarnings(@PathVariable Long photographerId) {
        BigDecimal paidEarnings = earningsService.calculatePaidEarnings(photographerId);
        return ResponseEntity.ok(Map.of(
                "photographerId", photographerId,
                "paidEarnings", paidEarnings
        ));
    }

    // ------------------------------
    // Get Earnings for Date Range
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/date-range")
    public ResponseEntity<Map<String, Object>> getEarningsForDateRange(
            @PathVariable Long photographerId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        BigDecimal earnings = earningsService.getEarningsForDateRange(photographerId, startDate, endDate);
        return ResponseEntity.ok(Map.of(
                "photographerId", photographerId,
                "earnings", earnings,
                "startDate", startDate,
                "endDate", endDate
        ));
    }

    // ------------------------------
    // Get Earnings Breakdown
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/breakdown")
    public ResponseEntity<Map<String, Object>> getEarningsBreakdown(@PathVariable Long photographerId) {
        Map<String, BigDecimal> breakdown = earningsService.getEarningsBreakdown(photographerId);
        return ResponseEntity.ok(Map.of(
                "photographerId", photographerId,
                "breakdown", breakdown
        ));
    }

    // ------------------------------
    // Get Most Recent Earnings Snapshot
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/recent-snapshot")
    public ResponseEntity<EarningsResponseDto> getMostRecentEarningsSnapshot(@PathVariable Long photographerId) {
        return earningsService.getMostRecentEarningsSnapshot(photographerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------
    // Get Earnings Statistics
    // ------------------------------
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getEarningsStatistics() {
        return ResponseEntity.ok(earningsService.getEarningsStatistics());
    }

    // ------------------------------
    // Create Earnings Snapshot
    // ------------------------------
    @PostMapping("/photographer/{photographerId}/snapshot")
    public ResponseEntity<EarningsResponseDto> createEarningsSnapshot(@PathVariable Long photographerId) {
        EarningsResponseDto snapshot = earningsService.createEarningsSnapshot(photographerId);
        return ResponseEntity.ok(snapshot);
    }
}
