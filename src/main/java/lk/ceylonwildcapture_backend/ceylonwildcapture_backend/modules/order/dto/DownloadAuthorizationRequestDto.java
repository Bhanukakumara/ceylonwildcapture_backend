package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for download authorization request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownloadAuthorizationRequestDto {

    @NotBlank(message = "License key is required")
    private String licenseKey;

    @NotNull(message = "Photo ID is required")
    private Long photoId;
}
