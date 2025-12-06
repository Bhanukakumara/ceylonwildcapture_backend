package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.AuditEventResponse;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.filter.AuditSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service interface for general audit operations.
 * Provides methods for retrieving and querying audit logs across all audit types.
 */
public interface AuditService {

    /**
     * Get audit record by ID.
     *
     * @param auditId the audit ID
     * @return optional containing the audit event response
     */
    Optional<AuditEventResponse> getAuditById(Long auditId);

    /**
     * Get all audit records with pagination.
     *
     * @param pageable pagination information
     * @return page of audit event responses
     */
    Page<AuditEventResponse> getAllAudits(Pageable pageable);

    /**
     * Search audit records using advanced criteria.
     *
     * @param criteria the search criteria
     * @param pageable pagination information
     * @return page of audit event responses matching criteria
     */
    Page<AuditEventResponse> searchAudits(AuditSearchCriteria criteria, Pageable pageable);

    /**
     * Get audit records for a specific actor (user).
     *
     * @param actorId the actor user ID
     * @param pageable pagination information
     * @return page of audit event responses
     */
    Page<AuditEventResponse> getAuditsByActor(Long actorId, Pageable pageable);

    /**
     * Get audit records for a specific target entity.
     *
     * @param entityId the entity ID
     * @param pageable pagination information
     * @return page of audit event responses
     */
    Page<AuditEventResponse> getAuditsByEntity(Long entityId, Pageable pageable);

    /**
     * Export audit logs as CSV.
     *
     * @param criteria the search criteria
     * @return CSV content as string
     */
    String exportAuditsCsv(AuditSearchCriteria criteria);

    /**
     * Export audit logs as JSON.
     *
     * @param criteria the search criteria
     * @return JSON content as string
     */
    String exportAuditsJson(AuditSearchCriteria criteria);

    /**
     * Get audit statistics.
     *
     * @return map containing various audit statistics
     */
    java.util.Map<String, Object> getAuditStatistics();

    /**
     * Delete old audit records (retention policy).
     *
     * @param daysToKeep number of days to keep records
     * @return number of records deleted
     */
    long deleteOldAudits(int daysToKeep);
}
