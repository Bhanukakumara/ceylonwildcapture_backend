package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.Payment;
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
 * Repository interface for Payment entity.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Find payment by order ID.
     *
     * @param orderId order ID
     * @return optional payment
     */
    Optional<Payment> findByOrderId(Long orderId);

    /**
     * Find payment by provider payment ID.
     *
     * @param paymentId provider payment ID
     * @return optional payment
     */
    Optional<Payment> findByPaymentId(String paymentId);

    /**
     * Find payment by transaction ID.
     *
     * @param transactionId transaction ID
     * @return optional payment
     */
    Optional<Payment> findByTransactionId(String transactionId);

    /**
     * Find payments by status.
     *
     * @param status payment status
     * @param pageable pagination parameters
     * @return page of payments
     */
    Page<Payment> findByStatus(String status, Pageable pageable);

    /**
     * Find payments by buyer ID.
     *
     * @param buyerId buyer ID
     * @param pageable pagination parameters
     * @return page of payments
     */
    @Query("SELECT p FROM Payment p WHERE p.order.buyer.id = :buyerId")
    Page<Payment> findByBuyerId(@Param("buyerId") Long buyerId, Pageable pageable);

    /**
     * Find payments by payment provider.
     *
     * @param provider payment provider
     * @param pageable pagination parameters
     * @return page of payments
     */
    Page<Payment> findByPaymentProvider(String provider, Pageable pageable);

    /**
     * Find payments by payment method.
     *
     * @param paymentMethod payment method
     * @param pageable pagination parameters
     * @return page of payments
     */
    Page<Payment> findByPaymentMethod(String paymentMethod, Pageable pageable);

    /**
     * Find payments by payer email.
     *
     * @param payerEmail payer email
     * @param pageable pagination parameters
     * @return page of payments
     */
    Page<Payment> findByPayerEmailContainingIgnoreCase(String payerEmail, Pageable pageable);

    /**
     * Find payments created between dates.
     *
     * @param startDate start date
     * @param endDate end date
     * @param pageable pagination parameters
     * @return page of payments
     */
    Page<Payment> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find payments paid between dates.
     *
     * @param startDate start date
     * @param endDate end date
     * @param pageable pagination parameters
     * @return page of payments
     */
    Page<Payment> findByPaidAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find successful payments.
     *
     * @param status success status
     * @param pageable pagination parameters
     * @return page of successful payments
     */
    @Query("SELECT p FROM Payment p WHERE p.status = :status ORDER BY p.paidAt DESC")
    Page<Payment> findSuccessfulPayments(@Param("status") String status, Pageable pageable);

    /**
     * Find failed payments.
     *
     * @param status failed status
     * @param pageable pagination parameters
     * @return page of failed payments
     */
    @Query("SELECT p FROM Payment p WHERE p.status = :status ORDER BY p.createdAt DESC")
    Page<Payment> findFailedPayments(@Param("status") String status, Pageable pageable);

    /**
     * Find refunded payments.
     *
     * @param pageable pagination parameters
     * @return page of refunded payments
     */
    Page<Payment> findByRefundedAtIsNotNull(Pageable pageable);

    /**
     * Find pending payments older than specified time.
     *
     * @param status pending status
     * @param olderThan cutoff time
     * @return list of pending payments
     */
    @Query("SELECT p FROM Payment p WHERE p.status = :status AND p.createdAt < :olderThan")
    List<Payment> findPendingPaymentsOlderThan(@Param("status") String status,
                                                @Param("olderThan") LocalDateTime olderThan);

    /**
     * Check if payment exists by payment ID.
     *
     * @param paymentId provider payment ID
     * @return true if exists
     */
    boolean existsByPaymentId(String paymentId);

    /**
     * Check if payment exists by transaction ID.
     *
     * @param transactionId transaction ID
     * @return true if exists
     */
    boolean existsByTransactionId(String transactionId);

    /**
     * Count payments by status.
     *
     * @param status payment status
     * @return payment count
     */
    long countByStatus(String status);

    /**
     * Count payments by buyer ID.
     *
     * @param buyerId buyer ID
     * @return payment count
     */
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.order.buyer.id = :buyerId")
    long countByBuyerId(@Param("buyerId") Long buyerId);

    /**
     * Search payments by order number or payer details.
     *
     * @param searchTerm search term
     * @param pageable pagination parameters
     * @return page of payments
     */
    @Query("SELECT p FROM Payment p WHERE " +
            "LOWER(p.order.orderNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.payerEmail) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.payerName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.paymentId) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.transactionId) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Payment> searchPayments(@Param("searchTerm") String searchTerm, Pageable pageable);
}
