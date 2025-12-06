package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for PaymentAudit entity.
 * Used for transferring payment audit information in API responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAuditDto {

    private Long id;

    private Long userId;

    private String username;

    private Long paymentId;

    private Long orderId;

    private String action;

    private ActionResult actionResult;

    private BigDecimal amount;

    private String currency;

    private String paymentMethod;

    private String transactionId;

    private String metadata;

    private String errorMessage;

    private String ipAddress;

    private LocalDateTime createdAt;
}
