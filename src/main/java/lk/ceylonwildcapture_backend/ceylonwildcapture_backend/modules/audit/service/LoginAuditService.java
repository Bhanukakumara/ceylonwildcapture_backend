package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.LoginAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.LoginAudit;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service interface for login audit operations.
 * Provides methods for recording and retrieving login audit logs.
 */
public interface LoginAuditService {

    /**
     * Record a login audit event.
     *
     * @param loginAudit the login audit entity
     * @return the saved login audit entity
     */
    LoginAudit recordLogin(LoginAudit loginAudit);

    /**
     * Get login audit by ID.
     *
     * @param auditId the audit ID
     * @return optional containing the login audit DTO
     */
    Optional<LoginAuditDto> getLoginAuditById(Long auditId);

    /**
     * Get all login audits for a user.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of login audit DTOs
     */
    Page<LoginAuditDto> getLoginsByUser(Long userId, Pageable pageable);

    /**
     * Get successful login audits for a user.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of login audit DTOs
     */
    Page<LoginAuditDto> getSuccessfulLoginsByUser(Long userId, Pageable pageable);

    /**
     * Get failed login audits for a user.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of login audit DTOs
     */
    Page<LoginAuditDto> getFailedLoginsByUser(Long userId, Pageable pageable);

    /**
     * Get login audits within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of login audit DTOs
     */
    Page<LoginAuditDto> getLoginsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Get login audits by action result.
     *
     * @param actionResult the action result
     * @param pageable pagination information
     * @return page of login audit DTOs
     */
    Page<LoginAuditDto> getLoginsByResult(ActionResult actionResult, Pageable pageable);

    /**
     * Get login audits by country.
     *
     * @param country the country
     * @param pageable pagination information
     * @return page of login audit DTOs
     */
    Page<LoginAuditDto> getLoginsByCountry(String country, Pageable pageable);

    /**
     * Count total login attempts for a user.
     *
     * @param userId the user ID
     * @return count of login attempts
     */
    long countLoginAttemptsForUser(Long userId);

    /**
     * Count failed login attempts for a user.
     *
     * @param userId the user ID
     * @return count of failed login attempts
     */
    long countFailedLoginAttemptsForUser(Long userId);

    /**
     * Get most recent login for a user.
     *
     * @param userId the user ID
     * @return optional containing the most recent login audit DTO
     */
    Optional<LoginAuditDto> getMostRecentLoginForUser(Long userId);

    /**
     * Get login statistics for a user.
     *
     * @param userId the user ID
     * @return map containing login statistics
     */
    java.util.Map<String, Object> getLoginStatistics(Long userId);

    /**
     * Check if user has suspicious login activity.
     *
     * @param userId the user ID
     * @param failedAttemptsThreshold threshold for failed attempts
     * @param timeWindowMinutes time window in minutes
     * @return true if suspicious activity detected
     */
    boolean hasSuspiciousActivity(Long userId, int failedAttemptsThreshold, int timeWindowMinutes);
}
