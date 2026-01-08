package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.photo;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for updating an existing photo.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoUpdateDto {

    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Base price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Base price must have at most 10 integer digits and 2 decimal places")
    private BigDecimal basePrice;


    @Size(max = 200, message = "Location must not exceed 200 characters")
    private String location;

    private List<Long> tagIds;

    private List<Long> categoryIds;

    private Boolean isActive;

    private Boolean isFeatured;

    // EXIF Data fields
    @Size(max = 200, message = "Camera model must not exceed 200 characters")
    private String cameraModel;

    @Size(max = 200, message = "Lens must not exceed 200 characters")
    private String lens;

    @Size(max = 50, message = "Focal length must not exceed 50 characters")
    private String focalLength;

    @Size(max = 50, message = "Aperture must not exceed 50 characters")
    private String aperture;

    @Size(max = 50, message = "Shutter speed must not exceed 50 characters")
    private String shutterSpeed;

    @Size(max = 50, message = "ISO must not exceed 50 characters")
    private String iso;

    private String captureDate;
}
