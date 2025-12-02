package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.search.PhotoFilterDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.search.PhotoSearchCriteria;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.search.QuickSearchDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.photo.PhotoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for advanced photo search operations.
 * Defines business logic for complex photo searches with multiple criteria,
 * filters, and sorting options. Returns DTOs instead of entities.
 * Removes duplicate methods already present in PhotoService.
 */
public interface PhotoSearchService {

    /**
     * Search photos with multiple filters.
     *
     * @param filters the photo filter DTO containing all filter criteria
     * @param pageable pagination information
     * @return page of matching photo DTOs
     */
    Page<PhotoResponseDto> searchWithFilters(PhotoFilterDto filters, Pageable pageable);

    /**
     * Search photos by multiple tags (AND operation).
     *
     * @param tagNames the list of tag names
     * @param pageable pagination information
     * @return page of photos containing all specified tags
     */
    Page<PhotoResponseDto> searchByAllTags(List<String> tagNames, Pageable pageable);

    /**
     * Search photos by any tags (OR operation).
     *
     * @param tagNames the list of tag names
     * @param pageable pagination information
     * @return page of photos containing any of the specified tags
     */
    Page<PhotoResponseDto> searchByAnyTags(List<String> tagNames, Pageable pageable);

    /**
     * Search photos by multiple categories.
     *
     * @param categorySlugs the list of category slugs
     * @param pageable pagination information
     * @return page of photos in any of the specified categories
     */
    Page<PhotoResponseDto> searchByCategories(List<String> categorySlugs, Pageable pageable);

    /**
     * Search photos by photographer and tags.
     *
     * @param photographerId the photographer ID
     * @param tagNames the list of tag names
     * @param pageable pagination information
     * @return page of matching photo DTOs
     */
    Page<PhotoResponseDto> searchByPhotographerAndTags(Long photographerId, List<String> tagNames, Pageable pageable);

    /**
     * Search photos by location and price range.
     *
     * @param location the location
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @param pageable pagination information
     * @return page of matching photo DTOs
     */
    Page<PhotoResponseDto> searchByLocationAndPriceRange(
            String location,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    );

    /**
     * Search photos by capture date range.
     *
     * @param startDate the start capture date
     * @param endDate the end capture date
     * @param pageable pagination information
     * @return page of photos captured within date range
     */
    Page<PhotoResponseDto> searchByCaptureDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Search photos by camera model.
     *
     * @param cameraModel the camera model
     * @param pageable pagination information
     * @return page of photos taken with the camera
     */
    Page<PhotoResponseDto> searchByCameraModel(String cameraModel, Pageable pageable);

    /**
     * Get popular photos (by views, downloads, or likes).
     *
     * @param metric the popularity metric (VIEWS, DOWNLOADS, LIKES)
     * @param pageable pagination information
     * @return page of popular photo DTOs
     */
    Page<PhotoResponseDto> getPopularPhotos(String metric, Pageable pageable);

    /**
     * Get trending photos (recently popular).
     *
     * @param days the number of days to consider
     * @param pageable pagination information
     * @return page of trending photo DTOs
     */
    Page<PhotoResponseDto> getTrendingPhotos(int days, Pageable pageable);

    /**
     * Get recommended photos based on user preferences.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of recommended photo DTOs
     */
    Page<PhotoResponseDto> getRecommendedPhotos(Long userId, Pageable pageable);

    /**
     * Get photos by price tier.
     *
     * @param priceTier the price tier (BUDGET, STANDARD, PREMIUM, LUXURY)
     * @param pageable pagination information
     * @return page of photos in the price tier
     */
    Page<PhotoResponseDto> getPhotosByPriceTier(String priceTier, Pageable pageable);

    /**
     * Search photos with full-text search (if supported).
     *
     * @param query the full-text search query
     * @param pageable pagination information
     * @return page of matching photo DTOs
     */
    Page<PhotoResponseDto> fullTextSearch(String query, Pageable pageable);

    /**
     * Filter approved photos by criteria.
     *
     * @param searchCriteria the filter criteria object
     * @param pageable pagination information
     * @return page of filtered photo DTOs
     */
    Page<PhotoResponseDto> filterApprovedPhotos(PhotoSearchCriteria searchCriteria, Pageable pageable);

    /**
     * Get recently uploaded photos by photographer.
     *
     * @param photographerId the photographer ID
     * @param days the number of days to look back
     * @param pageable pagination information
     * @return page of recent photo DTOs
     */
    Page<PhotoResponseDto> getRecentPhotosByPhotographer(Long photographerId, int days, Pageable pageable);

    /**
     * Search photos with comprehensive criteria and sorting.
     *
     * @param searchCriteria the search criteria object with all filters and sort options
     * @param pageable pagination information
     * @return page of sorted and filtered photo DTOs
     */
    Page<PhotoResponseDto> searchWithCriteria(PhotoSearchCriteria searchCriteria, Pageable pageable);

    /**
     * Get photos by lens type.
     *
     * @param lens the lens model/type
     * @param pageable pagination information
     * @return page of photos taken with the lens
     */
    Page<PhotoResponseDto> searchByLens(String lens, Pageable pageable);

    /**
     * Get photos by ISO range.
     *
     * @param isoRange the ISO range (e.g., "100-400", "800+")
     * @param pageable pagination information
     * @return page of photos in the ISO range
     */
    Page<PhotoResponseDto> searchByIsoRange(String isoRange, Pageable pageable);

    /**
     * Get photos by aperture range.
     *
     * @param apertureRange the aperture range (e.g., "f/1.4-f/2.8")
     * @param pageable pagination information
     * @return page of photos in the aperture range
     */
    Page<PhotoResponseDto> searchByApertureRange(String apertureRange, Pageable pageable);

    /**
     * Get photos by orientation.
     *
     * @param orientation the orientation (LANDSCAPE, PORTRAIT, SQUARE)
     * @param pageable pagination information
     * @return page of photos with the orientation
     */
    Page<PhotoResponseDto> searchByOrientation(String orientation, Pageable pageable);

    /**
     * Get photos by minimum dimensions.
     *
     * @param minWidth the minimum width
     * @param minHeight the minimum height
     * @param pageable pagination information
     * @return page of photos meeting dimension requirements
     */
    Page<PhotoResponseDto> searchByMinimumDimensions(Integer minWidth, Integer minHeight, Pageable pageable);

    /**
     * Quick search with simplified criteria.
     *
     * @param quickSearch the quick search DTO with common filters
     * @param pageable pagination information
     * @return page of matching photo DTOs
     */
    Page<PhotoResponseDto> quickSearch(QuickSearchDto quickSearch, Pageable pageable);
}
