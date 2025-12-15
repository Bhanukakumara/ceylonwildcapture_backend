package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.controller;

import jakarta.validation.Valid;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.PayoutAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.PayoutResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.PayoutReviewDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.service.PayoutReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST controller for payout approval operations.
 * Handles admin approval and rejection of payouts.
 */
@RestController
@RequestMapping("/api/v1/payouts/approvals")
@RequiredArgsConstructor
@CrossOrigin
public class PayoutApprovalController {

    private final PayoutReviewService payoutReviewService;

    // ------------------------------
    // Approve Payout
    // ------------------------------
    @PostMapping("/approve")
    public ResponseEntity<PayoutResponseDto> approvePayout(@Valid @RequestBody PayoutReviewDto payoutReviewDto) {
        PayoutResponseDto response = payoutReviewService.approvePayout(payoutReviewDto);
        return ResponseEntity.ok(response);
    }

    // ------------------------------
    // Reject Payout
    // ------------------------------
    @PostMapping("/reject")
    public ResponseEntity<PayoutResponseDto> rejectPayout(@Valid @RequestBody PayoutReviewDto payoutReviewDto) {
        PayoutResponseDto response = payoutReviewService.rejectPayout(payoutReviewDto);
        return ResponseEntity.ok(response);
    }

    // ------------------------------
    // Get Payouts Pending Review
    // ------------------------------
    @GetMapping("/pending")
    public ResponseEntity<Page<PayoutResponseDto>> getPayoutsPendingReview(Pageable pageable) {
        return ResponseEntity.ok(payoutReviewService.getPayoutsPendingReview(pageable));
    }

    // ------------------------------
    // Count Payouts Pending Review
    // ------------------------------
    @GetMapping("/pending/count")
    public ResponseEntity<Map<String, Object>> countPayoutsPendingReview() {
        long count = payoutReviewService.countPayoutsPendingReview();
        return ResponseEntity.ok(Map.of(
                "pendingCount", count
        ));
    }

    // ------------------------------
    // Validate Payout for Approval
    // ------------------------------
    @GetMapping("/{payoutId}/validate")
    public ResponseEntity<Map<String, Object>> validatePayoutForApproval(@PathVariable Long payoutId) {
        java.util.List<String> errors = payoutReviewService.validatePayoutForApproval(payoutId);
        boolean valid = errors.isEmpty();
        return ResponseEntity.ok(Map.of(
                "payoutId", payoutId,
                "valid", valid,
                "errors", errors
        ));
    }

    // ------------------------------
    // Get Review History
    // ------------------------------
    @GetMapping("/{payoutId}/history")
    public ResponseEntity<Page<PayoutAuditDto>> getPayoutReviewHistory(
            @PathVariable Long payoutId,
            Pageable pageable) {
        return ResponseEntity.ok(payoutReviewService.getPayoutReviewHistory(payoutId, pageable));
    }

    // ------------------------------
    // Get Review Statistics
    // ------------------------------
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getReviewStatistics() {
        return ResponseEntity.ok(payoutReviewService.getReviewStatistics());
    }

    // ------------------------------
    // Get Average Review Time
    // ------------------------------
    @GetMapping("/statistics/average-review-time")
    public ResponseEntity<Map<String, Object>> getAverageReviewTime() {
        double averageTime = payoutReviewService.getAverageReviewTime();
        return ResponseEntity.ok(Map.of(
                "averageReviewTimeHours", averageTime
        ));
    }

    // ------------------------------
    // Get Approval Rate
    // ------------------------------
    @GetMapping("/statistics/approval-rate")
    public ResponseEntity<Map<String, Object>> getApprovalRate() {
        double approvalRate = payoutReviewService.getApprovalRate();
        return ResponseEntity.ok(Map.of(
                "approvalRatePercentage", approvalRate
        ));
    }

    // ------------------------------
    // Get Rejection Rate
    // ------------------------------
    @GetMapping("/statistics/rejection-rate")
    public ResponseEntity<Map<String, Object>> getRejectionRate() {
        double rejectionRate = payoutReviewService.getRejectionRate();
        return ResponseEntity.ok(Map.of(
                "rejectionRatePercentage", rejectionRate
        ));
    }
}
