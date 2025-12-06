package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class DownloadAuditDto {
    private Long id;
    private Long licenseId;
    private Long userId;
    private String userName; // Optional, if loaded
    private String ipAddress;
    private String userAgent;
    private String downloadUrl;
    private Long fileSize;
    private Boolean downloadSuccessful;
    private String errorMessage;
    private String device;
    private String browser;
    private String operatingSystem;
    private String country;
    private String city;
    private LocalDateTime createdAt;
}
