package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateDto {
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Size(max = 100, message = "Slug must not exceed 100 characters")
    private String slug;

    @Size(max = 255, message = "Image URL must not exceed 255 characters")
    private String imageUrl;

    private Boolean isActive;

    private Integer displayOrder;
}
