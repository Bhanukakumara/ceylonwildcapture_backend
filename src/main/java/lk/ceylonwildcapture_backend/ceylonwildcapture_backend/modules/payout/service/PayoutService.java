package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.PayoutStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.PayoutResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.filter.PayoutSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service interface for payout operations.
 * Provides methods for managing payout records and status updates.
 */
public interface PayoutService {

    /**
     * Get payout by ID.
     *
     * @param payoutId the payout ID
     * @return optional containing the payout response DTO
     */
    Optional<PayoutResponseDto> getPayoutById(Long payoutId);

    /**
     * Get payout by reference.
     *
     * @param payoutReference the payout reference
     * @return optional containing the payout response DTO
     */
    Optional<PayoutResponseDto> getPayoutByReference(String payoutReference);

    /**
     * Get all payouts with pagination.
     *
     * @param pageable pagination information
     * @return page of payout response DTOs
     */
    Page<PayoutResponseDto> getAllPayouts(Pageable pageable);

    /**
     * Get payouts for a photographer.
     *
     * @param photographerId the photographer ID
     * @param pageable pagination information
     * @return page of payout response DTOs
     */
    Page<PayoutResponseDto> getPayoutsByPhotographer(Long photographerId, Pageable pageable);

    /**
     * Get payouts by status.
     *
     * @param status the payout status
     * @param pageable pagination information
     * @return page of payout response DTOs
     */
    Page<PayoutResponseDto> getPayoutsByStatus(PayoutStatus status, Pageable pageable);

    /**
     * Get payouts within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of payout response DTOs
     */
    Page<PayoutResponseDto> getPayoutsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Search payouts using criteria.
     *
     * @param criteria the search criteria
     * @param pageable pagination information
     * @return page of payout response DTOs
     */
    Page<PayoutResponseDto> searchPayouts(PayoutSearchCriteria criteria, Pageable pageable);

    /**
     * Get payout history for a photographer.
     *
     * @param photographerId the photographer ID
     * @param pageable pagination information
     * @return page of payout response DTOs
     */
    Page<PayoutResponseDto> getPayoutHistory(Long photographerId, Pageable pageable);

    /**
     * Get pending payouts for a photographer.
     *
     * @param photographerId the photographer ID
     * @param pageable pagination information
     * @return page of payout response DTOs
     */
    Page<PayoutResponseDto> getPendingPayouts(Long photographerId, Pageable pageable);

    /**
     * Get completed payouts for a photographer.
     *
     * @param photographerId the photographer ID
     * @param pageable pagination information
     * @return page of payout response DTOs
     */
    Page<PayoutResponseDto> getCompletedPayouts(Long photographerId, Pageable pageable);

    /**
     * Count payouts by status.
     *
     * @param status the payout status
     * @return count of payouts
     */
    long countPayoutsByStatus(PayoutStatus status);

    /**
     * Count payouts for a photographer.
     *
     * @param photographerId the photographer ID
     * @return count of payouts
     */
    long countPayoutsForPhotographer(Long photographerId);

    /**
     * Get total payout amount by status.
     *
     * @param status the payout status
     * @return total amount
     */
    BigDecimal getTotalPayoutAmountByStatus(PayoutStatus status);

    /**
     * Get total payout amount for a photographer by status.
     *
     * @param photographerId the photographer ID
     * @param status the payout status
     * @return total amount
     */
    BigDecimal getTotalPayoutAmountForPhotographer(Long photographerId, PayoutStatus status);

    /**
     * Get most recent payout for a photographer.
     *
     * @param photographerId the photographer ID
     * @return optional containing the most recent payout response DTO
     */
    Optional<PayoutResponseDto> getMostRecentPayoutForPhotographer(Long photographerId);

    /**
     * Get payouts requiring review.
     *
     * @param pageable pagination information
     * @return page of payout response DTOs
     */
    Page<PayoutResponseDto> getPayoutsRequiringReview(Pageable pageable);

    /**
     * Mark payout as completed.
     *
     * @param payoutId the payout ID
     * @param transactionId the transaction ID
     * @return the updated payout response DTO
     */
    PayoutResponseDto markPayoutCompleted(Long payoutId, String transactionId);

    /**
     * Get payout statistics.
     *
     * @return map containing payout statistics
     */
    java.util.Map<String, Object> getPayoutStatistics();
}
