package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.entity.PaymentLog;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for PaymentLog entity.
 */
@Repository
public interface PaymentLogRepository extends JpaRepository<PaymentLog, Long> {

    /**
     * Find payment logs by payment ID.
     *
     * @param paymentId payment ID
     * @param pageable pagination parameters
     * @return page of payment logs
     */
    Page<PaymentLog> findByPaymentId(Long paymentId, Pageable pageable);

    /**
     * Find payment logs by payment ID (list).
     *
     * @param paymentId payment ID
     * @return list of payment logs
     */
    List<PaymentLog> findByPaymentIdOrderByCreatedAtDesc(Long paymentId);

    /**
     * Find payment logs by transaction type.
     *
     * @param transactionType transaction type
     * @param pageable pagination parameters
     * @return page of payment logs
     */
    Page<PaymentLog> findByTransactionType(TransactionType transactionType, Pageable pageable);

    /**
     * Find payment logs by provider reference.
     *
     * @param providerReference provider reference
     * @return optional payment log
     */
    Optional<PaymentLog> findByProviderReference(String providerReference);

    /**
     * Find payment logs by status.
     *
     * @param status status
     * @param pageable pagination parameters
     * @return page of payment logs
     */
    Page<PaymentLog> findByStatus(String status, Pageable pageable);

    /**
     * Find payment logs by date range.
     *
     * @param startDate start date
     * @param endDate end date
     * @param pageable pagination parameters
     * @return page of payment logs
     */
    Page<PaymentLog> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find failed transactions.
     *
     * @param pageable pagination parameters
     * @return page of failed payment logs
     */
    @Query("SELECT pl FROM PaymentLog pl WHERE pl.errorMessage IS NOT NULL ORDER BY pl.createdAt DESC")
    Page<PaymentLog> findFailedTransactions(Pageable pageable);

    /**
     * Find payment logs by payment ID and transaction type.
     *
     * @param paymentId payment ID
     * @param transactionType transaction type
     * @return list of payment logs
     */
    List<PaymentLog> findByPaymentIdAndTransactionType(Long paymentId, TransactionType transactionType);

    /**
     * Find latest log for payment.
     *
     * @param paymentId payment ID
     * @return optional payment log
     */
    @Query("SELECT pl FROM PaymentLog pl WHERE pl.payment.id = :paymentId ORDER BY pl.createdAt DESC LIMIT 1")
    Optional<PaymentLog> findLatestLogForPayment(@Param("paymentId") Long paymentId);

    /**
     * Count logs by payment ID.
     *
     * @param paymentId payment ID
     * @return log count
     */
    long countByPaymentId(Long paymentId);

    /**
     * Count logs by transaction type.
     *
     * @param transactionType transaction type
     * @return log count
     */
    long countByTransactionType(TransactionType transactionType);

    /**
     * Delete old payment logs.
     *
     * @param olderThan cutoff date
     * @return number of deleted logs
     */
    long deleteByCreatedAtBefore(LocalDateTime olderThan);
}
