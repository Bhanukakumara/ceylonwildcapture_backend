package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.*;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.AuditType;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditQueryService auditQueryService;
    private final LoginAuditService loginAuditService;
    private final DownloadAuditService downloadAuditService;
    private final AdminActionAuditService adminActionAuditService;

    @GetMapping
    public ResponseEntity<Page<AuditEventResponse>> searchAudits(
            @RequestParam(required = false) AuditType auditType,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long adminId,
            @RequestParam(required = false) Long entityId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
            Pageable pageable) {

        AuditSearchCriteria criteria = AuditSearchCriteria.builder()
                .auditType(auditType)
                .userId(userId)
                .adminId(adminId)
                .entityId(entityId)
                .fromDate(fromDate)
                .toDate(toDate)
                .build();

        return ResponseEntity.ok(auditQueryService.searchAudits(criteria, pageable));
    }

    @GetMapping("/login")
    public ResponseEntity<Page<LoginAuditDto>> getLoginHistory(
            @RequestParam(required = false) Long userId,
            Pageable pageable) {
        if (userId != null) {
            return ResponseEntity.ok(loginAuditService.getLoginHistoryByUser(userId, pageable));
        }
        // If no user specified, could return all or error. Returning all for admin
        // view.
        return ResponseEntity.ok(loginAuditService.getLoginHistoryByUser(null, pageable));
    }

    @GetMapping("/download")
    public ResponseEntity<Page<DownloadAuditDto>> getDownloadHistory(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long licenseId,
            Pageable pageable) {
        if (userId != null) {
            return ResponseEntity.ok(downloadAuditService.getDownloadsByUser(userId, pageable));
        }
        if (licenseId != null) {
            return ResponseEntity.ok(downloadAuditService.getDownloadsByLicense(licenseId, pageable));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/admin")
    public ResponseEntity<Page<AdminActionAuditDto>> getAdminActions(
            @RequestParam(required = false) Long adminId,
            Pageable pageable) {
        if (adminId != null) {
            return ResponseEntity.ok(adminActionAuditService.getActionsByAdmin(adminId, pageable));
        }
        return ResponseEntity.ok(Page.empty());
    }
}
