package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.enums.PayoutMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object for payout requests.
 * Used for submitting new payout requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayoutRequestDto {

    @NotNull(message = "Photographer ID is required")
    private Long photographerId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Payout method is required")
    private PayoutMethod payoutMethod;

    private String bankAccountNumber;

    private String bankRoutingNumber;

    private String bankAccountHolderName;

    private String paypalEmail;

    private String cryptoWalletAddress;

    private String notes;
}
