package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.PhotographerProfile;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for PhotographerProfile entity.
 * Provides database operations for photographer profile management and analytics.
 */
@Repository
public interface PhotographerProfileRepository extends JpaRepository<PhotographerProfile, Long> {

    /**
     * Find photographer profile by user.
     *
     * @param user the user entity
     * @return Optional containing the photographer profile if found
     */
    Optional<PhotographerProfile> findByUser(User user);

    /**
     * Find photographer profile by user ID.
     *
     * @param userId the user ID
     * @return Optional containing the photographer profile if found
     */
    Optional<PhotographerProfile> findByUserId(Long userId);

    /**
     * Check if a photographer profile exists for a user.
     *
     * @param user the user entity
     * @return true if profile exists
     */
    boolean existsByUser(User user);

    /**
     * Check if a photographer profile exists by user ID.
     *
     * @param userId the user ID
     * @return true if profile exists
     */
    boolean existsByUserId(Long userId);

    /**
     * Find all verified photographers.
     *
     * @param verifiedPhotographer the verification status
     * @param pageable pagination information
     * @return page of verified photographer profiles
     */
    Page<PhotographerProfile> findByVerifiedPhotographer(Boolean verifiedPhotographer, Pageable pageable);

    /**
     * Find photographers with pending earnings above a threshold.
     *
     * @param minAmount the minimum pending earnings amount
     * @return list of photographer profiles with pending earnings above threshold
     */
    @Query("SELECT pp FROM PhotographerProfile pp WHERE pp.pendingEarnings >= :minAmount")
    List<PhotographerProfile> findByPendingEarningsGreaterThanEqual(@Param("minAmount") BigDecimal minAmount);

    /**
     * Find photographers with total earnings above a threshold.
     *
     * @param minAmount the minimum total earnings amount
     * @param pageable pagination information
     * @return page of photographer profiles with total earnings above threshold
     */
    @Query("SELECT pp FROM PhotographerProfile pp WHERE pp.totalEarnings >= :minAmount ORDER BY pp.totalEarnings DESC")
    Page<PhotographerProfile> findByTotalEarningsGreaterThanEqual(
            @Param("minAmount") BigDecimal minAmount, Pageable pageable);

    /**
     * Find top photographers by total sales.
     *
     * @param pageable pagination information
     * @return page of top photographer profiles by sales
     */
    Page<PhotographerProfile> findAllByOrderByTotalSalesDesc(Pageable pageable);

    /**
     * Find top photographers by rating.
     *
     * @param minReviews minimum number of reviews required
     * @param pageable pagination information
     * @return page of top-rated photographer profiles
     */
    @Query("SELECT pp FROM PhotographerProfile pp WHERE pp.totalReviews >= :minReviews ORDER BY pp.rating DESC")
    Page<PhotographerProfile> findTopRatedPhotographers(
            @Param("minReviews") Integer minReviews, Pageable pageable);

    /**
     * Find photographers by rating range.
     *
     * @param minRating minimum rating
     * @param maxRating maximum rating
     * @param pageable pagination information
     * @return page of photographer profiles within rating range
     */
    @Query("SELECT pp FROM PhotographerProfile pp WHERE pp.rating BETWEEN :minRating AND :maxRating ORDER BY pp.rating DESC")
    Page<PhotographerProfile> findByRatingBetween(
            @Param("minRating") BigDecimal minRating,
            @Param("maxRating") BigDecimal maxRating,
            Pageable pageable);

    /**
     * Find photographers with commission rate.
     *
     * @param commissionRate the commission rate
     * @return list of photographer profiles with specified commission rate
     */
    List<PhotographerProfile> findByCommissionRate(BigDecimal commissionRate);

    /**
     * Find photographers with PayPal account configured.
     *
     * @return list of photographer profiles with PayPal email set
     */
    @Query("SELECT pp FROM PhotographerProfile pp WHERE pp.paypalEmail IS NOT NULL")
    List<PhotographerProfile> findWithPayPalConfigured();

    /**
     * Find photographers with bank account configured.
     *
     * @return list of photographer profiles with bank account set
     */
    @Query("SELECT pp FROM PhotographerProfile pp WHERE pp.bankAccountNumber IS NOT NULL")
    List<PhotographerProfile> findWithBankAccountConfigured();

    /**
     * Count verified photographers.
     *
     * @param verifiedPhotographer the verification status
     * @return count of verified photographers
     */
    long countByVerifiedPhotographer(Boolean verifiedPhotographer);

    /**
     * Calculate total pending earnings across all photographers.
     *
     * @return total pending earnings
     */
    @Query("SELECT SUM(pp.pendingEarnings) FROM PhotographerProfile pp")
    BigDecimal calculateTotalPendingEarnings();

    /**
     * Calculate total earnings across all photographers.
     *
     * @return total earnings
     */
    @Query("SELECT SUM(pp.totalEarnings) FROM PhotographerProfile pp")
    BigDecimal calculateTotalEarnings();

    /**
     * Find photographers with no sales.
     *
     * @return list of photographer profiles with zero sales
     */
    @Query("SELECT pp FROM PhotographerProfile pp WHERE pp.totalSales = 0")
    List<PhotographerProfile> findPhotographersWithNoSales();

    /**
     * Search photographer profiles by bio content.
     *
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of photographer profiles matching search
     */
    @Query("SELECT pp FROM PhotographerProfile pp WHERE LOWER(pp.bio) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<PhotographerProfile> searchByBio(@Param("searchTerm") String searchTerm, Pageable pageable);
}
