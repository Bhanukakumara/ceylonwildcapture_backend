package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for advanced photo search operations.
 * Defines business logic for complex photo searches with multiple criteria,
 * filters, and sorting options.
 */
public interface PhotoSearchService {

    /**
     * Advanced search with multiple criteria.
     *
     * @param searchCriteria the search criteria object (DTO placeholder)
     * @param pageable pagination information
     * @return page of matching photos
     */
    Page<Photo> advancedSearch(Object searchCriteria, Pageable pageable);

    /**
     * Search photos with filters.
     *
     * @param searchTerm the search term for title/description
     * @param categoryIds the list of category IDs
     * @param tagNames the list of tag names
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @param location the location
     * @param photographerId the photographer ID
     * @param pageable pagination information
     * @return page of matching photos
     */
    Page<Photo> searchWithFilters(
            String searchTerm,
            List<Long> categoryIds,
            List<String> tagNames,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String location,
            Long photographerId,
            Pageable pageable
    );

    /**
     * Search photos by keyword in title and description.
     *
     * @param keyword the search keyword
     * @param pageable pagination information
     * @return page of matching photos
     */
    Page<Photo> searchByKeyword(String keyword, Pageable pageable);

    /**
     * Search photos by multiple tags (AND operation).
     *
     * @param tagNames the list of tag names
     * @param pageable pagination information
     * @return page of photos containing all specified tags
     */
    Page<Photo> searchByAllTags(List<String> tagNames, Pageable pageable);

    /**
     * Search photos by any tags (OR operation).
     *
     * @param tagNames the list of tag names
     * @param pageable pagination information
     * @return page of photos containing any of the specified tags
     */
    Page<Photo> searchByAnyTags(List<String> tagNames, Pageable pageable);

    /**
     * Search photos by multiple categories.
     *
     * @param categorySlugs the list of category slugs
     * @param pageable pagination information
     * @return page of photos in any of the specified categories
     */
    Page<Photo> searchByCategories(List<String> categorySlugs, Pageable pageable);

    /**
     * Search photos by photographer and tags.
     *
     * @param photographerId the photographer ID
     * @param tagNames the list of tag names
     * @param pageable pagination information
     * @return page of matching photos
     */
    Page<Photo> searchByPhotographerAndTags(Long photographerId, List<String> tagNames, Pageable pageable);

    /**
     * Search photos by location and price range.
     *
     * @param location the location
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @param pageable pagination information
     * @return page of matching photos
     */
    Page<Photo> searchByLocationAndPriceRange(
            String location,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    );

    /**
     * Search photos by date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of photos uploaded within date range
     */
    Page<Photo> searchByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Search photos by capture date range.
     *
     * @param startDate the start capture date
     * @param endDate the end capture date
     * @param pageable pagination information
     * @return page of photos captured within date range
     */
    Page<Photo> searchByCaptureDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Search photos by camera model.
     *
     * @param cameraModel the camera model
     * @param pageable pagination information
     * @return page of photos taken with the camera
     */
    Page<Photo> searchByCameraModel(String cameraModel, Pageable pageable);

    /**
     * Get popular photos (by views, downloads, or likes).
     *
     * @param metric the popularity metric (VIEWS, DOWNLOADS, LIKES)
     * @param pageable pagination information
     * @return page of popular photos
     */
    Page<Photo> getPopularPhotos(String metric, Pageable pageable);

    /**
     * Get trending photos (recently popular).
     *
     * @param days the number of days to consider
     * @param pageable pagination information
     * @return page of trending photos
     */
    Page<Photo> getTrendingPhotos(int days, Pageable pageable);

    /**
     * Get recommended photos based on user preferences.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of recommended photos
     */
    Page<Photo> getRecommendedPhotos(Long userId, Pageable pageable);

    /**
     * Get similar photos based on tags and categories.
     *
     * @param photoId the photo ID
     * @param limit the maximum number of similar photos
     * @return list of similar photos
     */
    List<Photo> getSimilarPhotos(Long photoId, Integer limit);

    /**
     * Get photos by price tier.
     *
     * @param priceTier the price tier (BUDGET, STANDARD, PREMIUM, LUXURY)
     * @param pageable pagination information
     * @return page of photos in the price tier
     */
    Page<Photo> getPhotosByPriceTier(String priceTier, Pageable pageable);

    /**
     * Search photos with full-text search (if supported).
     *
     * @param query the full-text search query
     * @param pageable pagination information
     * @return page of matching photos
     */
    Page<Photo> fullTextSearch(String query, Pageable pageable);

    /**
     * Filter approved photos by criteria.
     *
     * @param filterCriteria the filter criteria object (DTO placeholder)
     * @param pageable pagination information
     * @return page of filtered photos
     */
    Page<Photo> filterApprovedPhotos(Object filterCriteria, Pageable pageable);

    /**
     * Get photos needing moderation.
     *
     * @param pageable pagination information
     * @return page of photos pending approval
     */
    Page<Photo> getPhotosNeedingModeration(Pageable pageable);

    /**
     * Get recently uploaded photos by photographer.
     *
     * @param photographerId the photographer ID
     * @param days the number of days to look back
     * @param pageable pagination information
     * @return page of recent photos
     */
    Page<Photo> getRecentPhotosByPhotographer(Long photographerId, int days, Pageable pageable);

    /**
     * Search photos with sorting options.
     *
     * @param searchCriteria the search criteria object (DTO placeholder)
     * @param sortBy the field to sort by
     * @param sortDirection the sort direction (ASC, DESC)
     * @param pageable pagination information
     * @return page of sorted and filtered photos
     */
    Page<Photo> searchWithSorting(
            Object searchCriteria,
            String sortBy,
            String sortDirection,
            Pageable pageable
    );
}
