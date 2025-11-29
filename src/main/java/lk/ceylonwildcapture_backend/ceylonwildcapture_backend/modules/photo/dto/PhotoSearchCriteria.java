package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO class for photo search criteria.
 * Used for advanced photo search with multiple filters and parameters.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoSearchCriteria {

    /**
     * Search keyword for title and description.
     */
    private String keyword;

    /**
     * List of category IDs to filter by.
     */
    private List<Long> categoryIds;

    /**
     * List of category slugs to filter by.
     */
    private List<String> categorySlugs;

    /**
     * List of tag names to filter by.
     */
    private List<String> tagNames;

    /**
     * List of tag IDs to filter by.
     */
    private List<Long> tagIds;

    /**
     * Photographer ID to filter by.
     */
    private Long photographerId;

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
     * Camera model filter.
     */
    private String cameraModel;

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
     * Minimum view count filter.
     */
    private Integer minViewCount;

    /**
     * Minimum download count filter.
     */
    private Integer minDownloadCount;

    /**
     * Minimum like count filter.
     */
    private Integer minLikeCount;

    /**
     * Image format filter (jpg, png, etc.).
     */
    private String format;

    /**
     * Minimum width filter.
     */
    private Integer minWidth;

    /**
     * Minimum height filter.
     */
    private Integer minHeight;

    /**
     * Orientation filter (LANDSCAPE, PORTRAIT, SQUARE).
     */
    private String orientation;

    /**
     * Price tier filter (BUDGET, STANDARD, PREMIUM, LUXURY).
     */
    private String priceTier;

    /**
     * Sort field.
     */
    private String sortBy;

    /**
     * Sort direction (ASC, DESC).
     */
    private String sortDirection;

    /**
     * Whether to include only photos with watermarks.
     */
    private Boolean hasWatermark;

    /**
     * Whether to include only photos with thumbnails.
     */
    private Boolean hasThumbnail;

    /**
     * ISO range filter.
     */
    private String isoRange;

    /**
     * Aperture range filter.
     */
    private String apertureRange;

    /**
     * Lens filter.
     */
    private String lens;

    /**
     * Include photographer details in results.
     */
    @Builder.Default
    private Boolean includePhotographer = false;

    /**
     * Include tags in results.
     */
    @Builder.Default
    private Boolean includeTags = false;

    /**
     * Include categories in results.
     */
    @Builder.Default
    private Boolean includeCategories = false;
}
