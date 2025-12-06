package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.AuditType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class AuditEventResponse {
    private Long id;
    private AuditType auditType;
    private Long actorId;
    private String actorName; // Username or Admin name
    private String actorType; // USER, ADMIN, SYSTEM
    private String entityType;
    private Long entityId;
    private String action;
    private ActionResult actionResult;
    private String description;
    private String metadata; // Raw JSON or Map
    private String ipAddress;
    private LocalDateTime createdAt;

    // Optional details depending on type
    private Map<String, Object> details;
}
