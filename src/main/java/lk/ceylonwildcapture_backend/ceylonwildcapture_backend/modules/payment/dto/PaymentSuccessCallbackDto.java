package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for payment success callback.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSuccessCallbackDto {

    @NotBlank(message = "Payment ID is required")
    private String paymentId;

    @NotBlank(message = "Transaction ID is required")
    private String transactionId;

    private String signature;

    private String status;

    private Map<String, Object> metadata;
}
