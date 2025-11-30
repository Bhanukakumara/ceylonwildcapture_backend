package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for quick search functionality.
 * Provides a simplified interface for common search operations.
 * Used with @RequestBody for POST/request body binding.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuickSearchDto {

    /**
     * Search keyword for title and description.
     */
    private String keyword;

    /**
     * List of category slugs to filter by.
     */
    private List<String> categorySlugs;

    /**
     * List of tag names to filter by.
     */
    private List<String> tagNames;

    /**
     * Minimum price filter.
     */
    private BigDecimal minPrice;

    /**
     * Maximum price filter.
     */
    private BigDecimal maxPrice;

    /**
     * Location filter.
     */
    private String location;

    /**
     * Photographer ID to filter by.
     */
    private Long photographerId;

    /**
     * Orientation filter (LANDSCAPE, PORTRAIT, SQUARE).
     */
    private String orientation;

    /**
     * Convert this QuickSearchDto to a full PhotoSearchCriteria.
     * Sets default values for approved and active status.
     *
     * @return PhotoSearchCriteria with approved and active filters enabled
     */
    public PhotoSearchCriteria toSearchCriteria() {
        return PhotoSearchCriteria.builder()
                .keyword(this.keyword)
                .categorySlugs(this.categorySlugs)
                .tagNames(this.tagNames)
                .minPrice(this.minPrice)
                .maxPrice(this.maxPrice)
                .location(this.location)
                .photographerId(this.photographerId)
                .orientation(this.orientation)
                .isApproved(true)
                .isActive(true)
                .build();
    }
}
