package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.search.PhotoFilterDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.search.PhotoSearchCriteria;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.search.QuickSearchDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.photo.PhotoResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service.PhotoSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Controller for advanced photo search operations.
 * Provides endpoints for complex photo searches with multiple filters,
 * sorting options, and specialized search criteria.
 */
@RestController
@RequestMapping("/api/v1/photos/search")
@RequiredArgsConstructor
@Slf4j
public class PhotoSearchController {

    private final PhotoSearchService photoSearchService;

    // ------------------------------
    // Search with Multiple Filters
    // ------------------------------
    @PostMapping("/filters")
    public ResponseEntity<Page<PhotoResponseDto>> searchWithFilters(
            @Valid @RequestBody PhotoFilterDto filters,
            Pageable pageable) {
        log.info("Searching photos with filters: {}", filters);
        return ResponseEntity.ok(photoSearchService.searchWithFilters(filters, pageable));
    }

    // ------------------------------
    // Search by All Tags (AND)
    // ------------------------------
    @GetMapping("/tags/all")
    public ResponseEntity<Page<PhotoResponseDto>> searchByAllTags(
            @RequestParam("tags") List<String> tagNames,
            Pageable pageable) {
        log.info("Searching photos by all tags: {}", tagNames);
        return ResponseEntity.ok(photoSearchService.searchByAllTags(tagNames, pageable));
    }

    // ------------------------------
    // Search by Any Tags (OR)
    // ------------------------------
    @GetMapping("/tags/any")
    public ResponseEntity<Page<PhotoResponseDto>> searchByAnyTags(
            @RequestParam("tags") List<String> tagNames,
            Pageable pageable) {
        log.info("Searching photos by any tags: {}", tagNames);
        return ResponseEntity.ok(photoSearchService.searchByAnyTags(tagNames, pageable));
    }

    // ------------------------------
    // Search by Multiple Categories
    // ------------------------------
    @GetMapping("/categories")
    public ResponseEntity<Page<PhotoResponseDto>> searchByCategories(
            @RequestParam("slugs") List<String> categorySlugs,
            Pageable pageable) {
        log.info("Searching photos by categories: {}", categorySlugs);
        return ResponseEntity.ok(photoSearchService.searchByCategories(categorySlugs, pageable));
    }

    // ------------------------------
    // Search by Photographer and Tags
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/tags")
    public ResponseEntity<Page<PhotoResponseDto>> searchByPhotographerAndTags(
            @PathVariable("photographerId") Long photographerId,
            @RequestParam("tags") List<String> tagNames,
            Pageable pageable) {
        log.info("Searching photos by photographer {} and tags: {}", photographerId, tagNames);
        return ResponseEntity.ok(photoSearchService.searchByPhotographerAndTags(photographerId, tagNames, pageable));
    }

    // ------------------------------
    // Search by Location and Price Range
    // ------------------------------
    @GetMapping("/location-price")
    public ResponseEntity<Page<PhotoResponseDto>> searchByLocationAndPriceRange(
            @RequestParam("location") String location,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            Pageable pageable) {
        log.info("Searching photos by location {} and price range {}-{}", location, minPrice, maxPrice);
        return ResponseEntity.ok(photoSearchService.searchByLocationAndPriceRange(location, minPrice, maxPrice, pageable));
    }

