package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.PaymentAudit;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for PaymentAudit entity.
 * Provides database operations for payment audit records.
 */
@Repository
public interface PaymentAuditRepository extends JpaRepository<PaymentAudit, Long> {

    /**
     * Find all payment audits for a specific user.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of payment audits
     */
    Page<PaymentAudit> findByUserId(Long userId, Pageable pageable);

    /**
     * Find payment audits by payment ID.
     *
     * @param paymentId the payment ID
     * @param pageable pagination information
     * @return page of payment audits
     */
    Page<PaymentAudit> findByPaymentId(Long paymentId, Pageable pageable);

    /**
     * Find payment audits by order ID.
     *
     * @param orderId the order ID
     * @param pageable pagination information
     * @return page of payment audits
     */
    Page<PaymentAudit> findByOrderId(Long orderId, Pageable pageable);

    /**
     * Find payment audits by action.
     *
     * @param action the action type
     * @param pageable pagination information
     * @return page of payment audits
     */
    Page<PaymentAudit> findByAction(String action, Pageable pageable);

    /**
     * Find payment audits by action result.
     *
     * @param actionResult the action result
     * @param pageable pagination information
     * @return page of payment audits
     */
    Page<PaymentAudit> findByActionResult(ActionResult actionResult, Pageable pageable);

    /**
     * Find payment audits within a date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of payment audits
     */
    Page<PaymentAudit> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find payment audits by payment method.
     *
     * @param paymentMethod the payment method
     * @param pageable pagination information
     * @return page of payment audits
     */
    Page<PaymentAudit> findByPaymentMethod(String paymentMethod, Pageable pageable);

    /**
     * Find payment audits by user and date range.
     *
     * @param userId the user ID
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of payment audits
     */
    @Query("SELECT pa FROM PaymentAudit pa WHERE pa.user.id = :userId " +
           "AND pa.createdAt BETWEEN :startDate AND :endDate " +
           "ORDER BY pa.createdAt DESC")
    Page<PaymentAudit> findByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    /**
     * Find payment audits by user and action result.
     *
     * @param userId the user ID
     * @param actionResult the action result
     * @param pageable pagination information
     * @return page of payment audits
     */
    Page<PaymentAudit> findByUserIdAndActionResult(Long userId, ActionResult actionResult, Pageable pageable);

    /**
     * Find payment audits by order and result.
     *
     * @param orderId the order ID
     * @param actionResult the action result
     * @param pageable pagination information
     * @return page of payment audits
     */
    Page<PaymentAudit> findByOrderIdAndActionResult(Long orderId, ActionResult actionResult, Pageable pageable);

    /**
     * Count payment audits for a user.
     *
     * @param userId the user ID
     * @return count of payment audits
     */
    long countByUserId(Long userId);

    /**
     * Sum payment amounts for a user within date range.
     *
     * @param userId the user ID
     * @param startDate the start date
     * @param endDate the end date
     * @param actionResult the action result
     * @return sum of payment amounts
     */
    @Query("SELECT COALESCE(SUM(pa.amount), 0) FROM PaymentAudit pa " +
           "WHERE pa.user.id = :userId " +
           "AND pa.createdAt BETWEEN :startDate AND :endDate " +
           "AND pa.actionResult = :actionResult")
    BigDecimal sumAmountByUserIdAndDateRangeAndResult(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("actionResult") ActionResult actionResult);

    /**
     * Find payment audits by transaction ID.
     *
     * @param transactionId the transaction ID
     * @return list of payment audits
     */
    List<PaymentAudit> findByTransactionId(String transactionId);
}
