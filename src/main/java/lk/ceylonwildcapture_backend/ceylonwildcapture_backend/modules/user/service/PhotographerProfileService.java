package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.PhotographerProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for PhotographerProfile management operations.
 * Defines business logic for photographer profile CRUD, verification, earnings, and analytics.
 */
public interface PhotographerProfileService {

    /**
     * Create a new photographer profile.
     *
     * @param photographerProfile the photographer profile to create
     * @return the created photographer profile
     * @throws IllegalArgumentException if profile data is invalid
     */
    PhotographerProfile createProfile(PhotographerProfile photographerProfile);

    /**
     * Create photographer profile for user.
     *
     * @param userId the user ID
     * @param photographerProfile the photographer profile data
     * @return the created photographer profile
     * @throws IllegalArgumentException if user not found or profile already exists
     */
    PhotographerProfile createProfileForUser(Long userId, PhotographerProfile photographerProfile);

    /**
     * Update photographer profile.
     *
     * @param profileId the profile ID
     * @param photographerProfile the updated profile data
     * @return the updated photographer profile
     * @throws IllegalArgumentException if profile not found
     */
    PhotographerProfile updateProfile(Long profileId, PhotographerProfile photographerProfile);

    /**
     * Get photographer profile by ID.
     *
     * @param profileId the profile ID
     * @return Optional containing the profile if found
     */
    Optional<PhotographerProfile> getProfileById(Long profileId);

    /**
     * Get photographer profile by user ID.
     *
     * @param userId the user ID
     * @return Optional containing the profile if found
     */
    Optional<PhotographerProfile> getProfileByUserId(Long userId);

    /**
     * Get all photographer profiles with pagination.
     *
     * @param pageable pagination information
     * @return page of photographer profiles
     */
    Page<PhotographerProfile> getAllProfiles(Pageable pageable);

    /**
     * Get verified photographer profiles.
     *
     * @param pageable pagination information
     * @return page of verified photographer profiles
     */
    Page<PhotographerProfile> getVerifiedProfiles(Pageable pageable);

    /**
     * Delete photographer profile.
     *
     * @param profileId the profile ID
     * @throws IllegalArgumentException if profile not found
     */
    void deleteProfile(Long profileId);

    /**
     * Verify photographer profile.
     *
     * @param profileId the profile ID
     * @return the verified photographer profile
     * @throws IllegalArgumentException if profile not found
     */
    PhotographerProfile verifyPhotographer(Long profileId);

    /**
     * Unverify photographer profile.
     *
     * @param profileId the profile ID
     * @return the unverified photographer profile
     * @throws IllegalArgumentException if profile not found
     */
    PhotographerProfile unverifyPhotographer(Long profileId);

    /**
     * Update photographer bio.
     *
     * @param profileId the profile ID
     * @param bio the updated bio
     * @return the updated photographer profile
     * @throws IllegalArgumentException if profile not found
     */
    PhotographerProfile updateBio(Long profileId, String bio);

    /**
     * Update photographer portfolio URL.
     *
     * @param profileId the profile ID
     * @param portfolioUrl the portfolio URL
     * @return the updated photographer profile
     * @throws IllegalArgumentException if profile not found
     */
    PhotographerProfile updatePortfolioUrl(Long profileId, String portfolioUrl);

    /**
     * Update photographer social media links.
     *
     * @param profileId the profile ID
     * @param socialMediaLinks the social media links JSON
     * @return the updated photographer profile
     * @throws IllegalArgumentException if profile not found
     */
    PhotographerProfile updateSocialMediaLinks(Long profileId, String socialMediaLinks);

    /**
     * Update photographer bank account details.
     *
     * @param profileId the profile ID
     * @param bankAccountNumber the bank account number
     * @param bankName the bank name
     * @return the updated photographer profile
     * @throws IllegalArgumentException if profile not found
     */
    PhotographerProfile updateBankDetails(Long profileId, String bankAccountNumber, String bankName);

    /**
     * Update photographer PayPal email.
     *
     * @param profileId the profile ID
     * @param paypalEmail the PayPal email
     * @return the updated photographer profile
     * @throws IllegalArgumentException if profile not found
     */
    PhotographerProfile updatePayPalEmail(Long profileId, String paypalEmail);

    /**
     * Update photographer commission rate.
     *
     * @param profileId the profile ID
     * @param commissionRate the new commission rate
     * @return the updated photographer profile
     * @throws IllegalArgumentException if profile not found or rate invalid
     */
    PhotographerProfile updateCommissionRate(Long profileId, BigDecimal commissionRate);

    /**
     * Add earnings to photographer profile.
     *
     * @param profileId the profile ID
     * @param amount the earnings amount
     * @return the updated photographer profile
     * @throws IllegalArgumentException if profile not found
     */
    PhotographerProfile addEarnings(Long profileId, BigDecimal amount);

    /**
     * Move pending earnings to total earnings.
     *
     * @param profileId the profile ID
     * @param amount the amount to transfer
     * @return the updated photographer profile
     * @throws IllegalArgumentException if profile not found or insufficient pending earnings
     */
    PhotographerProfile transferPendingEarnings(Long profileId, BigDecimal amount);

    /**
     * Increment photographer sales count.
     *
     * @param profileId the profile ID
     * @return the updated photographer profile
     * @throws IllegalArgumentException if profile not found
     */
    PhotographerProfile incrementSalesCount(Long profileId);

    /**
     * Update photographer rating.
     *
     * @param profileId the profile ID
     * @param rating the new rating
     * @return the updated photographer profile
     * @throws IllegalArgumentException if profile not found or rating invalid
     */
    PhotographerProfile updateRating(Long profileId, BigDecimal rating);

    /**
     * Increment photographer review count.
     *
     * @param profileId the profile ID
     * @return the updated photographer profile
     * @throws IllegalArgumentException if profile not found
     */
    PhotographerProfile incrementReviewCount(Long profileId);

    /**
     * Get top photographers by total sales.
     *
     * @param pageable pagination information
     * @return page of top photographers
     */
    Page<PhotographerProfile> getTopPhotographersBySales(Pageable pageable);

    /**
     * Get top photographers by rating.
     *
     * @param minReviews minimum number of reviews required
     * @param pageable pagination information
     * @return page of top-rated photographers
     */
    Page<PhotographerProfile> getTopPhotographersByRating(Integer minReviews, Pageable pageable);

    /**
     * Get photographers with pending earnings above threshold.
     *
     * @param minAmount the minimum pending earnings amount
     * @return list of photographers with pending earnings above threshold
     */
    List<PhotographerProfile> getPhotographersWithPendingEarnings(BigDecimal minAmount);

    /**
     * Get photographers with no sales.
     *
     * @return list of photographers with zero sales
     */
    List<PhotographerProfile> getPhotographersWithNoSales();

    /**
     * Search photographer profiles by bio.
     *
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of photographer profiles matching search
     */
    Page<PhotographerProfile> searchByBio(String searchTerm, Pageable pageable);

    /**
     * Calculate total pending earnings across all photographers.
     *
     * @return total pending earnings
     */
    BigDecimal calculateTotalPendingEarnings();

    /**
     * Calculate total earnings across all photographers.
     *
     * @return total earnings
     */
    BigDecimal calculateTotalEarnings();

    /**
     * Check if photographer has payout method configured.
     *
     * @param profileId the profile ID
     * @return true if payout method is configured
     */
    boolean hasPayoutMethodConfigured(Long profileId);

    /**
     * Count verified photographers.
     *
     * @return count of verified photographers
     */
    long countVerifiedPhotographers();
}
