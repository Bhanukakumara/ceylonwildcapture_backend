package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.AuditEventResponse;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.filter.AuditSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for advanced audit query operations.
 * Supports complex filtering and searching across all audit types.
 */
public interface AuditQueryService {

    /**
     * Execute advanced search using audit search criteria.
     *
     * @param criteria the search criteria
     * @param pageable pagination information
     * @return page of audit event responses matching criteria
     */
    Page<AuditEventResponse> executeSearch(AuditSearchCriteria criteria, Pageable pageable);

    /**
     * Get audit events for a specific user as actor.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of audit event responses
     */
    Page<AuditEventResponse> getEventsByActor(Long userId, Pageable pageable);

    /**
     * Get audit events for a specific user as target.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of audit event responses
     */
    Page<AuditEventResponse> getEventsByTarget(Long userId, Pageable pageable);

    /**
     * Get audit events for a specific admin.
     *
     * @param adminId the admin user ID
     * @param pageable pagination information
     * @return page of audit event responses
     */
    Page<AuditEventResponse> getEventsByAdmin(Long adminId, Pageable pageable);

    /**
     * Get audit events by entity.
     *
     * @param entityId the entity ID
     * @param pageable pagination information
     * @return page of audit event responses
     */
    Page<AuditEventResponse> getEventsByEntity(Long entityId, Pageable pageable);

    /**
     * Count audit events matching criteria.
     *
     * @param criteria the search criteria
     * @return count of matching events
     */
    long countEventsByCriteria(AuditSearchCriteria criteria);

    /**
     * Get audit events for a specific IP address.
     *
     * @param ipAddress the IP address
     * @param pageable pagination information
     * @return page of audit event responses
     */
    Page<AuditEventResponse> getEventsByIpAddress(String ipAddress, Pageable pageable);

    /**
     * Get audit events for a specific country.
     *
     * @param country the country
     * @param pageable pagination information
     * @return page of audit event responses
     */
    Page<AuditEventResponse> getEventsByCountry(String country, Pageable pageable);

    /**
     * Get suspicious audit events.
     *
     * @param pageable pagination information
     * @return page of suspicious audit event responses
     */
    Page<AuditEventResponse> getSuspiciousEvents(Pageable pageable);

    /**
     * Get failed audit events.
     *
     * @param pageable pagination information
     * @return page of failed audit event responses
     */
    Page<AuditEventResponse> getFailedEvents(Pageable pageable);

    /**
     * Get audit events by action type.
     *
     * @param actionType the action type
     * @param pageable pagination information
     * @return page of audit event responses
     */
    Page<AuditEventResponse> getEventsByActionType(String actionType, Pageable pageable);
}
