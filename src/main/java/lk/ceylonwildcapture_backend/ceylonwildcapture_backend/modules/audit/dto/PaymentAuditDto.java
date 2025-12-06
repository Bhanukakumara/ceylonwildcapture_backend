package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentAuditDto {
    private Long id;
    private Long userId;
    private String userName;
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
