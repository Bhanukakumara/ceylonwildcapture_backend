package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentProvider;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.WebhookEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO for webhook event.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebhookEventDto {

    private Long id;
    private String eventId;
    private PaymentProvider provider;
    private WebhookEventType eventType;
    private String paymentId;
    private Map<String, Object> data;
    private Boolean processed;
    private LocalDateTime processedAt;
    private String processingError;
    private Integer retryCount;
    private LocalDateTime createdAt;
}
