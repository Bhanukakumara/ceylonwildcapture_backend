package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.AuditLogDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * REST controller for audit log operations.
 */
@RestController
@RequestMapping("/api/v1/admin/audit-logs")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<Page<AuditLogDto>> getAllAuditLogs(Pageable pageable) {
        Page<AuditLogDto> auditLogs = auditLogService.getAllAuditLogs(pageable);
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/event-type/{eventType}")
    public ResponseEntity<Page<AuditLogDto>> getAuditLogsByEventType(
            @PathVariable String eventType,
            Pageable pageable) {
        Page<AuditLogDto> auditLogs = auditLogService.getAuditLogsByEventType(eventType, pageable);
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/entity-type/{entityType}")
    public ResponseEntity<Page<AuditLogDto>> getAuditLogsByEntityType(
            @PathVariable String entityType,
            Pageable pageable) {
        Page<AuditLogDto> auditLogs = auditLogService.getAuditLogsByEntityType(entityType, pageable);
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<AuditLogDto>> getAuditLogsByUser(
            @PathVariable Long userId,
            Pageable pageable) {
        Page<AuditLogDto> auditLogs = auditLogService.getAuditLogsByUser(userId, pageable);
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/date-range")
    public ResponseEntity<Page<AuditLogDto>> getAuditLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable) {
        Page<AuditLogDto> auditLogs = auditLogService.getAuditLogsByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    public ResponseEntity<Page<AuditLogDto>> getAuditLogsForEntity(
            @PathVariable String entityType,
            @PathVariable Long entityId,
            Pageable pageable) {
        Page<AuditLogDto> auditLogs = auditLogService.getAuditLogsForEntity(entityType, entityId, pageable);
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/downloads")
    public ResponseEntity<Page<AuditLogDto>> getDownloadAuditLogs(Pageable pageable) {
        Page<AuditLogDto> auditLogs = auditLogService.getDownloadAuditLogs(pageable);
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/logins")
    public ResponseEntity<Page<AuditLogDto>> getLoginAuditLogs(Pageable pageable) {
        Page<AuditLogDto> auditLogs = auditLogService.getLoginAuditLogs(pageable);
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/failed-logins")
    public ResponseEntity<Page<AuditLogDto>> getFailedLoginAttempts(Pageable pageable) {
        Page<AuditLogDto> auditLogs = auditLogService.getFailedLoginAttempts(pageable);
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/action/{action}")
    public ResponseEntity<Page<AuditLogDto>> getAuditLogsByAction(
            @PathVariable String action,
            Pageable pageable) {
        Page<AuditLogDto> auditLogs = auditLogService.getAuditLogsByAction(action, pageable);
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AuditLogDto>> searchAuditLogs(
            @RequestParam String searchTerm,
            Pageable pageable) {
        Page<AuditLogDto> auditLogs = auditLogService.searchAuditLogs(searchTerm, pageable);
        return ResponseEntity.ok(auditLogs);
    }

    @DeleteMapping("/cleanup")
    public ResponseEntity<Long> deleteOldAuditLogs(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime olderThan,
            @RequestAttribute("userId") Long adminId) {
        long deletedCount = auditLogService.deleteOldAuditLogs(olderThan);
        return ResponseEntity.ok(deletedCount);
    }
}
