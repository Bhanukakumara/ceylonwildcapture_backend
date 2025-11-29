package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for payment failure callback.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFailureCallbackDto {

    @NotBlank(message = "Payment ID is required")
    private String paymentId;

    private String errorCode;

    private String errorMessage;

    private String reason;

    private Map<String, Object> metadata;
}
