package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.AdminActionAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.AdminActionAudit;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.EntityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service interface for admin action audit operations.
 * Provides methods for recording and retrieving admin action audit logs.
 */
public interface AdminActionAuditService {

    /**
     * Record an admin action audit event.
     *
     * @param adminActionAudit the admin action audit entity
     * @return the saved admin action audit entity
     */
    AdminActionAudit recordAdminAction(AdminActionAudit adminActionAudit);

    /**
     * Get admin action audit by ID.
     *
     * @param auditId the audit ID
     * @return optional containing the admin action audit DTO
     */
    Optional<AdminActionAuditDto> getAdminActionAuditById(Long auditId);

    /**
     * Get all admin actions by a specific admin.
     *
     * @param adminId the admin user ID
     * @param pageable pagination information
     * @return page of admin action audit DTOs
     */
    Page<AdminActionAuditDto> getActionsByAdmin(Long adminId, Pageable pageable);

    /**
     * Get admin actions on a specific entity.
     *
     * @param entityId the entity ID
     * @param pageable pagination information
     * @return page of admin action audit DTOs
     */
    Page<AdminActionAuditDto> getActionsByEntity(Long entityId, Pageable pageable);

    /**
     * Get admin actions on a specific entity type.
     *
     * @param entityType the entity type
     * @param pageable pagination information
     * @return page of admin action audit DTOs
     */
    Page<AdminActionAuditDto> getActionsByEntityType(EntityType entityType, Pageable pageable);

    /**
     * Get admin actions within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of admin action audit DTOs
     */
    Page<AdminActionAuditDto> getActionsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Get admin actions by admin and entity type.
     *
     * @param adminId the admin user ID
     * @param entityType the entity type
     * @param pageable pagination information
     * @return page of admin action audit DTOs
     */
    Page<AdminActionAuditDto> getActionsByAdminAndEntityType(Long adminId, EntityType entityType, Pageable pageable);

    /**
     * Count admin actions by admin.
     *
     * @param adminId the admin user ID
     * @return count of admin actions
     */
    long countActionsByAdmin(Long adminId);

    /**
     * Count admin actions on entity type.
     *
     * @param entityType the entity type
     * @return count of admin actions
     */
    long countActionsByEntityType(EntityType entityType);

    /**
     * Get admin action statistics.
     *
     * @param adminId the admin user ID
     * @return map containing admin action statistics
     */
    java.util.Map<String, Object> getAdminActionStatistics(Long adminId);

    /**
     * Get action history for a specific entity.
     *
     * @param entityType the entity type
     * @param entityId the entity ID
     * @param pageable pagination information
     * @return page of admin action audit DTOs
     */
    Page<AdminActionAuditDto> getEntityActionHistory(EntityType entityType, Long entityId, Pageable pageable);
}
