package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private String name;
    private String description;
    private String slug;
    private String imageUrl;
    private Boolean isActive;
    private Integer displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CategoryResponseDto fromEntity(Category c) {
        return CategoryResponseDto.builder()
                .id(c.getId())
                .name(c.getName())
                .description(c.getDescription())
                .slug(c.getSlug())
                .imageUrl(c.getImageUrl())
                .isActive(c.getIsActive())
                .displayOrder(c.getDisplayOrder())
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .build();
    }
}
