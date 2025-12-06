package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.AdminActionAudit;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.EntityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for AdminActionAudit entity.
 * Provides database operations for admin action audit records.
 */
@Repository
public interface AdminActionAuditRepository extends JpaRepository<AdminActionAudit, Long> {

    /**
     * Find all admin actions by a specific admin.
     *
     * @param adminId the admin user ID
     * @param pageable pagination information
     * @return page of admin action audits
     */
    Page<AdminActionAudit> findByAdminId(Long adminId, Pageable pageable);

    /**
     * Find admin actions on a specific entity.
     *
     * @param entityId the entity ID
     * @param pageable pagination information
     * @return page of admin action audits
     */
    Page<AdminActionAudit> findByEntityId(Long entityId, Pageable pageable);

    /**
     * Find admin actions on a specific entity type.
     *
     * @param entityType the entity type
     * @param pageable pagination information
     * @return page of admin action audits
     */
    Page<AdminActionAudit> findByEntityType(EntityType entityType, Pageable pageable);

    /**
     * Find admin actions by action type.
     *
     * @param action the action type
     * @param pageable pagination information
     * @return page of admin action audits
     */
    Page<AdminActionAudit> findByAction(String action, Pageable pageable);

    /**
     * Find admin actions by result.
     *
     * @param actionResult the action result
     * @param pageable pagination information
     * @return page of admin action audits
     */
    Page<AdminActionAudit> findByActionResult(ActionResult actionResult, Pageable pageable);

    /**
     * Find admin actions within a date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of admin action audits
     */
    Page<AdminActionAudit> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find admin actions by admin and entity type.
     *
     * @param adminId the admin user ID
     * @param entityType the entity type
     * @param pageable pagination information
     * @return page of admin action audits
     */
    Page<AdminActionAudit> findByAdminIdAndEntityType(Long adminId, EntityType entityType, Pageable pageable);

    /**
     * Find admin actions on specific entity by admin.
     *
     * @param adminId the admin user ID
     * @param entityId the entity ID
     * @param pageable pagination information
     * @return page of admin action audits
     */
    Page<AdminActionAudit> findByAdminIdAndEntityId(Long adminId, Long entityId, Pageable pageable);

    /**
     * Find admin actions on entity by type and ID.
     *
     * @param entityType the entity type
     * @param entityId the entity ID
     * @param pageable pagination information
     * @return page of admin action audits
     */
    Page<AdminActionAudit> findByEntityTypeAndEntityId(EntityType entityType, Long entityId, Pageable pageable);

    /**
     * Count admin actions by admin.
     *
     * @param adminId the admin user ID
     * @return count of admin actions
     */
    long countByAdminId(Long adminId);

    /**
     * Count admin actions on entity type.
     *
     * @param entityType the entity type
     * @return count of admin actions
     */
    long countByEntityType(EntityType entityType);

    /**
     * Find admin actions by admin, entity type, and date range.
     *
     * @param adminId the admin user ID
     * @param entityType the entity type
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of admin action audits
     */
    @Query("SELECT aa FROM AdminActionAudit aa WHERE aa.admin.id = :adminId " +
           "AND aa.entityType = :entityType " +
           "AND aa.createdAt BETWEEN :startDate AND :endDate " +
           "ORDER BY aa.createdAt DESC")
    Page<AdminActionAudit> findByAdminIdAndEntityTypeAndDateRange(
            @Param("adminId") Long adminId,
            @Param("entityType") EntityType entityType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    /**
     * Find admin actions by entity and date range.
     *
     * @param entityId the entity ID
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of admin action audits
     */
    @Query("SELECT aa FROM AdminActionAudit aa WHERE aa.entityId = :entityId " +
           "AND aa.createdAt BETWEEN :startDate AND :endDate " +
           "ORDER BY aa.createdAt DESC")
    Page<AdminActionAudit> findByEntityIdAndDateRange(
            @Param("entityId") Long entityId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);
}
