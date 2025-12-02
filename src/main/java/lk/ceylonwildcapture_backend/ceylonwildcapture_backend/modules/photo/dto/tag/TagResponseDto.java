package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.tag;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for tag response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagResponseDto {

    private Long id;
    private String name;
    private String description;
    private Integer usageCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Static method to convert Tag entity to TagResponseDto.
     *
     * @param tag the tag entity
     * @return TagResponseDto
     */
    public static TagResponseDto fromEntity(Tag tag) {
        if (tag == null) {
            return null;
        }

        return TagResponseDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .description(tag.getDescription())
                .usageCount(tag.getUsageCount())
                .createdAt(tag.getCreatedAt())
                .updatedAt(tag.getUpdatedAt())
                .build();
    }
}
