package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.PayoutStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.entity.PayoutAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for PayoutAudit entity.
 * Provides database operations for payout audit records.
 */
@Repository
public interface PayoutAuditRepository extends JpaRepository<PayoutAudit, Long> {

    /**
     * Find all audit records for a payout.
     *
     * @param payoutId the payout ID
     * @param pageable pagination information
     * @return page of payout audits
     */
    Page<PayoutAudit> findByPayoutId(Long payoutId, Pageable pageable);

    /**
     * Find audit records by admin.
     *
     * @param adminId the admin user ID
     * @param pageable pagination information
     * @return page of payout audits
     */
    Page<PayoutAudit> findByAdminId(Long adminId, Pageable pageable);

    /**
     * Find audit records by action.
     *
     * @param action the action
     * @param pageable pagination information
     * @return page of payout audits
     */
    Page<PayoutAudit> findByAction(String action, Pageable pageable);

    /**
     * Find audit records by new status.
     *
     * @param newStatus the new status
     * @param pageable pagination information
     * @return page of payout audits
     */
    Page<PayoutAudit> findByNewStatus(PayoutStatus newStatus, Pageable pageable);

    /**
     * Find audit records within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of payout audits
     */
    Page<PayoutAudit> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find audit records for payout within date range.
     *
     * @param payoutId the payout ID
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of payout audits
     */
    @Query("SELECT pa FROM PayoutAudit pa WHERE pa.payout.id = :payoutId " +
           "AND pa.createdAt BETWEEN :startDate AND :endDate " +
           "ORDER BY pa.createdAt DESC")
    Page<PayoutAudit> findByPayoutIdAndDateRange(
            @Param("payoutId") Long payoutId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    /**
     * Find audit records by admin and date range.
     *
     * @param adminId the admin user ID
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of payout audits
     */
    @Query("SELECT pa FROM PayoutAudit pa WHERE pa.admin.id = :adminId " +
           "AND pa.createdAt BETWEEN :startDate AND :endDate " +
           "ORDER BY pa.createdAt DESC")
    Page<PayoutAudit> findByAdminIdAndDateRange(
            @Param("adminId") Long adminId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    /**
     * Find audit records by old and new status.
     *
     * @param oldStatus the old status
     * @param newStatus the new status
     * @param pageable pagination information
     * @return page of payout audits
     */
    Page<PayoutAudit> findByOldStatusAndNewStatus(PayoutStatus oldStatus, PayoutStatus newStatus, Pageable pageable);

    /**
     * Count audit records for a payout.
     *
     * @param payoutId the payout ID
     * @return count of audit records
     */
    long countByPayoutId(Long payoutId);

    /**
     * Count audit records by admin.
     *
     * @param adminId the admin user ID
     * @return count of audit records
     */
    long countByAdminId(Long adminId);

    /**
     * Find latest audit record for a payout.
     *
     * @param payoutId the payout ID
     * @return list of latest audit record
     */
    @Query(value = "SELECT * FROM payout_audits WHERE payout_id = :payoutId ORDER BY created_at DESC LIMIT 1",
           nativeQuery = true)
    List<PayoutAudit> findLatestAuditForPayout(@Param("payoutId") Long payoutId);

    /**
     * Find audit records by action and date range.
     *
     * @param action the action
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of payout audits
     */
    @Query("SELECT pa FROM PayoutAudit pa WHERE pa.action = :action " +
           "AND pa.createdAt BETWEEN :startDate AND :endDate " +
           "ORDER BY pa.createdAt DESC")
    Page<PayoutAudit> findByActionAndDateRange(
            @Param("action") String action,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);
}
