package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.PayoutStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.PayoutResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.filter.PayoutSearchCriteria;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.service.PayoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * REST controller for payout operations.
 * Handles payout history retrieval and status queries.
 */
@RestController
@RequestMapping("/api/v1/payouts")
@RequiredArgsConstructor
@CrossOrigin
public class PayoutController {

    private final PayoutService payoutService;

    // ------------------------------
    // Get Payout by ID
    // ------------------------------
    @GetMapping("/{payoutId}")
    public ResponseEntity<PayoutResponseDto> getPayoutById(@PathVariable Long payoutId) {
        return payoutService.getPayoutById(payoutId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------
    // Get Payout by Reference
    // ------------------------------
    @GetMapping("/reference/{payoutReference}")
    public ResponseEntity<PayoutResponseDto> getPayoutByReference(@PathVariable String payoutReference) {
        return payoutService.getPayoutByReference(payoutReference)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------
    // Get All Payouts
    // ------------------------------
    @GetMapping
    public ResponseEntity<Page<PayoutResponseDto>> getAllPayouts(Pageable pageable) {
        return ResponseEntity.ok(payoutService.getAllPayouts(pageable));
    }

    // ------------------------------
    // Get Payouts by Photographer
    // ------------------------------
    @GetMapping("/photographer/{photographerId}")
    public ResponseEntity<Page<PayoutResponseDto>> getPayoutsByPhotographer(
            @PathVariable Long photographerId,
            Pageable pageable) {
        return ResponseEntity.ok(payoutService.getPayoutsByPhotographer(photographerId, pageable));
    }

    // ------------------------------
    // Get Payouts by Status
    // ------------------------------
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<PayoutResponseDto>> getPayoutsByStatus(
            @PathVariable PayoutStatus status,
            Pageable pageable) {
        return ResponseEntity.ok(payoutService.getPayoutsByStatus(status, pageable));
    }

    // ------------------------------
    // Get Payouts by Date Range
    // ------------------------------
    @GetMapping("/date-range")
    public ResponseEntity<Page<PayoutResponseDto>> getPayoutsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate,
            Pageable pageable) {
        return ResponseEntity.ok(payoutService.getPayoutsByDateRange(startDate, endDate, pageable));
    }

    // ------------------------------
    // Search Payouts with Criteria
    // ------------------------------
    @PostMapping("/search")
    public ResponseEntity<Page<PayoutResponseDto>> searchPayouts(
            @RequestBody PayoutSearchCriteria criteria,
            Pageable pageable) {
        return ResponseEntity.ok(payoutService.searchPayouts(criteria, pageable));
    }

    // ------------------------------
    // Get Payout History
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/history")
    public ResponseEntity<Page<PayoutResponseDto>> getPayoutHistory(
            @PathVariable Long photographerId,
            Pageable pageable) {
        return ResponseEntity.ok(payoutService.getPayoutHistory(photographerId, pageable));
    }

    // ------------------------------
    // Get Pending Payouts
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/pending")
    public ResponseEntity<Page<PayoutResponseDto>> getPendingPayouts(
            @PathVariable Long photographerId,
            Pageable pageable) {
        return ResponseEntity.ok(payoutService.getPendingPayouts(photographerId, pageable));
    }

    // ------------------------------
    // Get Completed Payouts
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/completed")
    public ResponseEntity<Page<PayoutResponseDto>> getCompletedPayouts(
            @PathVariable Long photographerId,
            Pageable pageable) {
        return ResponseEntity.ok(payoutService.getCompletedPayouts(photographerId, pageable));
    }

    // ------------------------------
    // Get Most Recent Payout
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/recent")
    public ResponseEntity<PayoutResponseDto> getMostRecentPayoutForPhotographer(@PathVariable Long photographerId) {
        return payoutService.getMostRecentPayoutForPhotographer(photographerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------
    // Get Payout Statistics
    // ------------------------------
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getPayoutStatistics() {
        return ResponseEntity.ok(payoutService.getPayoutStatistics());
    }

    // ------------------------------
    // Count Payouts by Status
    // ------------------------------
    @GetMapping("/status/{status}/count")
    public ResponseEntity<Map<String, Object>> countPayoutsByStatus(@PathVariable PayoutStatus status) {
        long count = payoutService.countPayoutsByStatus(status);
        return ResponseEntity.ok(Map.of(
                "status", status,
                "count", count
        ));
    }

    // ------------------------------
    // Count Payouts for Photographer
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/count")
    public ResponseEntity<Map<String, Object>> countPayoutsForPhotographer(@PathVariable Long photographerId) {
        long count = payoutService.countPayoutsForPhotographer(photographerId);
        return ResponseEntity.ok(Map.of(
                "photographerId", photographerId,
                "count", count
        ));
    }
}
