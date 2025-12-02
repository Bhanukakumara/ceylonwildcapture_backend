package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for photo filtering operations.
 * Provides common filter parameters for photo queries.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoFilterDto {

    /**
     * Search term for title and description.
     */
    private String searchTerm;

    /**
     * List of category IDs to filter by.
     */
    private List<Long> categoryIds;

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
     * Filter by approval status.
     */
    private Boolean isApproved;

    /**
     * Filter by active status.
     */
    private Boolean isActive;

    /**
     * Filter by featured status.
     */
    private Boolean isFeatured;

    /**
     * Start date for upload date filter.
     */
    private LocalDateTime uploadStartDate;

    /**
     * End date for upload date filter.
     */
    private LocalDateTime uploadEndDate;

    /**
     * Start date for capture date filter.
     */
    private LocalDateTime captureStartDate;

    /**
     * End date for capture date filter.
     */
    private LocalDateTime captureEndDate;

    /**
     * Camera model filter.
     */
    private String cameraModel;

    /**
     * Lens filter.
     */
    private String lens;

    /**
     * ISO range filter.
     */
    private String isoRange;

    /**
     * Aperture range filter.
     */
    private String apertureRange;

    /**
     * Orientation filter (LANDSCAPE, PORTRAIT, SQUARE).
     */
    private String orientation;

    /**
     * Minimum width filter.
     */
    private Integer minWidth;

    /**
     * Minimum height filter.
     */
    private Integer minHeight;
}
