package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.AuditEventResponse;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.filter.AuditSearchCriteria;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST controller for general audit operations.
 * Handles system-wide audit log retrieval and filtering.
 */
@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
@CrossOrigin
public class AuditController {

    private final AuditService auditService;

    // ------------------------------
    // Get All Audits
    // ------------------------------
    @GetMapping
    public ResponseEntity<Page<AuditEventResponse>> getAllAudits(Pageable pageable) {
        return ResponseEntity.ok(auditService.getAllAudits(pageable));
    }

    // ------------------------------
    // Get Audit by ID
    // ------------------------------
    @GetMapping("/{auditId}")
    public ResponseEntity<AuditEventResponse> getAuditById(@PathVariable Long auditId) {
        return auditService.getAuditById(auditId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------
    // Search Audits with Criteria
    // ------------------------------
    @PostMapping("/search")
    public ResponseEntity<Page<AuditEventResponse>> searchAudits(
            @RequestBody AuditSearchCriteria criteria,
            Pageable pageable) {
        return ResponseEntity.ok(auditService.searchAudits(criteria, pageable));
    }

    // ------------------------------
    // Get Audits by Actor
    // ------------------------------
    @GetMapping("/actor/{actorId}")
    public ResponseEntity<Page<AuditEventResponse>> getAuditsByActor(
            @PathVariable Long actorId,
            Pageable pageable) {
        return ResponseEntity.ok(auditService.getAuditsByActor(actorId, pageable));
    }

    // ------------------------------
    // Get Audits by Entity
    // ------------------------------
    @GetMapping("/entity/{entityId}")
    public ResponseEntity<Page<AuditEventResponse>> getAuditsByEntity(
            @PathVariable Long entityId,
            Pageable pageable) {
        return ResponseEntity.ok(auditService.getAuditsByEntity(entityId, pageable));
    }

    // ------------------------------
    // Export Audits as CSV
    // ------------------------------
    @PostMapping("/export/csv")
    public ResponseEntity<String> exportAuditsCsv(@RequestBody AuditSearchCriteria criteria) {
        String csvContent = auditService.exportAuditsCsv(criteria);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"audits.csv\"")
                .header("Content-Type", "text/csv")
                .body(csvContent);
    }

    // ------------------------------
    // Export Audits as JSON
    // ------------------------------
    @PostMapping("/export/json")
    public ResponseEntity<String> exportAuditsJson(@RequestBody AuditSearchCriteria criteria) {
        String jsonContent = auditService.exportAuditsJson(criteria);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"audits.json\"")
                .header("Content-Type", "application/json")
                .body(jsonContent);
    }

    // ------------------------------
    // Get Audit Statistics
    // ------------------------------
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getAuditStatistics() {
        return ResponseEntity.ok(auditService.getAuditStatistics());
    }

    // ------------------------------
    // Delete Old Audits
    // ------------------------------
    @DeleteMapping("/cleanup")
    public ResponseEntity<Map<String, Object>> deleteOldAudits(
            @RequestParam(defaultValue = "90") int daysToKeep) {
        long deletedCount = auditService.deleteOldAudits(daysToKeep);
        return ResponseEntity.ok(Map.of(
                "message", "Old audit records deleted",
                "deletedCount", deletedCount,
                "daysKept", daysToKeep
        ));
    }
}
