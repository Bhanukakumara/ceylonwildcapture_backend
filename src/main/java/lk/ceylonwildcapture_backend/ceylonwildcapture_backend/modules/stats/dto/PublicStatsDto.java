package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Public Stats DTO
 * Contains public platform statistics
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicStatsDto {
    private Long totalPhotos;
    private Long totalPhotographers;
    private Long totalCategories;
}
