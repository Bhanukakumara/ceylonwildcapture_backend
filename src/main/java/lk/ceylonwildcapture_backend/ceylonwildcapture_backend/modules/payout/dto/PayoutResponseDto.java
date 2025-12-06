package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.PayoutStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.enums.PayoutMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for payout responses.
 * Contains complete payout information safe to expose in API responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayoutResponseDto {

    private Long id;

    private String payoutReference;

    private Long photographerId;

    private String photographerUsername;

    private BigDecimal amount;

    private BigDecimal fee;

    private BigDecimal netAmount;

    private PayoutStatus status;

    private PayoutMethod payoutMethod;

    private String bankAccountNumber;

    private String paypalEmail;

    private String cryptoWalletAddress;

    private LocalDateTime requestedAt;

    private LocalDateTime approvedAt;

    private LocalDateTime completedAt;

    private LocalDateTime rejectedAt;

    private String rejectionReason;

    private String notes;

    private String transactionId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Get a human-readable summary of the payout.
     *
     * @return summary string
     */
    public String getSummary() {
        return String.format("Payout %s - %s (%s) - Status: %s",
                payoutReference,
                amount,
                payoutMethod,
                status);
    }
}
