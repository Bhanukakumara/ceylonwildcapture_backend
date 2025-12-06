package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.PayoutStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for payout review operations.
 * Used for admin approval/rejection of payouts.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayoutReviewDto {

    @NotNull(message = "Payout ID is required")
    private Long payoutId;

    @NotNull(message = "Decision is required")
    private PayoutStatus decision;

    @NotBlank(message = "Reason is required")
    private String reason;

    private String notes;

    private String transactionId;

    private String metadata;
}
