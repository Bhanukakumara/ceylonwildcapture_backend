package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for earnings information.
 * Provides earnings summary and breakdown.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EarningsResponseDto {

    private Long photographerId;

    private String photographerUsername;

    private BigDecimal totalEarnings;

    private BigDecimal pendingEarnings;

    private BigDecimal paidEarnings;

    private BigDecimal completedOrdersAmount;

    private BigDecimal refundedAmount;

    private Long totalOrders;

    private Long completedOrders;

    private Long refundedOrders;

    private BigDecimal averageOrderValue;

    private LocalDateTime lastUpdated;

    private LocalDateTime lastPayoutDate;

    /**
     * Calculate average order value.
     *
     * @return average order value
     */
    public BigDecimal calculateAverageOrderValue() {
        if (completedOrders == null || completedOrders == 0) {
            return BigDecimal.ZERO;
        }
        return completedOrdersAmount.divide(BigDecimal.valueOf(completedOrders), 2, java.math.RoundingMode.HALF_UP);
    }
}
