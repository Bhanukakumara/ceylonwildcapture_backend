package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.PayoutStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.entity.Payout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository interface for Payout entity.
 * Provides database operations for payout records.
 */
@Repository
public interface PayoutRepository extends JpaRepository<Payout, Long> {

    /**
     * Find payout by payout reference.
     *
     * @param payoutReference the payout reference
     * @return optional containing the payout
     */
    Optional<Payout> findByPayoutReference(String payoutReference);

    /**
     * Find all payouts for a photographer.
     *
     * @param photographerId the photographer ID
     * @param pageable pagination information
     * @return page of payouts
     */
    Page<Payout> findByPhotographerId(Long photographerId, Pageable pageable);

    /**
     * Find payouts by status.
     *
     * @param status the payout status
     * @param pageable pagination information
     * @return page of payouts
     */
    Page<Payout> findByStatus(PayoutStatus status, Pageable pageable);

    /**
     * Find payouts by photographer and status.
     *
     * @param photographerId the photographer ID
     * @param status the payout status
     * @param pageable pagination information
     * @return page of payouts
     */
    Page<Payout> findByPhotographerIdAndStatus(Long photographerId, PayoutStatus status, Pageable pageable);

    /**
     * Find payouts within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of payouts
     */
    Page<Payout> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find payouts by amount range.
     *
     * @param minAmount the minimum amount
     * @param maxAmount the maximum amount
     * @param pageable pagination information
     * @return page of payouts
     */
    @Query("SELECT p FROM Payout p WHERE p.amount BETWEEN :minAmount AND :maxAmount ORDER BY p.createdAt DESC")
    Page<Payout> findByAmountRange(@Param("minAmount") BigDecimal minAmount, @Param("maxAmount") BigDecimal maxAmount, Pageable pageable);

    /**
     * Find payouts by photographer, status, and date range.
     *
     * @param photographerId the photographer ID
     * @param status the payout status
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of payouts
     */
    @Query("SELECT p FROM Payout p WHERE p.photographer.id = :photographerId " +
           "AND p.status = :status " +
           "AND p.createdAt BETWEEN :startDate AND :endDate " +
           "ORDER BY p.createdAt DESC")
    Page<Payout> findByPhotographerIdAndStatusAndDateRange(
            @Param("photographerId") Long photographerId,
            @Param("status") PayoutStatus status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    /**
     * Count payouts by status.
     *
     * @param status the payout status
     * @return count of payouts
     */
    long countByStatus(PayoutStatus status);

    /**
     * Count payouts for a photographer.
     *
     * @param photographerId the photographer ID
     * @return count of payouts
     */
    long countByPhotographerId(Long photographerId);

    /**
     * Count payouts for a photographer by status.
     *
     * @param photographerId the photographer ID
     * @param status the payout status
     * @return count of payouts
     */
    long countByPhotographerIdAndStatus(Long photographerId, PayoutStatus status);

    /**
     * Find pending payouts for a photographer.
     *
     * @param photographerId the photographer ID
     * @param pageable pagination information
     * @return page of pending payouts
     */
    @Query("SELECT p FROM Payout p WHERE p.photographer.id = :photographerId " +
           "AND (p.status = 'PENDING' OR p.status = 'APPROVED') " +
           "ORDER BY p.createdAt ASC")
    Page<Payout> findPendingPayoutsForPhotographer(@Param("photographerId") Long photographerId, Pageable pageable);

    /**
     * Sum payout amounts by status.
     *
     * @param status the payout status
     * @return total amount
     */
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payout p WHERE p.status = :status")
    BigDecimal sumAmountByStatus(@Param("status") PayoutStatus status);

    /**
     * Sum payout amounts for a photographer by status.
     *
     * @param photographerId the photographer ID
     * @param status the payout status
     * @return total amount
     */
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payout p " +
           "WHERE p.photographer.id = :photographerId AND p.status = :status")
    BigDecimal sumAmountByPhotographerIdAndStatus(@Param("photographerId") Long photographerId, @Param("status") PayoutStatus status);

    /**
     * Find most recent payout for a photographer.
     *
     * @param photographerId the photographer ID
     * @return optional containing the most recent payout
     */
    @Query(value = "SELECT * FROM payouts WHERE photographer_id = :photographerId ORDER BY created_at DESC LIMIT 1",
           nativeQuery = true)
    Optional<Payout> findMostRecentPayoutForPhotographer(@Param("photographerId") Long photographerId);

    /**
     * Find payouts requiring review.
     *
     * @param pageable pagination information
     * @return page of payouts
     */
    @Query("SELECT p FROM Payout p WHERE p.status = 'PENDING' " +
           "ORDER BY p.createdAt ASC")
    Page<Payout> findPayoutsRequiringReview(Pageable pageable);
}
