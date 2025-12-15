package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.PayoutResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.PayoutReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Service interface for payout review operations.
 * Provides methods for admin review and approval/rejection of payouts.
 */
public interface PayoutReviewService {

    /**
     * Approve a payout request.
     *
     * @param payoutReviewDto the payout review DTO
     * @return the approved payout response DTO
     */
    PayoutResponseDto approvePayout(PayoutReviewDto payoutReviewDto);

    /**
     * Reject a payout request.
     *
     * @param payoutReviewDto the payout review DTO
     * @return the rejected payout response DTO
     */
    PayoutResponseDto rejectPayout(PayoutReviewDto payoutReviewDto);

    /**
     * Get payouts pending review.
     *
     * @param pageable pagination information
     * @return page of payout response DTOs
     */
    Page<PayoutResponseDto> getPayoutsPendingReview(Pageable pageable);

    /**
     * Get payouts pending review count.
     *
     * @return count of pending payouts
     */
    long countPayoutsPendingReview();

    /**
     * Validate payout before approval.
     *
     * @param payoutId the payout ID
     * @return list of validation errors (empty if valid)
     */
    java.util.List<String> validatePayoutForApproval(Long payoutId);

    /**
     * Get review history for a payout.
     *
     * @param payoutId the payout ID
     * @param pageable pagination information
     * @return page of payout audit DTOs
     */
    Page<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.PayoutAuditDto> getPayoutReviewHistory(Long payoutId, Pageable pageable);

    /**
     * Get review statistics.
     *
     * @return map containing review statistics
     */
    Map<String, Object> getReviewStatistics();

    /**
     * Get average review time.
     *
     * @return average review time in hours
     */
    double getAverageReviewTime();

    /**
     * Get approval rate.
     *
     * @return approval rate as percentage
     */
    double getApprovalRate();

    /**
     * Get rejection rate.
     *
     * @return rejection rate as percentage
     */
    double getRejectionRate();
}
