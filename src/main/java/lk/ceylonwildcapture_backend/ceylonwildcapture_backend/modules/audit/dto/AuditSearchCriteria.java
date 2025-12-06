package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.AuditType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuditSearchCriteria {
    private AuditType auditType;
    private Long userId;
    private Long adminId;
    private Long photoId; // Mapped to entityId if type is PHOTO
    private Long orderId;
    private Long licenseId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String actionType; // For generic action string
    private Boolean successFlag; // Derived from ActionResult
    private ActionResult actionResult;
    private Long entityId;
}
