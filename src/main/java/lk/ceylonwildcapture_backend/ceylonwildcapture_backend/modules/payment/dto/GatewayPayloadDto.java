package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

/**
 * DTO for gateway-specific payload.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GatewayPayloadDto {

    private String orderId;
    private BigDecimal amount;
    private String currency;
    private String description;
    private String customerEmail;
    private String customerName;
    private String returnUrl;
    private String cancelUrl;
    private String webhookUrl;
    private Map<String, Object> metadata;
    private Map<String, Object> customFields;
}
