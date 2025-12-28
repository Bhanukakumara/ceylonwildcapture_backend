package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Photographer Stats DTO
 * Contains statistics for a specific photographer dashboard
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotographerStatsDto {
    private Long totalPhotos;
    private BigDecimal totalEarnings;
    private Integer totalSales;
    private BigDecimal averageRating;
}
