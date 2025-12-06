package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.AdminActionAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.EntityType;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.AdminActionAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * REST controller for admin action audit operations.
 * Handles moderation and administrative action tracking.
 */
@RestController
@RequestMapping("/api/v1/audit/admin-actions")
@RequiredArgsConstructor
@CrossOrigin
public class AdminActionAuditController {

    private final AdminActionAuditService adminActionAuditService;

    // ------------------------------
    // Get Admin Action Audit by ID
    // ------------------------------
    @GetMapping("/{auditId}")
    public ResponseEntity<AdminActionAuditDto> getAdminActionAuditById(@PathVariable Long auditId) {
        return adminActionAuditService.getAdminActionAuditById(auditId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------
    // Get Actions by Admin
    // ------------------------------
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<Page<AdminActionAuditDto>> getActionsByAdmin(
            @PathVariable Long adminId,
            Pageable pageable) {
        return ResponseEntity.ok(adminActionAuditService.getActionsByAdmin(adminId, pageable));
    }

    // ------------------------------
    // Get Actions on Entity
    // ------------------------------
    @GetMapping("/entity/{entityId}")
    public ResponseEntity<Page<AdminActionAuditDto>> getActionsByEntity(
            @PathVariable Long entityId,
            Pageable pageable) {
        return ResponseEntity.ok(adminActionAuditService.getActionsByEntity(entityId, pageable));
    }

    // ------------------------------
    // Get Actions by Entity Type
    // ------------------------------
    @GetMapping("/entity-type/{entityType}")
    public ResponseEntity<Page<AdminActionAuditDto>> getActionsByEntityType(
            @PathVariable EntityType entityType,
            Pageable pageable) {
        return ResponseEntity.ok(adminActionAuditService.getActionsByEntityType(entityType, pageable));
    }

    // ------------------------------
    // Get Actions by Date Range
    // ------------------------------
    @GetMapping("/date-range")
    public ResponseEntity<Page<AdminActionAuditDto>> getActionsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate,
            Pageable pageable) {
        return ResponseEntity.ok(adminActionAuditService.getActionsByDateRange(startDate, endDate, pageable));
    }

    // ------------------------------
    // Get Actions by Admin and Entity Type
    // ------------------------------
    @GetMapping("/admin/{adminId}/entity-type/{entityType}")
    public ResponseEntity<Page<AdminActionAuditDto>> getActionsByAdminAndEntityType(
            @PathVariable Long adminId,
            @PathVariable EntityType entityType,
            Pageable pageable) {
        return ResponseEntity.ok(adminActionAuditService.getActionsByAdminAndEntityType(adminId, entityType, pageable));
    }

    // ------------------------------
    // Count Actions by Admin
    // ------------------------------
    @GetMapping("/admin/{adminId}/count")
    public ResponseEntity<Map<String, Object>> countActionsByAdmin(@PathVariable Long adminId) {
        long count = adminActionAuditService.countActionsByAdmin(adminId);
        return ResponseEntity.ok(Map.of(
                "adminId", adminId,
                "actionCount", count
        ));
    }

    // ------------------------------
    // Count Actions by Entity Type
    // ------------------------------
    @GetMapping("/entity-type/{entityType}/count")
    public ResponseEntity<Map<String, Object>> countActionsByEntityType(@PathVariable EntityType entityType) {
        long count = adminActionAuditService.countActionsByEntityType(entityType);
        return ResponseEntity.ok(Map.of(
                "entityType", entityType,
                "actionCount", count
        ));
    }

    // ------------------------------
    // Get Admin Action Statistics
    // ------------------------------
    @GetMapping("/admin/{adminId}/statistics")
    public ResponseEntity<Map<String, Object>> getAdminActionStatistics(@PathVariable Long adminId) {
        return ResponseEntity.ok(adminActionAuditService.getAdminActionStatistics(adminId));
    }

    // ------------------------------
    // Get Entity Action History
    // ------------------------------
    @GetMapping("/entity-history/{entityType}/{entityId}")
    public ResponseEntity<Page<AdminActionAuditDto>> getEntityActionHistory(
            @PathVariable EntityType entityType,
            @PathVariable Long entityId,
            Pageable pageable) {
        return ResponseEntity.ok(adminActionAuditService.getEntityActionHistory(entityType, entityId, pageable));
    }
}
