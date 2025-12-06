package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for AdminActionAudit entity.
 * Used for transferring admin action audit information in API responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminActionAuditDto {

    private Long id;

    private Long adminId;

    private String adminUsername;

    private EntityType entityType;

    private Long entityId;

    private String action;

    private ActionResult actionResult;

    private String reason;

    private String metadata;

    private String ipAddress;

    private String userAgent;

    private LocalDateTime createdAt;
}
