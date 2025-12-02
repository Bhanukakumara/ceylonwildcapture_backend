package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.photo;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for updating photo metadata.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoMetadataUpdateDto {

    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @Size(max = 200, message = "Location must not exceed 200 characters")
    private String location;
}
