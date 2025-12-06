package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object for payout summaries.
 * Provides aggregated payout information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayoutSummaryDto {

    private Long photographerId;

    private String photographerUsername;

    private Long totalPayouts;

    private Long pendingPayouts;

    private Long approvedPayouts;

    private Long completedPayouts;

    private Long rejectedPayouts;

    private BigDecimal totalAmount;

    private BigDecimal pendingAmount;

    private BigDecimal approvedAmount;

    private BigDecimal completedAmount;

    private BigDecimal rejectedAmount;

    private BigDecimal totalFees;

    private BigDecimal averagePayoutAmount;

    private String mostRecentPayoutReference;

    private String mostRecentPayoutStatus;
}
