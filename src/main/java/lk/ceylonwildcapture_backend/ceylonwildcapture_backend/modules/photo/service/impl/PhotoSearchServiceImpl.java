package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.search.PhotoFilterDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.search.PhotoOrientation;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.search.PhotoPopularityMetric;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.search.PhotoPriceTier;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.search.PhotoSearchCriteria;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.search.QuickSearchDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.photo.PhotoResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Photo;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.repository.PhotoRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service.PhotoSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of PhotoSearchService interface.
 * Provides advanced photo search operations with complex filtering,
 * sorting, and pagination capabilities.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PhotoSearchServiceImpl implements PhotoSearchService {

    private final PhotoRepository photoRepository;
    private final EntityManager entityManager;

    // Field name constants
    private static final String FIELD_IS_APPROVED = "isApproved";
    private static final String FIELD_IS_ACTIVE = "isActive";
    private static final String FIELD_IS_FEATURED = "isFeatured";
    private static final String FIELD_PHOTOGRAPHER = "photographer";
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_TAGS = "tags";
    private static final String FIELD_CATEGORIES = "categories";
    private static final String FIELD_SLUG = "slug";
    private static final String FIELD_BASE_PRICE = "basePrice";
    private static final String FIELD_LOCATION = "location";
    private static final String FIELD_CAMERA_MODEL = "cameraModel";
    private static final String FIELD_LENS = "lens";
    private static final String FIELD_WIDTH = "width";
    private static final String FIELD_HEIGHT = "height";
    private static final String FIELD_CREATED_AT = "createdAt";
    private static final String FIELD_CAPTURE_DATE = "captureDate";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_VIEW_COUNT = "viewCount";
    private static final String FIELD_DOWNLOAD_COUNT = "downloadCount";
    private static final String FIELD_LIKE_COUNT = "likeCount";

    @Override
    public Page<PhotoResponseDto> searchWithFilters(PhotoFilterDto filters, Pageable pageable) {
        log.debug("Searching photos with filters: {}", filters);

        Page<Photo> photos = photoRepository.advancedSearch(
                filters.getSearchTerm(),
                filters.getCategoryIds(),
                filters.getTagNames() != null ? filters.getTagNames().stream().map(String::toLowerCase).toList() : null,
                filters.getMinPrice(),
                filters.getMaxPrice(),
                filters.getLocation(),
                filters.getPhotographerId(),
                filters.getIsApproved() != null && filters.getIsApproved(),
                filters.getIsActive() != null && filters.getIsActive(),
                pageable
        );

        return photos.map(PhotoResponseDto::fromEntity);
    }

    @Override
    public Page<PhotoResponseDto> searchByAllTags(List<String> tagNames, Pageable pageable) {
        log.debug("Searching photos by all tags: {}", tagNames);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Photo> query = cb.createQuery(Photo.class);
        Root<Photo> photo = query.from(Photo.class);

        List<Predicate> predicates = new ArrayList<>();
        
        // Approved and active photos
        predicates.add(cb.isTrue(photo.get(FIELD_IS_APPROVED)));
        predicates.add(cb.isTrue(photo.get(FIELD_IS_ACTIVE)));

        // Must have all tags
        for (String tagName : tagNames) {
            Subquery<Long> tagSubquery = query.subquery(Long.class);
            Root<Photo> subPhoto = tagSubquery.from(Photo.class);
            Join<Object, Object> tags = subPhoto.join("tags");
            tagSubquery.select(subPhoto.get("id"))
                    .where(cb.and(
                            cb.equal(subPhoto.get("id"), photo.get("id")),
                            cb.equal(cb.lower(tags.get("name")), tagName.toLowerCase())
                    ));
            predicates.add(cb.exists(tagSubquery));
        }

        query.where(predicates.toArray(new Predicate[0]));
        query.distinct(true);

        List<Photo> results = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = countByAllTags(tagNames);
        
        return org.springframework.data.support.PageableExecutionUtils.getPage(
                results.stream().map(PhotoResponseDto::fromEntity).toList(),
                pageable,
                () -> total
        );
    }

    @Override
    public Page<PhotoResponseDto> searchByAnyTags(List<String> tagNames, Pageable pageable) {
        log.debug("Searching photos by any tags: {}", tagNames);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Photo> query = cb.createQuery(Photo.class);
        Root<Photo> photo = query.from(Photo.class);
        Join<Object, Object> tags = photo.join("tags");

        List<Predicate> tagPredicates = new ArrayList<>();
        for (String tagName : tagNames) {
            tagPredicates.add(cb.equal(cb.lower(tags.get("name")), tagName.toLowerCase()));
        }

        query.where(
                cb.isTrue(photo.get("isApproved")),
                cb.isTrue(photo.get("isActive")),
                cb.or(tagPredicates.toArray(new Predicate[0]))
        );
        query.distinct(true);

        List<Photo> results = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = countByAnyTags(tagNames);

        return org.springframework.data.support.PageableExecutionUtils.getPage(
                results.stream().map(PhotoResponseDto::fromEntity).toList(),
                pageable,
                () -> total
        );
    }

    @Override
    public Page<PhotoResponseDto> searchByCategories(List<String> categorySlugs, Pageable pageable) {
        log.debug("Searching photos by categories: {}", categorySlugs);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Photo> query = cb.createQuery(Photo.class);
        Root<Photo> photo = query.from(Photo.class);
        Join<Object, Object> categories = photo.join(FIELD_CATEGORIES);

        query.where(
                cb.isTrue(photo.get(FIELD_IS_APPROVED)),
                cb.isTrue(photo.get(FIELD_IS_ACTIVE)),
                categories.get(FIELD_SLUG).in(categorySlugs)
        );
        query.distinct(true);

        List<Photo> results = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = countByCategories(categorySlugs);

        return org.springframework.data.support.PageableExecutionUtils.getPage(
                results.stream().map(PhotoResponseDto::fromEntity).toList(),
                pageable,
                () -> total
        );
    }

    @Override
    public Page<PhotoResponseDto> searchByPhotographerAndTags(Long photographerId, List<String> tagNames, Pageable pageable) {
        log.debug("Searching photos by photographer {} and tags {}", photographerId, tagNames);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Photo> query = cb.createQuery(Photo.class);
        Root<Photo> photo = query.from(Photo.class);
        Join<Object, Object> tags = photo.join(FIELD_TAGS);

        List<Predicate> tagPredicates = new ArrayList<>();
        for (String tagName : tagNames) {
            tagPredicates.add(cb.equal(cb.lower(tags.get(FIELD_NAME)), tagName.toLowerCase()));
        }

        query.where(
                cb.isTrue(photo.get(FIELD_IS_APPROVED)),
                cb.isTrue(photo.get(FIELD_IS_ACTIVE)),
                cb.equal(photo.get(FIELD_PHOTOGRAPHER).get(FIELD_ID), photographerId),
                cb.or(tagPredicates.toArray(new Predicate[0]))
        );
        query.distinct(true);

        List<Photo> results = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = countByPhotographerAndTags(photographerId, tagNames);

        return org.springframework.data.support.PageableExecutionUtils.getPage(
                results.stream().map(PhotoResponseDto::fromEntity).toList(),
                pageable,
                () -> total
        );
    }

    @Override
    public Page<PhotoResponseDto> searchByLocationAndPriceRange(
            String location,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable) {
        
        log.debug("Searching photos by location {} and price range {}-{}", location, minPrice, maxPrice);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Photo> query = cb.createQuery(Photo.class);
        Root<Photo> photo = query.from(Photo.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isTrue(photo.get("isApproved")));
        predicates.add(cb.isTrue(photo.get("isActive")));
        
        if (location != null && !location.isBlank()) {
            predicates.add(cb.like(cb.lower(photo.get("location")), "%" + location.toLowerCase() + "%"));
        }
        if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(photo.get("basePrice"), minPrice));
        }
        if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(photo.get("basePrice"), maxPrice));
        }

        query.where(predicates.toArray(new Predicate[0]));

        List<Photo> results = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = countByLocationAndPriceRange(location, minPrice, maxPrice);

        return org.springframework.data.support.PageableExecutionUtils.getPage(
                results.stream().map(PhotoResponseDto::fromEntity).toList(),
                pageable,
                () -> total
        );
    }

    @Override
    public Page<PhotoResponseDto> searchByCaptureDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        log.debug("Searching photos by capture date range: {} to {}", startDate, endDate);

        Page<Photo> photos = photoRepository.findByCaptureDateBetween(startDate, endDate, pageable);
        return photos.map(PhotoResponseDto::fromEntity);
    }

    @Override
    public Page<PhotoResponseDto> searchByCameraModel(String cameraModel, Pageable pageable) {
        log.debug("Searching photos by camera model: {}", cameraModel);

        Page<Photo> photos = photoRepository.findByCameraModelContainingIgnoreCase(cameraModel, pageable);
        return photos.map(PhotoResponseDto::fromEntity);
    }

    @Override
    public Page<PhotoResponseDto> getPopularPhotos(String metric, Pageable pageable) {
        log.debug("Getting popular photos by metric: {}", metric);

        PhotoPopularityMetric popularityMetric;
        try {
            popularityMetric = PhotoPopularityMetric.valueOf(metric.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid popularity metric: {}, defaulting to VIEWS", metric);
            popularityMetric = PhotoPopularityMetric.VIEWS;
        }

        Page<Photo> photos = switch (popularityMetric) {
            case DOWNLOADS -> photoRepository.findAllByOrderByDownloadCountDesc(pageable);
            case LIKES -> photoRepository.findAllByOrderByLikeCountDesc(pageable);
            default -> photoRepository.findAllByOrderByViewCountDesc(pageable);
        };

        return photos.map(PhotoResponseDto::fromEntity);
    }

    @Override
    public Page<PhotoResponseDto> getTrendingPhotos(int days, Pageable pageable) {
        log.debug("Getting trending photos from last {} days", days);

        LocalDateTime since = LocalDateTime.now().minusDays(days);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Photo> query = cb.createQuery(Photo.class);
        Root<Photo> photo = query.from(Photo.class);

        query.where(
                cb.isTrue(photo.get(FIELD_IS_APPROVED)),
                cb.isTrue(photo.get(FIELD_IS_ACTIVE)),
                cb.greaterThanOrEqualTo(photo.get(FIELD_CREATED_AT), since)
        );
        
        // Order by combined popularity score
        Expression<Number> popularityScore = cb.sum(
                cb.sum(photo.get(FIELD_VIEW_COUNT), cb.prod(photo.get(FIELD_DOWNLOAD_COUNT), 5)),
                cb.prod(photo.get(FIELD_LIKE_COUNT), 3)
        );
        query.orderBy(cb.desc(popularityScore));

        List<Photo> results = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = countTrendingPhotos(days);

        return org.springframework.data.support.PageableExecutionUtils.getPage(
                results.stream().map(PhotoResponseDto::fromEntity).toList(),
                pageable,
                () -> total
        );
    }

    @Override
    public Page<PhotoResponseDto> getRecommendedPhotos(Long userId, Pageable pageable) {
        log.debug("Getting recommended photos for user {}", userId);

        // Placeholder implementation - can be enhanced with ML/AI recommendations
        // For now, return most viewed photos
        Page<Photo> photos = photoRepository.findAllByOrderByViewCountDesc(pageable);
        return photos.map(PhotoResponseDto::fromEntity);
    }

    @Override
    public Page<PhotoResponseDto> getPhotosByPriceTier(String priceTier, Pageable pageable) {
        log.debug("Getting photos by price tier: {}", priceTier);

        PhotoPriceTier tier;
        try {
            tier = PhotoPriceTier.valueOf(priceTier.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid price tier: {}, defaulting to STANDARD", priceTier);
            tier = PhotoPriceTier.STANDARD;
        }

        BigDecimal minPrice;
        BigDecimal maxPrice;

        switch (tier) {
            case BUDGET -> {
                minPrice = BigDecimal.ZERO;
                maxPrice = new BigDecimal("25");
            }
            case STANDARD -> {
                minPrice = new BigDecimal("26");
                maxPrice = new BigDecimal("75");
            }
            case PREMIUM -> {
                minPrice = new BigDecimal("76");
                maxPrice = new BigDecimal("150");
            }
            case LUXURY -> {
                minPrice = new BigDecimal("151");
                maxPrice = new BigDecimal("999999");
            }
            default -> {
                minPrice = BigDecimal.ZERO;
                maxPrice = new BigDecimal("999999");
            }
        }

        Page<Photo> photos = photoRepository.findByPriceRange(minPrice, maxPrice, pageable);
        return photos.map(PhotoResponseDto::fromEntity);
    }

    @Override
    public Page<PhotoResponseDto> fullTextSearch(String query, Pageable pageable) {
        log.debug("Performing full-text search: {}", query);

        Page<Photo> photos = photoRepository.searchByTitleOrDescription(query, pageable);
        return photos.map(PhotoResponseDto::fromEntity);
    }

    @Override
    public Page<PhotoResponseDto> filterApprovedPhotos(PhotoSearchCriteria searchCriteria, Pageable pageable) {
        log.debug("Filtering approved photos with criteria");

        // Ensure we only get approved and active photos
        PhotoSearchCriteria approvedCriteria = PhotoSearchCriteria.builder()
                .keyword(searchCriteria.getKeyword())
                .categoryIds(searchCriteria.getCategoryIds())
                .categorySlugs(searchCriteria.getCategorySlugs())
                .tagNames(searchCriteria.getTagNames())
                .tagIds(searchCriteria.getTagIds())
                .photographerId(searchCriteria.getPhotographerId())
                .minPrice(searchCriteria.getMinPrice())
                .maxPrice(searchCriteria.getMaxPrice())
                .location(searchCriteria.getLocation())
                .cameraModel(searchCriteria.getCameraModel())
                .uploadStartDate(searchCriteria.getUploadStartDate())
                .uploadEndDate(searchCriteria.getUploadEndDate())
                .captureStartDate(searchCriteria.getCaptureStartDate())
                .captureEndDate(searchCriteria.getCaptureEndDate())
                .isApproved(true)
                .isActive(true)
                .isFeatured(searchCriteria.getIsFeatured())
                .build();

        return searchWithCriteria(approvedCriteria, pageable);
    }

    @Override
    public Page<PhotoResponseDto> getRecentPhotosByPhotographer(Long photographerId, int days, Pageable pageable) {
        log.debug("Getting recent photos by photographer {} from last {} days", photographerId, days);

        LocalDateTime since = LocalDateTime.now().minusDays(days);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Photo> query = cb.createQuery(Photo.class);
        Root<Photo> photo = query.from(Photo.class);

        query.where(
                cb.equal(photo.get(FIELD_PHOTOGRAPHER).get(FIELD_ID), photographerId),
                cb.greaterThanOrEqualTo(photo.get(FIELD_CREATED_AT), since)
        );
        query.orderBy(cb.desc(photo.get(FIELD_CREATED_AT)));

        List<Photo> results = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = countRecentPhotosByPhotographer(photographerId, days);

        return org.springframework.data.support.PageableExecutionUtils.getPage(
                results.stream().map(PhotoResponseDto::fromEntity).toList(),
                pageable,
                () -> total
        );
    }

    @Override
    public Page<PhotoResponseDto> searchWithCriteria(PhotoSearchCriteria criteria, Pageable pageable) {
        log.debug("Searching photos with comprehensive criteria");

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Photo> query = cb.createQuery(Photo.class);
        Root<Photo> photo = query.from(Photo.class);

        List<Predicate> predicates = buildPredicates(cb, photo, criteria);
        query.where(predicates.toArray(new Predicate[0]));
        query.distinct(true);

        // Apply sorting
        if (criteria.getSortBy() != null && !criteria.getSortBy().isBlank()) {
            Order order = "DESC".equalsIgnoreCase(criteria.getSortDirection())
                    ? cb.desc(photo.get(criteria.getSortBy()))
                    : cb.asc(photo.get(criteria.getSortBy()));
            query.orderBy(order);
        }

        List<Photo> results = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = countWithCriteria(criteria);

        return org.springframework.data.support.PageableExecutionUtils.getPage(
                results.stream().map(PhotoResponseDto::fromEntity).toList(),
                pageable,
                () -> total
        );
    }

    @Override
    public Page<PhotoResponseDto> searchByLens(String lens, Pageable pageable) {
        log.debug("Searching photos by lens: {}", lens);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Photo> query = cb.createQuery(Photo.class);
        Root<Photo> photo = query.from(Photo.class);

        query.where(
                cb.isTrue(photo.get("isApproved")),
                cb.isTrue(photo.get("isActive")),
                cb.like(cb.lower(photo.get("lens")), "%" + lens.toLowerCase() + "%")
        );

        List<Photo> results = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = countByLens(lens);

        return org.springframework.data.support.PageableExecutionUtils.getPage(
                results.stream().map(PhotoResponseDto::fromEntity).toList(),
                pageable,
                () -> total
        );
    }

    @Override
    public Page<PhotoResponseDto> searchByIsoRange(String isoRange, Pageable pageable) {
        log.debug("Searching photos by ISO range: {}", isoRange);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Photo> query = cb.createQuery(Photo.class);
        Root<Photo> photo = query.from(Photo.class);

        query.where(
                cb.isTrue(photo.get("isApproved")),
                cb.isTrue(photo.get("isActive")),
                cb.like(photo.get("iso"), "%" + isoRange + "%")
        );

        List<Photo> results = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = countByIsoRange(isoRange);

        return org.springframework.data.support.PageableExecutionUtils.getPage(
                results.stream().map(PhotoResponseDto::fromEntity).toList(),
                pageable,
                () -> total
        );
    }

    @Override
    public Page<PhotoResponseDto> searchByApertureRange(String apertureRange, Pageable pageable) {
        log.debug("Searching photos by aperture range: {}", apertureRange);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Photo> query = cb.createQuery(Photo.class);
        Root<Photo> photo = query.from(Photo.class);

        query.where(
                cb.isTrue(photo.get("isApproved")),
                cb.isTrue(photo.get("isActive")),
                cb.like(photo.get("aperture"), "%" + apertureRange + "%")
        );

        List<Photo> results = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = countByApertureRange(apertureRange);

        return org.springframework.data.support.PageableExecutionUtils.getPage(
                results.stream().map(PhotoResponseDto::fromEntity).toList(),
                pageable,
                () -> total
        );
    }

    @Override
    public Page<PhotoResponseDto> searchByOrientation(String orientation, Pageable pageable) {
        log.debug("Searching photos by orientation: {}", orientation);

        PhotoOrientation photoOrientation;
        try {
            photoOrientation = PhotoOrientation.valueOf(orientation.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid orientation: {}, returning empty result", orientation);
            return Page.empty(pageable);
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Photo> query = cb.createQuery(Photo.class);
        Root<Photo> photo = query.from(Photo.class);

        Predicate orientationPredicate = switch (photoOrientation) {
            case LANDSCAPE -> cb.greaterThan(photo.get(FIELD_WIDTH), photo.get(FIELD_HEIGHT));
            case PORTRAIT -> cb.lessThan(photo.get(FIELD_WIDTH), photo.get(FIELD_HEIGHT));
            case SQUARE -> cb.equal(photo.get(FIELD_WIDTH), photo.get(FIELD_HEIGHT));
        };

        query.where(
                cb.isTrue(photo.get(FIELD_IS_APPROVED)),
                cb.isTrue(photo.get(FIELD_IS_ACTIVE)),
                orientationPredicate
        );

        List<Photo> results = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = countByOrientation(orientation);

        return org.springframework.data.support.PageableExecutionUtils.getPage(
                results.stream().map(PhotoResponseDto::fromEntity).toList(),
                pageable,
                () -> total
        );
    }

    @Override
    public Page<PhotoResponseDto> searchByMinimumDimensions(Integer minWidth, Integer minHeight, Pageable pageable) {
        log.debug("Searching photos by minimum dimensions: {}x{}", minWidth, minHeight);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Photo> query = cb.createQuery(Photo.class);
        Root<Photo> photo = query.from(Photo.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isTrue(photo.get("isApproved")));
        predicates.add(cb.isTrue(photo.get("isActive")));
        
        if (minWidth != null) {
            predicates.add(cb.greaterThanOrEqualTo(photo.get("width"), minWidth));
        }
        if (minHeight != null) {
            predicates.add(cb.greaterThanOrEqualTo(photo.get("height"), minHeight));
        }

        query.where(predicates.toArray(new Predicate[0]));

        List<Photo> results = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = countByMinimumDimensions(minWidth, minHeight);

        return org.springframework.data.support.PageableExecutionUtils.getPage(
                results.stream().map(PhotoResponseDto::fromEntity).toList(),
                pageable,
                () -> total
        );
    }

    // Helper methods for building predicates
    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<Photo> photo, PhotoSearchCriteria criteria) {
        List<Predicate> predicates = new ArrayList<>();

        addStatusPredicates(predicates, cb, photo, criteria);
        addKeywordPredicate(predicates, cb, photo, criteria);
        addPhotographerPredicate(predicates, cb, photo, criteria);
        addPricePredicates(predicates, cb, photo, criteria);
        addLocationPredicate(predicates, cb, photo, criteria);
        addCameraPredicates(predicates, cb, photo, criteria);
        addDatePredicates(predicates, cb, photo, criteria);
        addDimensionPredicates(predicates, cb, photo, criteria);
        addOrientationPredicate(predicates, cb, photo, criteria);

        return predicates;
    }

    private void addStatusPredicates(List<Predicate> predicates, CriteriaBuilder cb, Root<Photo> photo, PhotoSearchCriteria criteria) {
        if (criteria.getIsApproved() != null) {
            predicates.add(cb.equal(photo.get(FIELD_IS_APPROVED), criteria.getIsApproved()));
        }
        if (criteria.getIsActive() != null) {
            predicates.add(cb.equal(photo.get(FIELD_IS_ACTIVE), criteria.getIsActive()));
        }
        if (criteria.getIsFeatured() != null) {
            predicates.add(cb.equal(photo.get(FIELD_IS_FEATURED), criteria.getIsFeatured()));
        }
    }

    private void addKeywordPredicate(List<Predicate> predicates, CriteriaBuilder cb, Root<Photo> photo, PhotoSearchCriteria criteria) {
        if (criteria.getKeyword() != null && !criteria.getKeyword().isBlank()) {
            String keyword = "%" + criteria.getKeyword().toLowerCase() + "%";
            predicates.add(cb.or(
                    cb.like(cb.lower(photo.get(FIELD_TITLE)), keyword),
                    cb.like(cb.lower(photo.get(FIELD_DESCRIPTION)), keyword)
            ));
        }
    }

    private void addPhotographerPredicate(List<Predicate> predicates, CriteriaBuilder cb, Root<Photo> photo, PhotoSearchCriteria criteria) {
        if (criteria.getPhotographerId() != null) {
            predicates.add(cb.equal(photo.get(FIELD_PHOTOGRAPHER).get(FIELD_ID), criteria.getPhotographerId()));
        }
    }

    private void addPricePredicates(List<Predicate> predicates, CriteriaBuilder cb, Root<Photo> photo, PhotoSearchCriteria criteria) {
        if (criteria.getMinPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(photo.get(FIELD_BASE_PRICE), criteria.getMinPrice()));
        }
        if (criteria.getMaxPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(photo.get(FIELD_BASE_PRICE), criteria.getMaxPrice()));
        }
    }

    private void addLocationPredicate(List<Predicate> predicates, CriteriaBuilder cb, Root<Photo> photo, PhotoSearchCriteria criteria) {
        if (criteria.getLocation() != null && !criteria.getLocation().isBlank()) {
            predicates.add(cb.like(cb.lower(photo.get(FIELD_LOCATION)), 
                    "%" + criteria.getLocation().toLowerCase() + "%"));
        }
    }

    private void addCameraPredicates(List<Predicate> predicates, CriteriaBuilder cb, Root<Photo> photo, PhotoSearchCriteria criteria) {
        if (criteria.getCameraModel() != null && !criteria.getCameraModel().isBlank()) {
            predicates.add(cb.like(cb.lower(photo.get(FIELD_CAMERA_MODEL)), 
                    "%" + criteria.getCameraModel().toLowerCase() + "%"));
        }
        if (criteria.getLens() != null && !criteria.getLens().isBlank()) {
            predicates.add(cb.like(cb.lower(photo.get(FIELD_LENS)), 
                    "%" + criteria.getLens().toLowerCase() + "%"));
        }
    }

    private void addDatePredicates(List<Predicate> predicates, CriteriaBuilder cb, Root<Photo> photo, PhotoSearchCriteria criteria) {
        if (criteria.getUploadStartDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(photo.get(FIELD_CREATED_AT), criteria.getUploadStartDate()));
        }
        if (criteria.getUploadEndDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(photo.get(FIELD_CREATED_AT), criteria.getUploadEndDate()));
        }
        if (criteria.getCaptureStartDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(photo.get(FIELD_CAPTURE_DATE), criteria.getCaptureStartDate()));
        }
        if (criteria.getCaptureEndDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(photo.get(FIELD_CAPTURE_DATE), criteria.getCaptureEndDate()));
        }
    }

    private void addDimensionPredicates(List<Predicate> predicates, CriteriaBuilder cb, Root<Photo> photo, PhotoSearchCriteria criteria) {
        if (criteria.getMinWidth() != null) {
            predicates.add(cb.greaterThanOrEqualTo(photo.get(FIELD_WIDTH), criteria.getMinWidth()));
        }
        if (criteria.getMinHeight() != null) {
            predicates.add(cb.greaterThanOrEqualTo(photo.get(FIELD_HEIGHT), criteria.getMinHeight()));
        }
    }

    private void addOrientationPredicate(List<Predicate> predicates, CriteriaBuilder cb, Root<Photo> photo, PhotoSearchCriteria criteria) {
        if (criteria.getOrientation() != null && !criteria.getOrientation().isBlank()) {
            try {
                PhotoOrientation orientation = PhotoOrientation.valueOf(criteria.getOrientation().toUpperCase());
                Predicate orientationPredicate = switch (orientation) {
                    case LANDSCAPE -> cb.greaterThan(photo.get(FIELD_WIDTH), photo.get(FIELD_HEIGHT));
                    case PORTRAIT -> cb.lessThan(photo.get(FIELD_WIDTH), photo.get(FIELD_HEIGHT));
                    case SQUARE -> cb.equal(photo.get(FIELD_WIDTH), photo.get(FIELD_HEIGHT));
                };
                predicates.add(orientationPredicate);
            } catch (IllegalArgumentException e) {
                log.warn("Invalid orientation filter: {}", criteria.getOrientation());
            }
        }
    }

    // Helper count methods
    private long countByAllTags(List<String> tagNames) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Photo> photo = query.from(Photo.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isTrue(photo.get(FIELD_IS_APPROVED)));
        predicates.add(cb.isTrue(photo.get(FIELD_IS_ACTIVE)));

        for (String tagName : tagNames) {
            Subquery<Long> tagSubquery = query.subquery(Long.class);
            Root<Photo> subPhoto = tagSubquery.from(Photo.class);
            Join<Object, Object> tags = subPhoto.join(FIELD_TAGS);
            tagSubquery.select(subPhoto.get(FIELD_ID))
                    .where(cb.and(
                            cb.equal(subPhoto.get(FIELD_ID), photo.get(FIELD_ID)),
                            cb.equal(cb.lower(tags.get(FIELD_NAME)), tagName.toLowerCase())
                    ));
            predicates.add(cb.exists(tagSubquery));
        }

        query.select(cb.countDistinct(photo.get("id")));
        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getSingleResult();
    }

    private long countByAnyTags(List<String> tagNames) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Photo> photo = query.from(Photo.class);
        Join<Object, Object> tags = photo.join("tags");

        List<Predicate> tagPredicates = new ArrayList<>();
        for (String tagName : tagNames) {
            tagPredicates.add(cb.equal(cb.lower(tags.get("name")), tagName.toLowerCase()));
        }

        query.select(cb.countDistinct(photo.get("id")));
        query.where(
                cb.isTrue(photo.get("isApproved")),
                cb.isTrue(photo.get("isActive")),
                cb.or(tagPredicates.toArray(new Predicate[0]))
        );

        return entityManager.createQuery(query).getSingleResult();
    }

    private long countByCategories(List<String> categorySlugs) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Photo> photo = query.from(Photo.class);
        Join<Object, Object> categories = photo.join("categories");

        query.select(cb.countDistinct(photo.get("id")));
        query.where(
                cb.isTrue(photo.get("isApproved")),
                cb.isTrue(photo.get("isActive")),
                categories.get("slug").in(categorySlugs)
        );

        return entityManager.createQuery(query).getSingleResult();
    }

    private long countByPhotographerAndTags(Long photographerId, List<String> tagNames) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Photo> photo = query.from(Photo.class);
        Join<Object, Object> tags = photo.join("tags");

        List<Predicate> tagPredicates = new ArrayList<>();
        for (String tagName : tagNames) {
            tagPredicates.add(cb.equal(cb.lower(tags.get("name")), tagName.toLowerCase()));
        }

        query.select(cb.countDistinct(photo.get("id")));
        query.where(
                cb.isTrue(photo.get("isApproved")),
                cb.isTrue(photo.get("isActive")),
                cb.equal(photo.get("photographer").get("id"), photographerId),
                cb.or(tagPredicates.toArray(new Predicate[0]))
        );

        return entityManager.createQuery(query).getSingleResult();
    }

    private long countByLocationAndPriceRange(String location, BigDecimal minPrice, BigDecimal maxPrice) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Photo> photo = query.from(Photo.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isTrue(photo.get("isApproved")));
        predicates.add(cb.isTrue(photo.get("isActive")));
        
        if (location != null && !location.isBlank()) {
            predicates.add(cb.like(cb.lower(photo.get("location")), "%" + location.toLowerCase() + "%"));
        }
        if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(photo.get("basePrice"), minPrice));
        }
        if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(photo.get("basePrice"), maxPrice));
        }

        query.select(cb.count(photo));
        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getSingleResult();
    }

    private long countTrendingPhotos(int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Photo> photo = query.from(Photo.class);

        query.select(cb.count(photo));
        query.where(
                cb.isTrue(photo.get("isApproved")),
                cb.isTrue(photo.get("isActive")),
                cb.greaterThanOrEqualTo(photo.get("createdAt"), since)
        );

        return entityManager.createQuery(query).getSingleResult();
    }

    private long countRecentPhotosByPhotographer(Long photographerId, int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Photo> photo = query.from(Photo.class);

        query.select(cb.count(photo));
        query.where(
                cb.equal(photo.get("photographer").get("id"), photographerId),
                cb.greaterThanOrEqualTo(photo.get("createdAt"), since)
        );

        return entityManager.createQuery(query).getSingleResult();
    }

    private long countWithCriteria(PhotoSearchCriteria criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Photo> photo = query.from(Photo.class);

        List<Predicate> predicates = buildPredicates(cb, photo, criteria);
        query.select(cb.countDistinct(photo.get("id")));
        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getSingleResult();
    }

    private long countByLens(String lens) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Photo> photo = query.from(Photo.class);

        query.select(cb.count(photo));
        query.where(
                cb.isTrue(photo.get("isApproved")),
                cb.isTrue(photo.get("isActive")),
                cb.like(cb.lower(photo.get("lens")), "%" + lens.toLowerCase() + "%")
        );

        return entityManager.createQuery(query).getSingleResult();
    }

    private long countByIsoRange(String isoRange) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Photo> photo = query.from(Photo.class);

        query.select(cb.count(photo));
        query.where(
                cb.isTrue(photo.get("isApproved")),
                cb.isTrue(photo.get("isActive")),
                cb.like(photo.get("iso"), "%" + isoRange + "%")
        );

        return entityManager.createQuery(query).getSingleResult();
    }

    private long countByApertureRange(String apertureRange) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Photo> photo = query.from(Photo.class);

        query.select(cb.count(photo));
        query.where(
                cb.isTrue(photo.get("isApproved")),
                cb.isTrue(photo.get("isActive")),
                cb.like(photo.get("aperture"), "%" + apertureRange + "%")
        );

        return entityManager.createQuery(query).getSingleResult();
    }

    private long countByOrientation(String orientation) {
        PhotoOrientation photoOrientation;
        try {
            photoOrientation = PhotoOrientation.valueOf(orientation.toUpperCase());
        } catch (IllegalArgumentException e) {
            return 0;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Photo> photo = query.from(Photo.class);

        Predicate orientationPredicate = switch (photoOrientation) {
            case LANDSCAPE -> cb.greaterThan(photo.get("width"), photo.get("height"));
            case PORTRAIT -> cb.lessThan(photo.get("width"), photo.get("height"));
            case SQUARE -> cb.equal(photo.get("width"), photo.get("height"));
        };

        query.select(cb.count(photo));
        query.where(
                cb.isTrue(photo.get("isApproved")),
                cb.isTrue(photo.get("isActive")),
                orientationPredicate
        );

        return entityManager.createQuery(query).getSingleResult();
    }

    private long countByMinimumDimensions(Integer minWidth, Integer minHeight) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Photo> photo = query.from(Photo.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isTrue(photo.get("isApproved")));
        predicates.add(cb.isTrue(photo.get("isActive")));
        
        if (minWidth != null) {
            predicates.add(cb.greaterThanOrEqualTo(photo.get("width"), minWidth));
        }
        if (minHeight != null) {
            predicates.add(cb.greaterThanOrEqualTo(photo.get("height"), minHeight));
        }

        query.select(cb.count(photo));
        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public Page<PhotoResponseDto> quickSearch(QuickSearchDto quickSearch, Pageable pageable) {
        log.debug("Performing quick search with criteria: {}", quickSearch);
        return searchWithCriteria(quickSearch.toSearchCriteria(), pageable);
    }
}
