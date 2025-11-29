package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.LicenseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for license response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LicenseResponseDto {

    private Long id;
    private String licenseKey;
    private Long orderItemId;
    private Long orderId;
    private String orderNumber;
    private Long photoId;
    private String photoTitle;
    private String photoThumbnailUrl;
    private Long photographerId;
    private String photographerName;
    private LicenseType licenseType;
    private String issuedTo;
    private String issuedEmail;
    private Integer downloadLimit;
    private Integer downloadCount;
    private Integer remainingDownloads;
    private Boolean isActive;
    private Boolean isExpired;
    private LocalDateTime expiresAt;
    private LocalDateTime firstDownloadedAt;
    private LocalDateTime lastDownloadedAt;
    private String terms;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
