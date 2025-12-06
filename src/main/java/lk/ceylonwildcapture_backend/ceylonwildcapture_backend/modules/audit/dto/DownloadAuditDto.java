package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for DownloadAudit entity.
 * Used for transferring download audit information in API responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownloadAuditDto {

    private Long id;

    private Long userId;

    private Long licenseId;

    private Long photoId;

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
