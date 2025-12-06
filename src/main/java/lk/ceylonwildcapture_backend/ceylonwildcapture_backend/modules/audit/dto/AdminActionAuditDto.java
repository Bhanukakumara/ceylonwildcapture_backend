package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class AdminActionAuditDto {
    private Long id;
    private Long adminId;
    private String adminName;
    private String entityType; // Using String to decouple
    private Long entityId;
    private String action;
    private ActionResult actionResult;
    private String reason;
    private String metadata; // JSON string
    private String ipAddress;
    private String userAgent;
    private LocalDateTime createdAt;
}