    // ------------------------------
    // Search by Capture Date Range
    // ------------------------------
    @GetMapping("/capture-date-range")
    public ResponseEntity<Page<PhotoResponseDto>> searchByCaptureDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable) {
        log.info("Searching photos by capture date range: {} to {}", startDate, endDate);
        return ResponseEntity.ok(photoSearchService.searchByCaptureDateRange(startDate, endDate, pageable));
    }

    // ------------------------------
    // Search by Camera Model
    // ------------------------------
    @GetMapping("/camera")
    public ResponseEntity<Page<PhotoResponseDto>> searchByCameraModel(
            @RequestParam("model") String cameraModel,
            Pageable pageable) {
        log.info("Searching photos by camera model: {}", cameraModel);
        return ResponseEntity.ok(photoSearchService.searchByCameraModel(cameraModel, pageable));
    }

    // ------------------------------
    // Search by Lens
    // ------------------------------
    @GetMapping("/lens")
    public ResponseEntity<Page<PhotoResponseDto>> searchByLens(
            @RequestParam("lens") String lens,
            Pageable pageable) {
        log.info("Searching photos by lens: {}", lens);
        return ResponseEntity.ok(photoSearchService.searchByLens(lens, pageable));
    }

    // ------------------------------
    // Search by ISO Range
    // ------------------------------
    @GetMapping("/iso")
    public ResponseEntity<Page<PhotoResponseDto>> searchByIsoRange(
            @RequestParam("range") String isoRange,
            Pageable pageable) {
        log.info("Searching photos by ISO range: {}", isoRange);
        return ResponseEntity.ok(photoSearchService.searchByIsoRange(isoRange, pageable));
    }

    // ------------------------------
    // Search by Aperture Range
    // ------------------------------
    @GetMapping("/aperture")
    public ResponseEntity<Page<PhotoResponseDto>> searchByApertureRange(
            @RequestParam("range") String apertureRange,
            Pageable pageable) {
        log.info("Searching photos by aperture range: {}", apertureRange);
        return ResponseEntity.ok(photoSearchService.searchByApertureRange(apertureRange, pageable));
    }

    // ------------------------------
    // Search by Orientation
    // ------------------------------
    @GetMapping("/orientation/{orientation}")
    public ResponseEntity<Page<PhotoResponseDto>> searchByOrientation(
            @PathVariable("orientation") String orientation,
            Pageable pageable) {
        log.info("Searching photos by orientation: {}", orientation);
        return ResponseEntity.ok(photoSearchService.searchByOrientation(orientation, pageable));
    }

    // ------------------------------
    // Search by Minimum Dimensions
    // ------------------------------
    @GetMapping("/dimensions")
    public ResponseEntity<Page<PhotoResponseDto>> searchByMinimumDimensions(
            @RequestParam(value = "minWidth", required = false) Integer minWidth,
            @RequestParam(value = "minHeight", required = false) Integer minHeight,
            Pageable pageable) {
        log.info("Searching photos by minimum dimensions: {}x{}", minWidth, minHeight);
        return ResponseEntity.ok(photoSearchService.searchByMinimumDimensions(minWidth, minHeight, pageable));
    }

    // ------------------------------
    // Get Popular Photos
    // ------------------------------
    @GetMapping("/popular/{metric}")
    public ResponseEntity<Page<PhotoResponseDto>> getPopularPhotos(
            @PathVariable("metric") String metric,
            Pageable pageable) {
        log.info("Getting popular photos by metric: {}", metric);
        return ResponseEntity.ok(photoSearchService.getPopularPhotos(metric, pageable));
    }

    // ------------------------------
    // Get Trending Photos
    // ------------------------------
    @GetMapping("/trending")
    public ResponseEntity<Page<PhotoResponseDto>> getTrendingPhotos(
            @RequestParam(value = "days", defaultValue = "7") @Min(1) int days,
            Pageable pageable) {
        log.info("Getting trending photos from last {} days", days);
        return ResponseEntity.ok(photoSearchService.getTrendingPhotos(days, pageable));
    }

    // ------------------------------
    // Get Recommended Photos
    // ------------------------------
    @GetMapping("/recommended/{userId}")
    public ResponseEntity<Page<PhotoResponseDto>> getRecommendedPhotos(
            @PathVariable("userId") Long userId,
            Pageable pageable) {
        log.info("Getting recommended photos for user {}", userId);
        return ResponseEntity.ok(photoSearchService.getRecommendedPhotos(userId, pageable));
    }

    // ------------------------------
    // Get Photos by Price Tier
    // ------------------------------
    @GetMapping("/price-tier/{tier}")
    public ResponseEntity<Page<PhotoResponseDto>> getPhotosByPriceTier(
            @PathVariable("tier") String priceTier,
            Pageable pageable) {
        log.info("Getting photos by price tier: {}", priceTier);
        return ResponseEntity.ok(photoSearchService.getPhotosByPriceTier(priceTier, pageable));
    }

    // ------------------------------
    // Full-Text Search
    // ------------------------------
    @GetMapping("/full-text")
    public ResponseEntity<Page<PhotoResponseDto>> fullTextSearch(
            @RequestParam("query") String query,
            Pageable pageable) {
        log.info("Performing full-text search: {}", query);
        return ResponseEntity.ok(photoSearchService.fullTextSearch(query, pageable));
    }

    // ------------------------------
    // Filter Approved Photos
    // ------------------------------
    @PostMapping("/approved")
    public ResponseEntity<Page<PhotoResponseDto>> filterApprovedPhotos(
            @Valid @RequestBody PhotoSearchCriteria searchCriteria,
            Pageable pageable) {
        log.info("Filtering approved photos with criteria");
        return ResponseEntity.ok(photoSearchService.filterApprovedPhotos(searchCriteria, pageable));
    }

    // ------------------------------
    // Get Recent Photos by Photographer
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/recent")
    public ResponseEntity<Page<PhotoResponseDto>> getRecentPhotosByPhotographer(
            @PathVariable("photographerId") Long photographerId,
            @RequestParam(value = "days", defaultValue = "30") @Min(1) int days,
            Pageable pageable) {
        log.info("Getting recent photos by photographer {} from last {} days", photographerId, days);
        return ResponseEntity.ok(photoSearchService.getRecentPhotosByPhotographer(photographerId, days, pageable));
    }

    // ------------------------------
    // Comprehensive Search with Criteria
    // ------------------------------
    @PostMapping("/comprehensive")
    public ResponseEntity<Page<PhotoResponseDto>> searchWithCriteria(
            @Valid @RequestBody PhotoSearchCriteria searchCriteria,
            Pageable pageable) {
        log.info("Performing comprehensive search with criteria");
        return ResponseEntity.ok(photoSearchService.searchWithCriteria(searchCriteria, pageable));
    }

    // ------------------------------
    // Quick Search Endpoint
    // ------------------------------
    @PostMapping("/quick")
    public ResponseEntity<Page<PhotoResponseDto>> quickSearch(
            @Valid @RequestBody QuickSearchDto quickSearch,
            Pageable pageable) {
        log.info("Quick search with criteria: {}", quickSearch);
        return ResponseEntity.ok(photoSearchService.quickSearch(quickSearch, pageable));
    }
}
