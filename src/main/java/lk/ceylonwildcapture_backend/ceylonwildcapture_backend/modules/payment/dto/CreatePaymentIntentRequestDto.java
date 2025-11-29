package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for creating a payment intent request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentIntentRequestDto {

    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Payment provider is required")
    private PaymentProvider provider;

    private String currency;

    private String returnUrl;

    private String cancelUrl;

    private String description;

    private Boolean savePaymentMethod;
}
