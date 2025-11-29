package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.PayoutReviewDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.PayoutReviewService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.entity.Payout;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;

/**
 * REST controller for payout review operations.
 */
@RestController
@RequestMapping("/api/v1/admin/payouts")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class PayoutReviewController {

    private final PayoutReviewService payoutReviewService;

    @PostMapping("/review")
    public ResponseEntity<Payout> reviewPayout(
            @Valid @RequestBody PayoutReviewDto reviewDto,
            @RequestAttribute("userId") Long adminId) {
        Payout payout = payoutReviewService.reviewPayout(reviewDto, adminId);
        return ResponseEntity.ok(payout);
    }

    @PostMapping("/{payoutId}/approve")
    public ResponseEntity<Payout> approvePayout(
            @PathVariable Long payoutId,
            @RequestAttribute("userId") Long adminId) {
        Payout approvedPayout = payoutReviewService.approvePayout(payoutId, adminId);
        return ResponseEntity.ok(approvedPayout);
    }

    @PostMapping("/{payoutId}/reject")
    public ResponseEntity<Payout> rejectPayout(
            @PathVariable Long payoutId,
            @RequestParam String reason,
            @RequestAttribute("userId") Long adminId) {
        Payout rejectedPayout = payoutReviewService.rejectPayout(payoutId, reason, adminId);
        return ResponseEntity.ok(rejectedPayout);
    }

    @PostMapping("/{payoutId}/hold")
    public ResponseEntity<Payout> holdPayout(
            @PathVariable Long payoutId,
            @RequestParam String reason,
            @RequestAttribute("userId") Long adminId) {
        Payout heldPayout = payoutReviewService.holdPayout(payoutId, reason, adminId);
        return ResponseEntity.ok(heldPayout);
    }

    @PostMapping("/{payoutId}/release")
    public ResponseEntity<Payout> releasePayout(
            @PathVariable Long payoutId,
            @RequestAttribute("userId") Long adminId) {
        Payout releasedPayout = payoutReviewService.releasePayout(payoutId, adminId);
        return ResponseEntity.ok(releasedPayout);
    }

    @GetMapping("/pending")
    public ResponseEntity<Page<Payout>> getPendingPayouts(Pageable pageable) {
        Page<Payout> pendingPayouts = payoutReviewService.getPendingPayouts(pageable);
        return ResponseEntity.ok(pendingPayouts);
    }

    @GetMapping("/approved")
    public ResponseEntity<Page<Payout>> getApprovedPayouts(Pageable pageable) {
        Page<Payout> approvedPayouts = payoutReviewService.getApprovedPayouts(pageable);
        return ResponseEntity.ok(approvedPayouts);
    }

    @GetMapping("/rejected")
    public ResponseEntity<Page<Payout>> getRejectedPayouts(Pageable pageable) {
        Page<Payout> rejectedPayouts = payoutReviewService.getRejectedPayouts(pageable);
        return ResponseEntity.ok(rejectedPayouts);
    }

    @GetMapping("/on-hold")
    public ResponseEntity<Page<Payout>> getHeldPayouts(Pageable pageable) {
        Page<Payout> heldPayouts = payoutReviewService.getHeldPayouts(pageable);
        return ResponseEntity.ok(heldPayouts);
    }

    @GetMapping("/photographer/{photographerId}")
    public ResponseEntity<Page<Payout>> getPhotographerPayouts(
            @PathVariable Long photographerId,
            Pageable pageable) {
        Page<Payout> payouts = payoutReviewService.getPhotographerPayouts(photographerId, pageable);
        return ResponseEntity.ok(payouts);
    }

    @GetMapping("/threshold")
    public ResponseEntity<Page<Payout>> getPayoutsAboveThreshold(
            @RequestParam BigDecimal threshold,
            Pageable pageable) {
        Page<Payout> payouts = payoutReviewService.getPayoutsAboveThreshold(threshold, pageable);
        return ResponseEntity.ok(payouts);
    }

    @GetMapping("/{payoutId}")
    public ResponseEntity<Payout> getPayoutDetails(@PathVariable Long payoutId) {
        Payout payout = payoutReviewService.getPayoutDetails(payoutId);
        return ResponseEntity.ok(payout);
    }
}
