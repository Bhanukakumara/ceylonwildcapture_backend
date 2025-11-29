package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for payout review actions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayoutReviewDto {

    @NotNull(message = "Payout ID is required")
    private Long payoutId;

    @NotNull(message = "Action is required")
    private PayoutAction action;

    private String reviewNotes;

    private String rejectionReason;

    public enum PayoutAction {
        APPROVE,
        REJECT,
        HOLD,
        RELEASE
    }
}
