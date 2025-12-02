package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.photo;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for updating photo EXIF data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoExifUpdateDto {

    @Size(max = 100, message = "Camera model must not exceed 100 characters")
    private String cameraModel;

    @Size(max = 100, message = "Lens must not exceed 100 characters")
    private String lens;

    @Size(max = 20, message = "Focal length must not exceed 20 characters")
    private String focalLength;

    @Size(max = 20, message = "Aperture must not exceed 20 characters")
    private String aperture;

    @Size(max = 20, message = "Shutter speed must not exceed 20 characters")
    private String shutterSpeed;

    @Size(max = 20, message = "ISO must not exceed 20 characters")
    private String iso;

    private String captureDate; // Will be parsed to LocalDateTime
}
