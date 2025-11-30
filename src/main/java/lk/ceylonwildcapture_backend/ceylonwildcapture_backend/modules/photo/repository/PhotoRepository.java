package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Photo;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Tag;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Photo entity.
 * Provides database operations for wildlife photo management including search,
 * filtering, and photographer-specific queries.
 */
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    /**
     * Find photo by ID with photographer loaded.
     *
     * @param id the photo ID
     * @return Optional containing the photo if found
     */
    @Query("SELECT p FROM Photo p LEFT JOIN FETCH p.photographer WHERE p.id = :id")
    Optional<Photo> findByIdWithPhotographer(@Param("id") Long id);

    /**
     * Find all photos by photographer.
     *
     * @param photographer the photographer (User entity)
     * @param pageable pagination information
     * @return page of photos
     */
    Page<Photo> findByPhotographer(User photographer, Pageable pageable);

    /**
     * Find all photos by photographer ID.
     *
     * @param photographerId the photographer ID
     * @param pageable pagination information
     * @return page of photos
     */
    Page<Photo> findByPhotographerId(Long photographerId, Pageable pageable);

    /**
     * Find approved photos by photographer.
     *
     * @param photographerId the photographer ID
     * @param isApproved the approval status
     * @param pageable pagination information
     * @return page of approved photos
     */
    Page<Photo> findByPhotographerIdAndIsApproved(Long photographerId, Boolean isApproved, Pageable pageable);

    /**
     * Find active photos by photographer.
     *
     * @param photographerId the photographer ID
     * @param isActive the active status
     * @param pageable pagination information
     * @return page of active photos
     */
    Page<Photo> findByPhotographerIdAndIsActive(Long photographerId, Boolean isActive, Pageable pageable);

    /**
     * Find photos by approval status.
     *
     * @param isApproved the approval status
     * @param pageable pagination information
     * @return page of photos
     */
    Page<Photo> findByIsApproved(Boolean isApproved, Pageable pageable);

    /**
     * Find photos by active status.
     *
     * @param isActive the active status
     * @param pageable pagination information
     * @return page of photos
     */
    Page<Photo> findByIsActive(Boolean isActive, Pageable pageable);

    /**
     * Find featured photos.
     *
     * @param isFeatured the featured status
     * @param pageable pagination information
     * @return page of featured photos
     */
    Page<Photo> findByIsFeatured(Boolean isFeatured, Pageable pageable);

    /**
     * Find approved and active photos.
     *
     * @param isApproved the approval status
     * @param isActive the active status
     * @param pageable pagination information
     * @return page of photos
     */
    Page<Photo> findByIsApprovedAndIsActive(Boolean isApproved, Boolean isActive, Pageable pageable);

    /**
     * Find photos containing tag by name.
     *
     * @param tagName the tag name
     * @param pageable pagination information
     * @return page of photos with the specified tag
     */
    @Query("SELECT p FROM Photo p JOIN p.tags t WHERE LOWER(t.name) = LOWER(:tagName)")
    Page<Photo> findByTagsName(@Param("tagName") String tagName, Pageable pageable);

    /**
     * Find photos containing any of the specified tags.
     *
     * @param tags the list of tags
     * @param pageable pagination information
     * @return page of photos
     */
    @Query("SELECT DISTINCT p FROM Photo p JOIN p.tags t WHERE t IN :tags")
    Page<Photo> findByTagsIn(@Param("tags") List<Tag> tags, Pageable pageable);

    /**
     * Find photos by category.
     *
     * @param category the category
     * @param pageable pagination information
     * @return page of photos in the category
     */
    @Query("SELECT p FROM Photo p JOIN p.categories c WHERE c = :category")
    Page<Photo> findByCategory(@Param("category") Category category, Pageable pageable);

    /**
     * Find photos by category slug.
     *
     * @param slug the category slug
     * @param pageable pagination information
     * @return page of photos in the category
     */
    @Query("SELECT p FROM Photo p JOIN p.categories c WHERE c.slug = :slug")
    Page<Photo> findByCategorySlug(@Param("slug") String slug, Pageable pageable);

    /**
     * Find photos by category ID.
     *
     * @param categoryId the category ID
     * @param pageable pagination information
     * @return page of photos in the category
     */
    @Query("SELECT p FROM Photo p JOIN p.categories c WHERE c.id = :categoryId")
    Page<Photo> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    /**
     * Search photos by title or description.
     *
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of matching photos
     */
    @Query("SELECT p FROM Photo p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Photo> searchByTitleOrDescription(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Find photos by location.
     *
     * @param location the location
     * @param pageable pagination information
     * @return page of photos from the location
     */
    Page<Photo> findByLocationContainingIgnoreCase(String location, Pageable pageable);

    /**
     * Find photos by price range.
     *
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @param pageable pagination information
     * @return page of photos within price range
     */
    @Query("SELECT p FROM Photo p WHERE p.basePrice BETWEEN :minPrice AND :maxPrice")
    Page<Photo> findByPriceRange(@Param("minPrice") BigDecimal minPrice,
                                  @Param("maxPrice") BigDecimal maxPrice,
                                  Pageable pageable);

    /**
     * Find photos uploaded within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of photos uploaded within date range
     */
    Page<Photo> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find photos captured within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of photos captured within date range
     */
    Page<Photo> findByCaptureDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find most viewed photos.
     *
     * @param pageable pagination information
     * @return page of most viewed photos
     */
    Page<Photo> findAllByOrderByViewCountDesc(Pageable pageable);

    /**
     * Find most downloaded photos.
     *
     * @param pageable pagination information
     * @return page of most downloaded photos
     */
    Page<Photo> findAllByOrderByDownloadCountDesc(Pageable pageable);

    /**
     * Find most liked photos.
     *
     * @param pageable pagination information
     * @return page of most liked photos
     */
    Page<Photo> findAllByOrderByLikeCountDesc(Pageable pageable);

    /**
     * Find recently uploaded photos.
     *
     * @param pageable pagination information
     * @return page of recently uploaded photos
     */
    Page<Photo> findAllByOrderByCreatedAtDesc(Pageable pageable);

    /**
     * Find photos by camera model.
     *
     * @param cameraModel the camera model
     * @param pageable pagination information
     * @return page of photos taken with the camera
     */
    Page<Photo> findByCameraModelContainingIgnoreCase(String cameraModel, Pageable pageable);

    /**
     * Count photos by photographer.
     *
     * @param photographerId the photographer ID
     * @return count of photos
     */
    long countByPhotographerId(Long photographerId);

    /**
     * Count approved photos by photographer.
     *
     * @param photographerId the photographer ID
     * @param isApproved the approval status
     * @return count of approved photos
     */
    long countByPhotographerIdAndIsApproved(Long photographerId, Boolean isApproved);

    /**
     * Count photos by approval status.
     *
     * @param isApproved the approval status
     * @return count of photos
     */
    long countByIsApproved(Boolean isApproved);

    /**
     * Find pending approval photos.
     *
     * @param isApproved the approval status
     * @param pageable pagination information
     * @return page of pending photos
     */
    @Query("SELECT p FROM Photo p WHERE p.isApproved = :isApproved ORDER BY p.createdAt ASC")
    Page<Photo> findPendingApprovalPhotos(@Param("isApproved") Boolean isApproved, Pageable pageable);

    /**
     * Find photos needing watermark.
     *
     * @return list of photos without watermarked URL
     */
    @Query("SELECT p FROM Photo p WHERE p.watermarkedUrl IS NULL AND p.isActive = true")
    List<Photo> findPhotosNeedingWatermark();

    /**
     * Find photos needing thumbnail.
     *
     * @return list of photos without thumbnail URL
     */
    @Query("SELECT p FROM Photo p WHERE p.thumbnailUrl IS NULL AND p.isActive = true")
    List<Photo> findPhotosNeedingThumbnail();

    /**
     * Find similar photos by tags.
     *
     * @param photoId the photo ID to find similar photos for
     * @param limit the maximum number of results
     * @return list of similar photos
     */
    @Query("SELECT DISTINCT p FROM Photo p JOIN p.tags t WHERE t IN " +
           "(SELECT t2 FROM Photo p2 JOIN p2.tags t2 WHERE p2.id = :photoId) " +
           "AND p.id != :photoId AND p.isApproved = true AND p.isActive = true " +
           "ORDER BY p.viewCount DESC")
    List<Photo> findSimilarPhotos(@Param("photoId") Long photoId, Pageable pageable);

    /**
     * Advanced search with multiple criteria.
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
    @Query("""
        SELECT DISTINCT p FROM Photo p
        LEFT JOIN p.categories c
        LEFT JOIN p.tags t
        WHERE (:isApproved IS NULL OR p.isApproved = :isApproved)
          AND (:isActive IS NULL OR p.isActive = :isActive)
          AND (
                :searchTerm IS NULL
                OR LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
                OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
              )
          AND (
                :categoryIds IS NULL 
                OR c.id IS NULL 
                OR c.id IN :categoryIds
              )
          AND (
                :tagNames IS NULL 
                OR t.name IS NULL
                OR LOWER(t.name) IN :tagNames
              )
          AND (:minPrice IS NULL OR p.basePrice >= :minPrice)
          AND (:maxPrice IS NULL OR p.basePrice <= :maxPrice)
          AND (:location IS NULL OR LOWER(p.location) LIKE LOWER(CONCAT('%', :location, '%')))
          AND (:photographerId IS NULL OR p.photographer.id = :photographerId)
    """)
    Page<Photo> advancedSearch(
            @Param("searchTerm") String searchTerm,
            @Param("categoryIds") List<Long> categoryIds,
            @Param("tagNames") List<String> tagNames,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("location") String location,
            @Param("photographerId") Long photographerId,
            @Param("isApproved") Boolean isApproved,
            @Param("isActive") Boolean isActive,
            Pageable pageable
    );
}
