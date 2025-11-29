package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for refund request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundRequestDto {

    @NotNull(message = "Payment ID is required")
    private Long paymentId;

    @Positive(message = "Refund amount must be positive")
    private BigDecimal amount;

    private String reason;

    private Boolean isFullRefund;
}
