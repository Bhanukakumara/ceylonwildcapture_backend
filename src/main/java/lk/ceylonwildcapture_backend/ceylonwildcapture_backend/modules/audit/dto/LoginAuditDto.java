package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class LoginAuditDto {
    private Long id;
    private Long userId;
    private String userName;
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
