package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for UserActivityAudit entity.
 * Used for transferring user activity audit information in API responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityAuditDto {

    private Long id;

    private Long userId;

    private String username;

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
