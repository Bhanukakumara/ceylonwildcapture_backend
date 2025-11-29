package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentProvider;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for payment intent response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentIntentResponseDto {

    private Long paymentId;
    private String clientSecret;
    private String providerPaymentId;
    private PaymentProvider provider;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private String redirectUrl;
    private String publicKey;
    private LocalDateTime expiresAt;
    private Long orderId;
    private String orderNumber;
}
