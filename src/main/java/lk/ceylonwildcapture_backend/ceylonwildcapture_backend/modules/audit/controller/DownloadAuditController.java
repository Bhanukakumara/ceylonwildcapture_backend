package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.DownloadAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.DownloadAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * REST controller for download audit operations.
 * Handles download history and statistics retrieval.
 */
@RestController
@RequestMapping("/api/v1/audit/downloads")
@RequiredArgsConstructor
@CrossOrigin
public class DownloadAuditController {

    private final DownloadAuditService downloadAuditService;

    // ------------------------------
    // Get Download Audit by ID
    // ------------------------------
    @GetMapping("/{auditId}")
    public ResponseEntity<DownloadAuditDto> getDownloadAuditById(@PathVariable Long auditId) {
        return downloadAuditService.getDownloadAuditById(auditId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------
    // Get Downloads by User
    // ------------------------------
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<DownloadAuditDto>> getDownloadsByUser(
            @PathVariable Long userId,
            Pageable pageable) {
        return ResponseEntity.ok(downloadAuditService.getDownloadsByUser(userId, pageable));
    }

    // ------------------------------
    // Get Successful Downloads by User
    // ------------------------------
    @GetMapping("/user/{userId}/successful")
    public ResponseEntity<Page<DownloadAuditDto>> getSuccessfulDownloadsByUser(
            @PathVariable Long userId,
            Pageable pageable) {
        return ResponseEntity.ok(downloadAuditService.getSuccessfulDownloadsByUser(userId, pageable));
    }

    // ------------------------------
    // Get Downloads by Date Range
    // ------------------------------
    @GetMapping("/date-range")
    public ResponseEntity<Page<DownloadAuditDto>> getDownloadsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate,
            Pageable pageable) {
        return ResponseEntity.ok(downloadAuditService.getDownloadsByDateRange(startDate, endDate, pageable));
    }

    // ------------------------------
    // Get Downloads by Country
    // ------------------------------
    @GetMapping("/country/{country}")
    public ResponseEntity<Page<DownloadAuditDto>> getDownloadsByCountry(
            @PathVariable String country,
            Pageable pageable) {
        return ResponseEntity.ok(downloadAuditService.getDownloadsByCountry(country, pageable));
    }

    // ------------------------------
    // Count Downloads for User
    // ------------------------------
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Map<String, Object>> countDownloadsForUser(@PathVariable Long userId) {
        long count = downloadAuditService.countDownloadsForUser(userId);
        return ResponseEntity.ok(Map.of(
                "userId", userId,
                "downloadCount", count
        ));
    }

    // ------------------------------
    // Get Most Recent Download
    // ------------------------------
    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<DownloadAuditDto> getMostRecentDownloadForUser(@PathVariable Long userId) {
        return downloadAuditService.getMostRecentDownloadForUser(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------
    // Get Download Statistics
    // ------------------------------
    @GetMapping("/user/{userId}/statistics")
    public ResponseEntity<Map<String, Object>> getDownloadStatistics(@PathVariable Long userId) {
        return ResponseEntity.ok(downloadAuditService.getDownloadStatistics(userId));
    }
}
