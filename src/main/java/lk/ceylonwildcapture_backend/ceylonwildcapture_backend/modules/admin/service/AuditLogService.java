package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.AuditLogDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * Service interface for audit log operations.
 */
public interface AuditLogService {

    /**
     * Create an audit log entry.
     *
     * @param eventType type of event
     * @param entityType type of entity
     * @param entityId ID of entity
     * @param userId ID of user who performed the action
     * @param action action performed
     * @param description detailed description
     * @param metadata additional metadata (JSON)
     */
    void createAuditLog(String eventType, String entityType, Long entityId, Long userId,
                       String action, String description, String metadata);

    /**
     * Get all audit logs with pagination.
     *
     * @param pageable pagination parameters
     * @return page of audit logs
     */
    Page<AuditLogDto> getAllAuditLogs(Pageable pageable);

    /**
     * Get audit logs by event type.
     *
     * @param eventType event type
     * @param pageable pagination parameters
     * @return page of audit logs
     */
    Page<AuditLogDto> getAuditLogsByEventType(String eventType, Pageable pageable);

    /**
     * Get audit logs by entity type.
     *
     * @param entityType entity type
     * @param pageable pagination parameters
     * @return page of audit logs
     */
    Page<AuditLogDto> getAuditLogsByEntityType(String entityType, Pageable pageable);

    /**
     * Get audit logs by user.
     *
     * @param userId ID of the user
     * @param pageable pagination parameters
     * @return page of audit logs
     */
    Page<AuditLogDto> getAuditLogsByUser(Long userId, Pageable pageable);

    /**
     * Get audit logs by date range.
     *
     * @param startDate start date
     * @param endDate end date
     * @param pageable pagination parameters
     * @return page of audit logs
     */
    Page<AuditLogDto> getAuditLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Get audit logs for a specific entity.
     *
     * @param entityType entity type
     * @param entityId entity ID
     * @param pageable pagination parameters
     * @return page of audit logs
     */
    Page<AuditLogDto> getAuditLogsForEntity(String entityType, Long entityId, Pageable pageable);

    /**
     * Get download audit logs.
     *
     * @param pageable pagination parameters
     * @return page of download audit logs
     */
    Page<AuditLogDto> getDownloadAuditLogs(Pageable pageable);

    /**
     * Get login audit logs.
     *
     * @param pageable pagination parameters
     * @return page of login audit logs
     */
    Page<AuditLogDto> getLoginAuditLogs(Pageable pageable);

    /**
     * Get failed login attempts.
     *
     * @param pageable pagination parameters
     * @return page of failed login attempts
     */
    Page<AuditLogDto> getFailedLoginAttempts(Pageable pageable);

    /**
     * Get audit logs by action.
     *
     * @param action action name
     * @param pageable pagination parameters
     * @return page of audit logs
     */
    Page<AuditLogDto> getAuditLogsByAction(String action, Pageable pageable);

    /**
     * Search audit logs.
     *
     * @param searchTerm search term
     * @param pageable pagination parameters
     * @return page of audit logs
     */
    Page<AuditLogDto> searchAuditLogs(String searchTerm, Pageable pageable);

    /**
     * Delete old audit logs.
     *
     * @param olderThan delete logs older than this date
     * @return number of deleted records
     */
    long deleteOldAuditLogs(LocalDateTime olderThan);
}
