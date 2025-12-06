package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.UserActivityAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.UserActivityAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * REST controller for user activity audit operations.
 * Handles user profile changes, password resets, and other user-initiated actions.
 */
@RestController
@RequestMapping("/api/v1/audit/user-activities")
@RequiredArgsConstructor
@CrossOrigin
public class UserActivityAuditController {

    private final UserActivityAuditService userActivityAuditService;

    // ------------------------------
    // Get User Activity Audit by ID
    // ------------------------------
    @GetMapping("/{auditId}")
    public ResponseEntity<UserActivityAuditDto> getUserActivityAuditById(@PathVariable Long auditId) {
        return userActivityAuditService.getUserActivityAuditById(auditId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------
    // Get Activities by User
    // ------------------------------
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<UserActivityAuditDto>> getActivitiesByUser(
            @PathVariable Long userId,
            Pageable pageable) {
        return ResponseEntity.ok(userActivityAuditService.getActivitiesByUser(userId, pageable));
    }

    // ------------------------------
    // Get Activities by Action
    // ------------------------------
    @GetMapping("/action/{action}")
    public ResponseEntity<Page<UserActivityAuditDto>> getActivitiesByAction(
            @PathVariable String action,
            Pageable pageable) {
        return ResponseEntity.ok(userActivityAuditService.getActivitiesByAction(action, pageable));
    }

    // ------------------------------
    // Get Activities by Result
    // ------------------------------
    @GetMapping("/result/{result}")
    public ResponseEntity<Page<UserActivityAuditDto>> getActivitiesByResult(
            @PathVariable ActionResult result,
            Pageable pageable) {
        return ResponseEntity.ok(userActivityAuditService.getActivitiesByResult(result, pageable));
    }

    // ------------------------------
    // Get Activities by Date Range
    // ------------------------------
    @GetMapping("/date-range")
    public ResponseEntity<Page<UserActivityAuditDto>> getActivitiesByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate,
            Pageable pageable) {
        return ResponseEntity.ok(userActivityAuditService.getActivitiesByDateRange(startDate, endDate, pageable));
    }

    // ------------------------------
    // Get Activities by User and Action
    // ------------------------------
    @GetMapping("/user/{userId}/action/{action}")
    public ResponseEntity<Page<UserActivityAuditDto>> getActivitiesByUserAndAction(
            @PathVariable Long userId,
            @PathVariable String action,
            Pageable pageable) {
        return ResponseEntity.ok(userActivityAuditService.getActivitiesByUserAndAction(userId, action, pageable));
    }

    // ------------------------------
    // Get Activities by User and Date Range
    // ------------------------------
    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<Page<UserActivityAuditDto>> getActivitiesByUserAndDateRange(
            @PathVariable Long userId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate,
            Pageable pageable) {
        return ResponseEntity.ok(userActivityAuditService.getActivitiesByUserAndDateRange(userId, startDate, endDate, pageable));
    }

    // ------------------------------
    // Count Activities for User
    // ------------------------------
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Map<String, Object>> countActivitiesForUser(@PathVariable Long userId) {
        long count = userActivityAuditService.countActivitiesForUser(userId);
        return ResponseEntity.ok(Map.of(
                "userId", userId,
                "activityCount", count
        ));
    }

    // ------------------------------
    // Count Activities by Action
    // ------------------------------
    @GetMapping("/action/{action}/count")
    public ResponseEntity<Map<String, Object>> countActivitiesByAction(@PathVariable String action) {
        long count = userActivityAuditService.countActivitiesByAction(action);
        return ResponseEntity.ok(Map.of(
                "action", action,
                "activityCount", count
        ));
    }

    // ------------------------------
    // Get User Activity Statistics
    // ------------------------------
    @GetMapping("/user/{userId}/statistics")
    public ResponseEntity<Map<String, Object>> getUserActivityStatistics(@PathVariable Long userId) {
        return ResponseEntity.ok(userActivityAuditService.getUserActivityStatistics(userId));
    }

    // ------------------------------
    // Get Profile Change History
    // ------------------------------
    @GetMapping("/user/{userId}/profile-changes")
    public ResponseEntity<Page<UserActivityAuditDto>> getProfileChangeHistory(
            @PathVariable Long userId,
            Pageable pageable) {
        return ResponseEntity.ok(userActivityAuditService.getProfileChangeHistory(userId, pageable));
    }
}
