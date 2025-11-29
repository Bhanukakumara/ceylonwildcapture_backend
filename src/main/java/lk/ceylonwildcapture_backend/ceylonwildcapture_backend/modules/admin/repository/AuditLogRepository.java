package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * Repository interface for AuditLog entity.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    /**
     * Find audit logs by event type.
     *
     * @param eventType event type
     * @param pageable pagination parameters
     * @return page of audit logs
     */
    Page<AuditLog> findByEventType(String eventType, Pageable pageable);

    /**
     * Find audit logs by entity type.
     *
     * @param entityType entity type
     * @param pageable pagination parameters
     * @return page of audit logs
     */
    Page<AuditLog> findByEntityType(String entityType, Pageable pageable);

    /**
     * Find audit logs by user ID.
     *
     * @param userId user ID
     * @param pageable pagination parameters
     * @return page of audit logs
     */
    Page<AuditLog> findByUserId(Long userId, Pageable pageable);

    /**
     * Find audit logs by entity type and entity ID.
     *
     * @param entityType entity type
     * @param entityId entity ID
     * @param pageable pagination parameters
     * @return page of audit logs
     */
    Page<AuditLog> findByEntityTypeAndEntityId(String entityType, Long entityId, Pageable pageable);

    /**
     * Find audit logs by date range.
     *
     * @param startDate start date
     * @param endDate end date
     * @param pageable pagination parameters
     * @return page of audit logs
     */
    Page<AuditLog> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find audit logs by action.
     *
     * @param action action name
     * @param pageable pagination parameters
     * @return page of audit logs
     */
    Page<AuditLog> findByAction(String action, Pageable pageable);

    /**
     * Search audit logs by description.
     *
     * @param searchTerm search term
     * @param pageable pagination parameters
     * @return page of audit logs
     */
    @Query("SELECT a FROM AuditLog a WHERE " +
            "LOWER(a.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(a.action) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(a.eventType) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<AuditLog> searchAuditLogs(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Delete audit logs older than specified date.
     *
     * @param olderThan date threshold
     * @return number of deleted records
     */
    long deleteByCreatedAtBefore(LocalDateTime olderThan);

    /**
     * Find failed login attempts.
     *
     * @param pageable pagination parameters
     * @return page of failed login attempts
     */
    @Query("SELECT a FROM AuditLog a WHERE a.eventType = 'LOGIN' AND a.status = 'FAILURE'")
    Page<AuditLog> findFailedLoginAttempts(Pageable pageable);
}
