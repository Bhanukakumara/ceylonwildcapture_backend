package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for payment verification request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentVerificationRequestDto {

    @NotBlank(message = "Payment ID is required")
    private String paymentId;

    private String signature;

    private String orderId;

    private Map<String, String> verificationData;
}
