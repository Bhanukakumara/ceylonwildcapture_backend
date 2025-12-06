package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.UserActivityAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.UserActivityAudit;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service interface for user activity audit operations.
 * Provides methods for recording and retrieving user activity audit logs.
 */
public interface UserActivityAuditService {

    /**
     * Record a user activity audit event.
     *
     * @param userActivityAudit the user activity audit entity
     * @return the saved user activity audit entity
     */
    UserActivityAudit recordUserActivity(UserActivityAudit userActivityAudit);

    /**
     * Get user activity audit by ID.
     *
     * @param auditId the audit ID
     * @return optional containing the user activity audit DTO
     */
    Optional<UserActivityAuditDto> getUserActivityAuditById(Long auditId);

    /**
     * Get all user activities for a user.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of user activity audit DTOs
     */
    Page<UserActivityAuditDto> getActivitiesByUser(Long userId, Pageable pageable);

    /**
     * Get user activities by action type.
     *
     * @param action the action type
     * @param pageable pagination information
     * @return page of user activity audit DTOs
     */
    Page<UserActivityAuditDto> getActivitiesByAction(String action, Pageable pageable);

    /**
     * Get user activities by action result.
     *
     * @param actionResult the action result
     * @param pageable pagination information
     * @return page of user activity audit DTOs
     */
    Page<UserActivityAuditDto> getActivitiesByResult(ActionResult actionResult, Pageable pageable);

    /**
     * Get user activities within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of user activity audit DTOs
     */
    Page<UserActivityAuditDto> getActivitiesByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Get user activities by user and action.
     *
     * @param userId the user ID
     * @param action the action type
     * @param pageable pagination information
     * @return page of user activity audit DTOs
     */
    Page<UserActivityAuditDto> getActivitiesByUserAndAction(Long userId, String action, Pageable pageable);

    /**
     * Get user activities by user and date range.
     *
     * @param userId the user ID
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of user activity audit DTOs
     */
    Page<UserActivityAuditDto> getActivitiesByUserAndDateRange(Long userId, LocalDateTime startDate,
                                                               LocalDateTime endDate, Pageable pageable);

    /**
     * Count user activities for a user.
     *
     * @param userId the user ID
     * @return count of user activities
     */
    long countActivitiesForUser(Long userId);

    /**
     * Count user activities by action.
     *
     * @param action the action type
     * @return count of user activities
     */
    long countActivitiesByAction(String action);

    /**
     * Get user activity statistics.
     *
     * @param userId the user ID
     * @return map containing user activity statistics
     */
    java.util.Map<String, Object> getUserActivityStatistics(Long userId);

    /**
     * Get profile change history for a user.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of user activity audit DTOs for profile changes
     */
    Page<UserActivityAuditDto> getProfileChangeHistory(Long userId, Pageable pageable);
}
