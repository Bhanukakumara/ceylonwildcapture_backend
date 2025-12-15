package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.controller;

import jakarta.validation.Valid;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.PayoutRequestDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.PayoutResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.service.PayoutRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * REST controller for payout request operations.
 * Handles submission and validation of payout requests.
 */
@RestController
@RequestMapping("/api/v1/payouts/requests")
@RequiredArgsConstructor
@CrossOrigin
public class PayoutRequestController {

    private final PayoutRequestService payoutRequestService;

    // ------------------------------
    // Submit Payout Request
    // ------------------------------
    @PostMapping
    public ResponseEntity<PayoutResponseDto> submitPayoutRequest(@Valid @RequestBody PayoutRequestDto payoutRequestDto) {
        PayoutResponseDto response = payoutRequestService.submitPayoutRequest(payoutRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ------------------------------
    // Validate Payout Eligibility
    // ------------------------------
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validatePayoutEligibility(
            @RequestParam Long photographerId,
            @RequestParam BigDecimal requestedAmount) {
        boolean eligible = payoutRequestService.validatePayoutEligibility(photographerId, requestedAmount);
        return ResponseEntity.ok(Map.of(
                "eligible", eligible,
                "photographerId", photographerId,
                "requestedAmount", requestedAmount
        ));
    }

    // ------------------------------
    // Check Pending Requests
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/pending")
    public ResponseEntity<Map<String, Object>> checkPendingPayoutRequests(@PathVariable Long photographerId) {
        boolean hasPending = payoutRequestService.hasPendingPayoutRequests(photographerId);
        return ResponseEntity.ok(Map.of(
                "photographerId", photographerId,
                "hasPendingRequests", hasPending
        ));
    }

    // ------------------------------
    // Cancel Payout Request
    // ------------------------------
    @DeleteMapping("/{payoutId}")
    public ResponseEntity<PayoutResponseDto> cancelPayoutRequest(@PathVariable Long payoutId) {
        PayoutResponseDto response = payoutRequestService.cancelPayoutRequest(payoutId);
        return ResponseEntity.ok(response);
    }

    // ------------------------------
    // Validate Request DTO
    // ------------------------------
    @PostMapping("/validate-request")
    public ResponseEntity<Map<String, Object>> validatePayoutRequest(@Valid @RequestBody PayoutRequestDto payoutRequestDto) {
        java.util.List<String> errors = payoutRequestService.getPayoutRequestValidationErrors(payoutRequestDto);
        boolean valid = errors.isEmpty();
        return ResponseEntity.ok(Map.of(
                "valid", valid,
                "errors", errors
        ));
    }

    // ------------------------------
    // Get Minimum Payout Amount
    // ------------------------------
    @GetMapping("/minimum-amount")
    public ResponseEntity<Map<String, Object>> getMinimumPayoutAmount() {
        BigDecimal minimumAmount = payoutRequestService.getMinimumPayoutAmount();
        return ResponseEntity.ok(Map.of(
                "minimumAmount", minimumAmount
        ));
    }

    // ------------------------------
    // Get Maximum Payout Amount
    // ------------------------------
    @GetMapping("/maximum-amount")
    public ResponseEntity<Map<String, Object>> getMaximumPayoutAmount() {
        BigDecimal maximumAmount = payoutRequestService.getMaximumPayoutAmount();
        return ResponseEntity.ok(Map.of(
                "maximumAmount", maximumAmount
        ));
    }

    // ------------------------------
    // Check Amount Validity
    // ------------------------------
    @GetMapping("/amount-valid")
    public ResponseEntity<Map<String, Object>> checkAmountValidity(@RequestParam BigDecimal amount) {
        boolean valid = payoutRequestService.isPayoutAmountValid(amount);
        return ResponseEntity.ok(Map.of(
                "amount", amount,
                "valid", valid
        ));
    }
}
