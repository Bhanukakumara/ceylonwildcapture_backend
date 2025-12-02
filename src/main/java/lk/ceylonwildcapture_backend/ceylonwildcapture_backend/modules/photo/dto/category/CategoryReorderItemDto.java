package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryReorderItemDto {
    @NotNull(message = "Category id is required")
    private Long id;

    @NotNull(message = "Display order is required")
    @Min(value = 0, message = "Display order must be a non-negative number")
    private Integer displayOrder;
}
