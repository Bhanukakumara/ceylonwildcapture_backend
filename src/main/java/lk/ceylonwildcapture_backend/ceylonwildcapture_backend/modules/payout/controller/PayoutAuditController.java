package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.PayoutStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.PayoutAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.service.PayoutAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * REST controller for payout audit operations.
 * Handles audit log retrieval and tracking.
 */
@RestController
@RequestMapping("/api/v1/payouts/audits")
@RequiredArgsConstructor
@CrossOrigin
public class PayoutAuditController {

    private final PayoutAuditService payoutAuditService;

    // ------------------------------
    // Get Audit by ID
    // ------------------------------
    @GetMapping("/{auditId}")
    public ResponseEntity<PayoutAuditDto> getAuditById(@PathVariable Long auditId) {
        return payoutAuditService.getAuditById(auditId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------
    // Get Audits by Payout
    // ------------------------------
    @GetMapping("/payout/{payoutId}")
    public ResponseEntity<Page<PayoutAuditDto>> getAuditsByPayout(
            @PathVariable Long payoutId,
            Pageable pageable) {
        return ResponseEntity.ok(payoutAuditService.getAuditsByPayout(payoutId, pageable));
    }

    // ------------------------------
    // Get Audits by Admin
    // ------------------------------
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<Page<PayoutAuditDto>> getAuditsByAdmin(
            @PathVariable Long adminId,
            Pageable pageable) {
        return ResponseEntity.ok(payoutAuditService.getAuditsByAdmin(adminId, pageable));
    }

    // ------------------------------
    // Get Audits by Action
    // ------------------------------
    @GetMapping("/action/{action}")
    public ResponseEntity<Page<PayoutAuditDto>> getAuditsByAction(
            @PathVariable String action,
            Pageable pageable) {
        return ResponseEntity.ok(payoutAuditService.getAuditsByAction(action, pageable));
    }

    // ------------------------------
    // Get Audits by New Status
    // ------------------------------
    @GetMapping("/status/{newStatus}")
    public ResponseEntity<Page<PayoutAuditDto>> getAuditsByNewStatus(
            @PathVariable PayoutStatus newStatus,
            Pageable pageable) {
        return ResponseEntity.ok(payoutAuditService.getAuditsByNewStatus(newStatus, pageable));
    }

    // ------------------------------
    // Get Audits by Date Range
    // ------------------------------
    @GetMapping("/date-range")
    public ResponseEntity<Page<PayoutAuditDto>> getAuditsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate,
            Pageable pageable) {
        return ResponseEntity.ok(payoutAuditService.getAuditsByDateRange(startDate, endDate, pageable));
    }

    // ------------------------------
    // Get Payout Audit History
    // ------------------------------
    @GetMapping("/payout/{payoutId}/full-history")
    public ResponseEntity<List<PayoutAuditDto>> getPayoutAuditHistory(@PathVariable Long payoutId) {
        List<PayoutAuditDto> history = payoutAuditService.getPayoutAuditHistory(payoutId);
        return ResponseEntity.ok(history);
    }

    // ------------------------------
    // Count Audits for Payout
    // ------------------------------
    @GetMapping("/payout/{payoutId}/count")
    public ResponseEntity<Map<String, Object>> countAuditsForPayout(@PathVariable Long payoutId) {
        long count = payoutAuditService.countAuditsForPayout(payoutId);
        return ResponseEntity.ok(Map.of(
                "payoutId", payoutId,
                "auditCount", count
        ));
    }

    // ------------------------------
    // Count Audits by Admin
    // ------------------------------
    @GetMapping("/admin/{adminId}/count")
    public ResponseEntity<Map<String, Object>> countAuditsByAdmin(@PathVariable Long adminId) {
        long count = payoutAuditService.countAuditsByAdmin(adminId);
        return ResponseEntity.ok(Map.of(
                "adminId", adminId,
                "auditCount", count
        ));
    }

    // ------------------------------
    // Get Audit Statistics
    // ------------------------------
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getAuditStatistics() {
        return ResponseEntity.ok(payoutAuditService.getAuditStatistics());
    }

    // ------------------------------
    // Export Audits as CSV
    // ------------------------------
    @GetMapping("/payout/{payoutId}/export/csv")
    public ResponseEntity<String> exportAuditsCsv(@PathVariable Long payoutId) {
        String csvContent = payoutAuditService.exportAuditsCsv(payoutId);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"payout-audits.csv\"")
                .header("Content-Type", "text/csv")
                .body(csvContent);
    }
}
