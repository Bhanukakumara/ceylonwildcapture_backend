package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.PayoutStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.PayoutAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.entity.PayoutAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for payout audit operations.
 * Provides methods for recording and retrieving payout audit logs.
 */
public interface PayoutAuditService {

    /**
     * Record a payout audit event.
     *
     * @param payoutAudit the payout audit entity
     * @return the saved payout audit entity
     */
    PayoutAudit recordPayoutAudit(PayoutAudit payoutAudit);

    /**
     * Record a status change for a payout.
     *
     * @param payoutId the payout ID
     * @param oldStatus the old status
     * @param newStatus the new status
     * @param action the action description
     * @param reason the reason for change
     * @param adminId the admin user ID (optional)
     * @return the saved payout audit entity
     */
    PayoutAudit recordStatusChange(Long payoutId, PayoutStatus oldStatus, PayoutStatus newStatus,
                                   String action, String reason, Long adminId);

    /**
     * Get audit record by ID.
     *
     * @param auditId the audit ID
     * @return optional containing the payout audit DTO
     */
    Optional<PayoutAuditDto> getAuditById(Long auditId);

    /**
     * Get all audit records for a payout.
     *
     * @param payoutId the payout ID
     * @param pageable pagination information
     * @return page of payout audit DTOs
     */
    Page<PayoutAuditDto> getAuditsByPayout(Long payoutId, Pageable pageable);

    /**
     * Get audit records by admin.
     *
     * @param adminId the admin user ID
     * @param pageable pagination information
     * @return page of payout audit DTOs
     */
    Page<PayoutAuditDto> getAuditsByAdmin(Long adminId, Pageable pageable);

    /**
     * Get audit records by action.
     *
     * @param action the action
     * @param pageable pagination information
     * @return page of payout audit DTOs
     */
    Page<PayoutAuditDto> getAuditsByAction(String action, Pageable pageable);

    /**
     * Get audit records by new status.
     *
     * @param newStatus the new status
     * @param pageable pagination information
     * @return page of payout audit DTOs
     */
    Page<PayoutAuditDto> getAuditsByNewStatus(PayoutStatus newStatus, Pageable pageable);

    /**
     * Get audit records within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of payout audit DTOs
     */
    Page<PayoutAuditDto> getAuditsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Get audit history for a payout.
     *
     * @param payoutId the payout ID
     * @return list of payout audit DTOs
     */
    List<PayoutAuditDto> getPayoutAuditHistory(Long payoutId);

    /**
     * Count audit records for a payout.
     *
     * @param payoutId the payout ID
     * @return count of audit records
     */
    long countAuditsForPayout(Long payoutId);

    /**
     * Count audit records by admin.
     *
     * @param adminId the admin user ID
     * @return count of audit records
     */
    long countAuditsByAdmin(Long adminId);

    /**
     * Get audit statistics.
     *
     * @return map containing audit statistics
     */
    java.util.Map<String, Object> getAuditStatistics();

    /**
     * Export audit records as CSV.
     *
     * @param payoutId the payout ID
     * @return CSV content as string
     */
    String exportAuditsCsv(Long payoutId);
}
