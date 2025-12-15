package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.util;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Utility interface for calculating earnings from completed order items.
 * Provides methods for computing payouts and fees.
 */
public interface PayoutCalculator {

    /**
     * Calculate earnings from a completed order item.
     *
     * @param orderItemId the order item ID
     * @return calculated earnings amount
     */
    BigDecimal calculateEarningsFromOrderItem(Long orderItemId);

    /**
     * Calculate total earnings for a photographer from completed orders.
     *
     * @param photographerId the photographer ID
     * @return total earnings amount
     */
    BigDecimal calculateTotalEarningsForPhotographer(Long photographerId);

    /**
     * Calculate pending earnings for a photographer.
     *
     * @param photographerId the photographer ID
     * @return pending earnings amount
     */
    BigDecimal calculatePendingEarningsForPhotographer(Long photographerId);

    /**
     * Calculate payout fee based on amount and method.
     *
     * @param amount the payout amount
     * @param payoutMethod the payout method
     * @return calculated fee
     */
    BigDecimal calculatePayoutFee(BigDecimal amount, String payoutMethod);

    /**
     * Calculate net payout amount after fees.
     *
     * @param amount the payout amount
     * @param fee the payout fee
     * @return net payout amount
     */
    BigDecimal calculateNetPayoutAmount(BigDecimal amount, BigDecimal fee);

    /**
     * Calculate earnings breakdown for a photographer.
     *
     * @param photographerId the photographer ID
     * @return map containing earnings breakdown
     */
    Map<String, BigDecimal> calculateEarningsBreakdown(Long photographerId);

    /**
     * Calculate refunded amount for a photographer.
     *
     * @param photographerId the photographer ID
     * @return refunded amount
     */
    BigDecimal calculateRefundedAmount(Long photographerId);

    /**
     * Validate if photographer has minimum earnings for payout.
     *
     * @param photographerId the photographer ID
     * @param minimumAmount the minimum amount required
     * @return true if photographer has sufficient earnings
     */
    boolean hasMinimumEarningsForPayout(Long photographerId, BigDecimal minimumAmount);

    /**
     * Calculate earnings for a date range.
     *
     * @param photographerId the photographer ID
     * @param startDate the start date as string (YYYY-MM-DD)
     * @param endDate the end date as string (YYYY-MM-DD)
     * @return earnings for the date range
     */
    BigDecimal calculateEarningsForDateRange(Long photographerId, String startDate, String endDate);

    /**
     * Get fee percentage for a payout method.
     *
     * @param payoutMethod the payout method
     * @return fee percentage
     */
    BigDecimal getFeePercentageForMethod(String payoutMethod);
}
