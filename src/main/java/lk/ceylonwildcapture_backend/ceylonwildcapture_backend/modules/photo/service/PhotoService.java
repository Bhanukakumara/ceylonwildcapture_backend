package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Photo;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service interface for Photo management operations.
 * Defines business logic for photo CRUD, metadata management, approval,
 * and photographer-specific operations.
 */
public interface PhotoService {

    /**
     * Create a new photo.
     *
     * @param photo the photo entity to create
     * @return the created photo
     * @throws IllegalArgumentException if photo data is invalid
     */
    Photo createPhoto(Photo photo);

    /**
     * Upload and create a new photo with file.
     *
     * @param file the photo file
     * @param photographerId the photographer ID
     * @param photoData the photo metadata (DTO placeholder)
     * @return the created photo
     * @throws IllegalArgumentException if file or data is invalid
     */
    Photo uploadPhoto(MultipartFile file, Long photographerId, Object photoData);

    /**
     * Update an existing photo.
     *
     * @param photoId the photo ID
     * @param photo the updated photo data
     * @return the updated photo
     * @throws IllegalArgumentException if photo not found
     */
    Photo updatePhoto(Long photoId, Photo photo);

    /**
     * Update photo metadata.
     *
     * @param photoId the photo ID
     * @param title the title
     * @param description the description
     * @param location the location
     * @return the updated photo
     * @throws IllegalArgumentException if photo not found
     */
    Photo updatePhotoMetadata(Long photoId, String title, String description, String location);

    /**
     * Update photo pricing.
     *
     * @param photoId the photo ID
     * @param basePrice the base price
     * @param commercialPrice the commercial license price
     * @param editorialPrice the editorial license price
     * @param extendedPrice the extended license price
     * @return the updated photo
     * @throws IllegalArgumentException if photo not found or prices invalid
     */
    Photo updatePhotoPricing(Long photoId, BigDecimal basePrice, BigDecimal commercialPrice,
                             BigDecimal editorialPrice, BigDecimal extendedPrice);

    /**
     * Update photo EXIF data.
     *
     * @param photoId the photo ID
     * @param exifData the EXIF data map
     * @return the updated photo
     * @throws IllegalArgumentException if photo not found
     */
    Photo updatePhotoExifData(Long photoId, Map<String, String> exifData);

    /**
     * Get photo by ID.
     *
     * @param photoId the photo ID
     * @return Optional containing the photo if found
     */
    Optional<Photo> getPhotoById(Long photoId);

    /**
     * Get photo by ID with photographer details.
     *
     * @param photoId the photo ID
     * @return Optional containing the photo if found
     */
    Optional<Photo> getPhotoWithPhotographer(Long photoId);

    /**
     * Get all photos with pagination.
     *
     * @param pageable pagination information
     * @return page of photos
     */
    Page<Photo> getAllPhotos(Pageable pageable);

    /**
     * Get photos by photographer.
     *
     * @param photographerId the photographer ID
     * @param pageable pagination information
     * @return page of photographer's photos
     */
    Page<Photo> getPhotosByPhotographer(Long photographerId, Pageable pageable);

    /**
     * Get approved photos by photographer.
     *
     * @param photographerId the photographer ID
     * @param pageable pagination information
     * @return page of approved photos
     */
    Page<Photo> getApprovedPhotosByPhotographer(Long photographerId, Pageable pageable);

    /**
     * Get approved and active photos.
     *
     * @param pageable pagination information
     * @return page of approved and active photos
     */
    Page<Photo> getApprovedAndActivePhotos(Pageable pageable);

    /**
     * Get featured photos.
     *
     * @param pageable pagination information
     * @return page of featured photos
     */
    Page<Photo> getFeaturedPhotos(Pageable pageable);

    /**
     * Get pending approval photos.
     *
     * @param pageable pagination information
     * @return page of pending photos
     */
    Page<Photo> getPendingApprovalPhotos(Pageable pageable);

    /**
     * Delete photo.
     *
     * @param photoId the photo ID
     * @throws IllegalArgumentException if photo not found
     */
    void deletePhoto(Long photoId);

    /**
     * Soft delete photo (set inactive).
     *
     * @param photoId the photo ID
     * @return the deactivated photo
     * @throws IllegalArgumentException if photo not found
     */
    Photo deactivatePhoto(Long photoId);

    /**
     * Activate photo.
     *
     * @param photoId the photo ID
     * @return the activated photo
     * @throws IllegalArgumentException if photo not found
     */
    Photo activatePhoto(Long photoId);

    /**
     * Approve photo for sale.
     *
     * @param photoId the photo ID
     * @return the approved photo
     * @throws IllegalArgumentException if photo not found
     */
    Photo approvePhoto(Long photoId);

    /**
     * Reject photo.
     *
     * @param photoId the photo ID
     * @param reason the rejection reason
     * @return the rejected photo
     * @throws IllegalArgumentException if photo not found
     */
    Photo rejectPhoto(Long photoId, String reason);

    /**
     * Set photo as featured.
     *
     * @param photoId the photo ID
     * @param featured the featured status
     * @return the updated photo
     * @throws IllegalArgumentException if photo not found
     */
    Photo setFeatured(Long photoId, Boolean featured);

