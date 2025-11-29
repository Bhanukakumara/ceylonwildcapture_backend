package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.tag;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for updating an existing tag.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagUpdateDto {

    @Size(min = 2, max = 50, message = "Tag name must be between 2 and 50 characters")
    private String name;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
}
