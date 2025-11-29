package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.PayoutReviewDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.entity.Payout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
 * Service interface for payout review and approval operations.
 */
public interface PayoutReviewService {

    /**
     * Review a payout request (approve, reject, hold, release).
     *
     * @param reviewDto payout review details
     * @param adminId ID of the admin reviewing
     * @return updated payout
     */
    Payout reviewPayout(PayoutReviewDto reviewDto, Long adminId);

    /**
     * Approve a payout request.
     *
     * @param payoutId ID of the payout
     * @param adminId ID of the admin
     * @return approved payout
     */
    Payout approvePayout(Long payoutId, Long adminId);

    /**
     * Reject a payout request.
     *
     * @param payoutId ID of the payout
     * @param reason rejection reason
     * @param adminId ID of the admin
     * @return rejected payout
     */
    Payout rejectPayout(Long payoutId, String reason, Long adminId);

    /**
     * Hold a payout for further review.
     *
     * @param payoutId ID of the payout
     * @param notes review notes
     * @param adminId ID of the admin
     * @return held payout
     */
    Payout holdPayout(Long payoutId, String notes, Long adminId);

    /**
     * Release a held payout.
     *
     * @param payoutId ID of the payout
     * @param adminId ID of the admin
     * @return released payout
     */
    Payout releasePayout(Long payoutId, Long adminId);

    /**
     * Get all pending payouts.
     *
     * @param pageable pagination parameters
     * @return page of pending payouts
     */
    Page<Payout> getPendingPayouts(Pageable pageable);

    /**
     * Get all approved payouts.
     *
     * @param pageable pagination parameters
     * @return page of approved payouts
     */
    Page<Payout> getApprovedPayouts(Pageable pageable);

    /**
     * Get all rejected payouts.
     *
     * @param pageable pagination parameters
     * @return page of rejected payouts
     */
    Page<Payout> getRejectedPayouts(Pageable pageable);

    /**
     * Get all held payouts.
     *
     * @param pageable pagination parameters
     * @return page of held payouts
     */
    Page<Payout> getHeldPayouts(Pageable pageable);

    /**
     * Get payouts for a specific photographer.
     *
     * @param photographerId ID of the photographer
     * @param pageable pagination parameters
     * @return page of photographer's payouts
     */
    Page<Payout> getPhotographerPayouts(Long photographerId, Pageable pageable);

    /**
     * Get total pending payout amount.
     *
     * @return total pending amount
     */
    BigDecimal getTotalPendingAmount();

    /**
     * Get payout statistics.
     *
     * @return payout statistics map
     */
    java.util.Map<String, Object> getPayoutStatistics();

    Payout markPayoutAsFailed(Long payoutId, String reason, Long adminId);

    Payout processPayout(Long payoutId, String transactionId, Long adminId);

    Page<Payout> getPayoutsAboveThreshold(BigDecimal threshold, Pageable pageable);

    Payout getPayoutDetails(Long payoutId);
}
