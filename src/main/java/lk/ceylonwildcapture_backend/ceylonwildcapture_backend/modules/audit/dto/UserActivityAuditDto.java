package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class UserActivityAuditDto {
    private Long id;
    private Long userId;
    private String userName;
    private String action;
    private ActionResult actionResult;
    private String description;
    private String oldValue;
    private String newValue;
    private String metadata;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime createdAt;
}
