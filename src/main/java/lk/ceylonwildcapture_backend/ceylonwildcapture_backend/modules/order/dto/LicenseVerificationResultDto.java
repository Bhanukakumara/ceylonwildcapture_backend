package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for license verification result.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LicenseVerificationResultDto {

    private Boolean valid;
    private String message;
    private String licenseKey;
    private Long photoId;
    private Long userId;
    private Boolean canDownload;
    private Integer remainingDownloads;
    private String reason;
}
