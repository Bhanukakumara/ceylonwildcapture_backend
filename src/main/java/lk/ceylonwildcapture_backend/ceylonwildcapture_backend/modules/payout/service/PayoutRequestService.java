package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.PayoutRequestDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.PayoutResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.entity.Payout;

import java.math.BigDecimal;

/**
 * Service interface for payout request operations.
 * Provides methods for submitting and validating payout requests.
 */
public interface PayoutRequestService {

    /**
     * Submit a new payout request.
     *
     * @param payoutRequestDto the payout request DTO
     * @return the created payout response DTO
     */
    PayoutResponseDto submitPayoutRequest(PayoutRequestDto payoutRequestDto);

    /**
     * Validate payout request eligibility.
     *
     * @param photographerId the photographer ID
     * @param requestedAmount the requested amount
     * @return true if photographer is eligible for payout
     */
    boolean validatePayoutEligibility(Long photographerId, BigDecimal requestedAmount);

    /**
     * Check if photographer has pending payout requests.
     *
     * @param photographerId the photographer ID
     * @return true if photographer has pending requests
     */
    boolean hasPendingPayoutRequests(Long photographerId);

    /**
     * Cancel a payout request.
     *
     * @param payoutId the payout ID
     * @return the cancelled payout response DTO
     */
    PayoutResponseDto cancelPayoutRequest(Long payoutId);

    /**
     * Get validation errors for a payout request.
     *
     * @param payoutRequestDto the payout request DTO
     * @return list of validation error messages
     */
    java.util.List<String> getPayoutRequestValidationErrors(PayoutRequestDto payoutRequestDto);

    /**
     * Check if payout amount is within acceptable range.
     *
     * @param amount the payout amount
     * @return true if amount is acceptable
     */
    boolean isPayoutAmountValid(BigDecimal amount);

    /**
     * Get minimum payout amount.
     *
     * @return minimum payout amount
     */
    BigDecimal getMinimumPayoutAmount();

    /**
     * Get maximum payout amount.
     *
     * @return maximum payout amount
     */
    BigDecimal getMaximumPayoutAmount();
}
