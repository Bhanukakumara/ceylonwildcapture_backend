package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for category performance data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPerformanceDto {
    private Long categoryId;
    private String categoryName;
    private String categorySlug;
    private Long photoCount;
    private Long salesCount;
    private BigDecimal totalRevenue;
}
