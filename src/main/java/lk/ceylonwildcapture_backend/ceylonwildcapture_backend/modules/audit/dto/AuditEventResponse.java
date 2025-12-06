package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.AuditType;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Unified Data Transfer Object for audit events.
 * Provides a consistent response format for all audit types.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditEventResponse {

    private Long id;

    private AuditType auditType;

    private EntityType entityType;

    private Long entityId;

    private Long actorId;

    private String actorUsername;

    private Long targetUserId;

    private String targetUsername;

    private String action;

    private ActionResult actionResult;

    private String description;

    private String ipAddress;

    private String userAgent;

    private String device;

    private String browser;

    private String operatingSystem;

    private String country;

    private String city;

    private String metadata;

    private String errorMessage;

    private LocalDateTime timestamp;

    /**
     * Get a human-readable summary of the audit event.
     *
     * @return summary string
     */
    public String getSummary() {
        return String.format("%s - %s (%s) by %s at %s",
                auditType,
                action,
                actionResult,
                actorUsername != null ? actorUsername : "System",
                timestamp);
    }
}
