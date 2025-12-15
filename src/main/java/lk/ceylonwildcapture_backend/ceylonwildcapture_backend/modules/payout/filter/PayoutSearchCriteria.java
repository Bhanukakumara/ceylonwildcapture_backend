package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.filter;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.PayoutStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.enums.PayoutMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Search criteria for filtering payout records.
 * Supports multi-dimensional filtering for advanced queries.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayoutSearchCriteria {

    /**
     * Filter by photographer ID
     */
    private Long photographerId;

    /**
     * Filter by payout status
     */
    private PayoutStatus status;

    /**
     * Filter by payout method
     */
    private PayoutMethod payoutMethod;

    /**
     * Filter by minimum amount
     */
    private BigDecimal minAmount;

    /**
     * Filter by maximum amount
     */
    private BigDecimal maxAmount;

    /**
     * Filter by start date
     */
    private LocalDateTime startDate;

    /**
     * Filter by end date
     */
    private LocalDateTime endDate;

    /**
     * Filter by payout reference
     */
    private String payoutReference;

    /**
     * Filter by admin ID (for audits)
     */
    private Long adminId;

    /**
     * Check if criteria has any filters applied.
     *
     * @return true if at least one filter is set
     */
    public boolean hasFilters() {
        return photographerId != null ||
               status != null ||
               payoutMethod != null ||
               minAmount != null ||
               maxAmount != null ||
               startDate != null ||
               endDate != null ||
               payoutReference != null ||
               adminId != null;
    }

    /**
     * Check if date range is valid.
     *
     * @return true if both dates are set and start is before end
     */
    public boolean isDateRangeValid() {
        return startDate != null && endDate != null && startDate.isBefore(endDate);
    }

    /**
     * Check if amount range is valid.
     *
     * @return true if both amounts are set and min is less than max
     */
    public boolean isAmountRangeValid() {
        return minAmount != null && maxAmount != null && minAmount.compareTo(maxAmount) <= 0;
    }
}
