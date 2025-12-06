package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for LoginAudit entity.
 * Used for transferring login audit information in API responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginAuditDto {

    private Long id;

    private Long userId;

    private String username;

    private ActionResult actionResult;

    private String ipAddress;

    private String userAgent;

    private String device;

    private String browser;

    private String operatingSystem;

    private String country;

    private String city;

    private String errorMessage;

    private LocalDateTime createdAt;
}
