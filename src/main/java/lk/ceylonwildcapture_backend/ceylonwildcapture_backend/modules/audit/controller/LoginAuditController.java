package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.LoginAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.LoginAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * REST controller for login audit operations.
 * Handles login activity tracking and security monitoring.
 */
@RestController
@RequestMapping("/api/v1/audit/logins")
@RequiredArgsConstructor
@CrossOrigin
public class LoginAuditController {

    private final LoginAuditService loginAuditService;

    // ------------------------------
    // Get Login Audit by ID
    // ------------------------------
    @GetMapping("/{auditId}")
    public ResponseEntity<LoginAuditDto> getLoginAuditById(@PathVariable Long auditId) {
        return loginAuditService.getLoginAuditById(auditId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------
    // Get Logins by User
    // ------------------------------
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<LoginAuditDto>> getLoginsByUser(
            @PathVariable Long userId,
            Pageable pageable) {
        return ResponseEntity.ok(loginAuditService.getLoginsByUser(userId, pageable));
    }

    // ------------------------------
    // Get Successful Logins by User
    // ------------------------------
    @GetMapping("/user/{userId}/successful")
    public ResponseEntity<Page<LoginAuditDto>> getSuccessfulLoginsByUser(
            @PathVariable Long userId,
            Pageable pageable) {
        return ResponseEntity.ok(loginAuditService.getSuccessfulLoginsByUser(userId, pageable));
    }

    // ------------------------------
    // Get Failed Logins by User
    // ------------------------------
    @GetMapping("/user/{userId}/failed")
    public ResponseEntity<Page<LoginAuditDto>> getFailedLoginsByUser(
            @PathVariable Long userId,
            Pageable pageable) {
        return ResponseEntity.ok(loginAuditService.getFailedLoginsByUser(userId, pageable));
    }

    // ------------------------------
    // Get Logins by Date Range
    // ------------------------------
    @GetMapping("/date-range")
    public ResponseEntity<Page<LoginAuditDto>> getLoginsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate,
            Pageable pageable) {
        return ResponseEntity.ok(loginAuditService.getLoginsByDateRange(startDate, endDate, pageable));
    }

    // ------------------------------
    // Get Logins by Result
    // ------------------------------
    @GetMapping("/result/{result}")
    public ResponseEntity<Page<LoginAuditDto>> getLoginsByResult(
            @PathVariable ActionResult result,
            Pageable pageable) {
        return ResponseEntity.ok(loginAuditService.getLoginsByResult(result, pageable));
    }

    // ------------------------------
    // Get Logins by Country
    // ------------------------------
    @GetMapping("/country/{country}")
    public ResponseEntity<Page<LoginAuditDto>> getLoginsByCountry(
            @PathVariable String country,
            Pageable pageable) {
        return ResponseEntity.ok(loginAuditService.getLoginsByCountry(country, pageable));
    }

    // ------------------------------
    // Count Login Attempts
    // ------------------------------
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Map<String, Object>> countLoginAttemptsForUser(@PathVariable Long userId) {
        long totalAttempts = loginAuditService.countLoginAttemptsForUser(userId);
        long failedAttempts = loginAuditService.countFailedLoginAttemptsForUser(userId);
        return ResponseEntity.ok(Map.of(
                "userId", userId,
                "totalAttempts", totalAttempts,
                "failedAttempts", failedAttempts,
                "successfulAttempts", totalAttempts - failedAttempts
        ));
    }

    // ------------------------------
    // Get Most Recent Login
    // ------------------------------
    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<LoginAuditDto> getMostRecentLoginForUser(@PathVariable Long userId) {
        return loginAuditService.getMostRecentLoginForUser(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------
    // Get Login Statistics
    // ------------------------------
    @GetMapping("/user/{userId}/statistics")
    public ResponseEntity<Map<String, Object>> getLoginStatistics(@PathVariable Long userId) {
        return ResponseEntity.ok(loginAuditService.getLoginStatistics(userId));
    }

    // ------------------------------
    // Check Suspicious Activity
    // ------------------------------
    @GetMapping("/user/{userId}/suspicious")
    public ResponseEntity<Map<String, Object>> checkSuspiciousActivity(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "5") int failedAttemptsThreshold,
            @RequestParam(defaultValue = "15") int timeWindowMinutes) {
        boolean suspicious = loginAuditService.hasSuspiciousActivity(userId, failedAttemptsThreshold, timeWindowMinutes);
        return ResponseEntity.ok(Map.of(
                "userId", userId,
                "suspicious", suspicious,
                "threshold", failedAttemptsThreshold,
                "timeWindowMinutes", timeWindowMinutes
        ));
    }
}