    /**
     * Assign tags to photo.
     *
     * @param photoId the photo ID
     * @param tags the list of tags to assign
     * @return the updated photo
     * @throws IllegalArgumentException if photo not found
     */
    Photo assignTags(Long photoId, List<Tag> tags);

    /**
     * Add tag to photo.
     *
     * @param photoId the photo ID
     * @param tag the tag to add
     * @return the updated photo
     * @throws IllegalArgumentException if photo not found
     */
    Photo addTag(Long photoId, Tag tag);

    /**
     * Remove tag from photo.
     *
     * @param photoId the photo ID
     * @param tagId the tag ID
     * @return the updated photo
     * @throws IllegalArgumentException if photo or tag not found
     */
    Photo removeTag(Long photoId, Long tagId);

    /**
     * Assign categories to photo.
     *
     * @param photoId the photo ID
     * @param categories the list of categories to assign
     * @return the updated photo
     * @throws IllegalArgumentException if photo not found
     */
    Photo assignCategories(Long photoId, List<Category> categories);

    /**
     * Add category to photo.
     *
     * @param photoId the photo ID
     * @param category the category to add
     * @return the updated photo
     * @throws IllegalArgumentException if photo not found
     */
    Photo addCategory(Long photoId, Category category);

    /**
     * Remove category from photo.
     *
     * @param photoId the photo ID
     * @param categoryId the category ID
     * @return the updated photo
     * @throws IllegalArgumentException if photo or category not found
     */
    Photo removeCategory(Long photoId, Long categoryId);

    /**
     * Increment photo view count.
     *
     * @param photoId the photo ID
     * @return the updated photo
     * @throws IllegalArgumentException if photo not found
     */
    Photo incrementViewCount(Long photoId);

    /**
     * Increment photo download count.
     *
     * @param photoId the photo ID
     * @return the updated photo
     * @throws IllegalArgumentException if photo not found
     */
    Photo incrementDownloadCount(Long photoId);

    /**
     * Increment photo like count.
     *
     * @param photoId the photo ID
     * @return the updated photo
     * @throws IllegalArgumentException if photo not found
     */
    Photo incrementLikeCount(Long photoId);

    /**
     * Decrement photo like count.
     *
     * @param photoId the photo ID
     * @return the updated photo
     * @throws IllegalArgumentException if photo not found
     */
    Photo decrementLikeCount(Long photoId);

    /**
     * Get most viewed photos.
     *
     * @param pageable pagination information
     * @return page of most viewed photos
     */
    Page<Photo> getMostViewedPhotos(Pageable pageable);

    /**
     * Get most downloaded photos.
     *
     * @param pageable pagination information
     * @return page of most downloaded photos
     */
    Page<Photo> getMostDownloadedPhotos(Pageable pageable);

    /**
     * Get most liked photos.
     *
     * @param pageable pagination information
     * @return page of most liked photos
     */
    Page<Photo> getMostLikedPhotos(Pageable pageable);

    /**
     * Get recently uploaded photos.
     *
     * @param pageable pagination information
     * @return page of recently uploaded photos
     */
    Page<Photo> getRecentlyUploadedPhotos(Pageable pageable);

    /**
     * Get similar photos based on tags.
     *
     * @param photoId the photo ID
     * @param limit the maximum number of similar photos
     * @return list of similar photos
     */
    List<Photo> getSimilarPhotos(Long photoId, Integer limit);

    /**
     * Get photos by tag.
     *
     * @param tagName the tag name
     * @param pageable pagination information
     * @return page of photos with the tag
     */
    Page<Photo> getPhotosByTag(String tagName, Pageable pageable);

    /**
     * Get photos by category.
     *
     * @param categorySlug the category slug
     * @param pageable pagination information
     * @return page of photos in the category
     */
    Page<Photo> getPhotosByCategory(String categorySlug, Pageable pageable);

    /**
     * Get photos by location.
     *
     * @param location the location
     * @param pageable pagination information
     * @return page of photos from the location
     */
    Page<Photo> getPhotosByLocation(String location, Pageable pageable);

    /**
     * Get photos by price range.
     *
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @param pageable pagination information
     * @return page of photos within price range
     */
    Page<Photo> getPhotosByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    /**
     * Get photos uploaded within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of photos uploaded within date range
     */
    Page<Photo> getPhotosUploadedBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Search photos by title or description.
     *
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of matching photos
     */
    Page<Photo> searchPhotos(String searchTerm, Pageable pageable);

    /**
     * Count photos by photographer.
     *
     * @param photographerId the photographer ID
     * @return count of photos
     */
    long countPhotosByPhotographer(Long photographerId);

    /**
     * Count approved photos by photographer.
     *
     * @param photographerId the photographer ID
     * @return count of approved photos
     */
    long countApprovedPhotosByPhotographer(Long photographerId);

    /**
     * Count pending approval photos.
     *
     * @return count of pending photos
     */
    long countPendingApprovalPhotos();

    /**
     * Process uploaded photo (generate thumbnails, watermarks, extract EXIF).
     *
     * @param photoId the photo ID
     * @return the processed photo
     * @throws IllegalArgumentException if photo not found
     */
    Photo processUploadedPhoto(Long photoId);
}
