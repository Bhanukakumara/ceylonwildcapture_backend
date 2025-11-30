package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.PhotoSearchCriteria;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.photo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Photo management operations.
 * Defines business logic for photo CRUD, metadata management, approval,
 * and photographer-specific operations.
 */
public interface PhotoService {

    /**
     * Upload and create a new photo with file.
     *
     * @param file the photo file
     * @param photoCreateDto the photo creation data
     * @return the created photo response DTO
     * @throws IllegalArgumentException if file or data is invalid
     */
    PhotoResponseDto uploadPhoto(MultipartFile file, PhotoCreateDto photoCreateDto);

    /**
     * Create a new photo (without file upload).
     *
     * @param photoCreateDto the photo creation data
     * @return the created photo response DTO
     * @throws IllegalArgumentException if photo data is invalid
     */
    PhotoResponseDto createPhoto(PhotoCreateDto photoCreateDto);

    /**
     * Update an existing photo.
     *
     * @param photoId the photo ID
     * @param photoUpdateDto the updated photo data
     * @return the updated photo response DTO
     * @throws IllegalArgumentException if photo not found
     */
    PhotoResponseDto updatePhoto(Long photoId, PhotoUpdateDto photoUpdateDto);

    /**
     * Update photo metadata.
     *
     * @param photoId the photo ID
     * @param metadataUpdateDto the metadata update data
     * @return the updated photo response DTO
     * @throws IllegalArgumentException if photo not found
     */
    PhotoResponseDto updatePhotoMetadata(Long photoId, PhotoMetadataUpdateDto metadataUpdateDto);

    /**
     * Update photo pricing.
     *
     * @param photoId the photo ID
     * @param pricingUpdateDto the pricing update data
     * @return the updated photo response DTO
     * @throws IllegalArgumentException if photo not found or prices invalid
     */
    PhotoResponseDto updatePhotoPricing(Long photoId, PhotoPricingUpdateDto pricingUpdateDto);

    /**
     * Update photo EXIF data.
     *
     * @param photoId the photo ID
     * @param exifUpdateDto the EXIF data update DTO
     * @return the updated photo response DTO
     * @throws IllegalArgumentException if photo not found
     */
    PhotoResponseDto updatePhotoExifData(Long photoId, PhotoExifUpdateDto exifUpdateDto);

    /**
     * Get photo by ID.
     *
     * @param photoId the photo ID
     * @return Optional containing the photo response DTO if found
     */
    Optional<PhotoResponseDto> getPhotoById(Long photoId);

    /**
     * Get photo by ID with photographer details.
     *
     * @param photoId the photo ID
     * @return Optional containing the photo response DTO if found
     */
    Optional<PhotoResponseDto> getPhotoWithPhotographer(Long photoId);

    /**
     * Get all photos with pagination.
     *
     * @param pageable pagination information
     * @return page of photo response DTOs
     */
    Page<PhotoResponseDto> getAllPhotos(Pageable pageable);

    /**
     * Get photos by photographer.
     *
     * @param photographerId the photographer ID
     * @param pageable pagination information
     * @return page of photo response DTOs
     */
    Page<PhotoResponseDto> getPhotosByPhotographer(Long photographerId, Pageable pageable);

    /**
     * Get approved photos by photographer.
     *
     * @param photographerId the photographer ID
     * @param pageable pagination information
     * @return page of photo response DTOs
     */
    Page<PhotoResponseDto> getApprovedPhotosByPhotographer(Long photographerId, Pageable pageable);

    /**
     * Get approved and active photos.
     *
     * @param pageable pagination information
     * @return page of photo response DTOs
     */
    Page<PhotoResponseDto> getApprovedAndActivePhotos(Pageable pageable);

    /**
     * Get featured photos.
     *
     * @param pageable pagination information
     * @return page of photo response DTOs
     */
    Page<PhotoResponseDto> getFeaturedPhotos(Pageable pageable);

    /**
     * Get pending approval photos.
     *
     * @param pageable pagination information
     * @return page of photo response DTOs
     */
    Page<PhotoResponseDto> getPendingApprovalPhotos(Pageable pageable);

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
     * @return the deactivated photo response DTO
     * @throws IllegalArgumentException if photo not found
     */
    PhotoResponseDto deactivatePhoto(Long photoId);

    /**
     * Activate photo.
     *
     * @param photoId the photo ID
     * @return the activated photo response DTO
     * @throws IllegalArgumentException if photo not found
     */
    PhotoResponseDto activatePhoto(Long photoId);

    /**
     * Approve photo for sale.
     *
     * @param photoId the photo ID
     * @return the approved photo response DTO
     * @throws IllegalArgumentException if photo not found
     */
    PhotoResponseDto approvePhoto(Long photoId);

    /**
     * Reject photo.
     *
     * @param photoId the photo ID
     * @param reason the rejection reason
     * @return the rejected photo response DTO
     * @throws IllegalArgumentException if photo not found
     */
    PhotoResponseDto rejectPhoto(Long photoId, String reason);

    /**
     * Set photo as featured.
     *
     * @param photoId the photo ID
     * @param featured the featured status
     * @return the updated photo response DTO
     * @throws IllegalArgumentException if photo not found
     */
    PhotoResponseDto setFeatured(Long photoId, Boolean featured);

    /**
     * Assign tags to photo.
     *
     * @param photoId the photo ID
     * @param tagIds the list of tag IDs to assign
     * @return the updated photo response DTO
     * @throws IllegalArgumentException if photo not found
     */
    PhotoResponseDto assignTags(Long photoId, List<Long> tagIds);

    /**
     * Add tag to photo.
     *
     * @param photoId the photo ID
     * @param tagId the tag ID to add
     * @return the updated photo response DTO
     * @throws IllegalArgumentException if photo not found
     */
    PhotoResponseDto addTag(Long photoId, Long tagId);

    /**
     * Remove tag from photo.
     *
     * @param photoId the photo ID
     * @param tagId the tag ID
     * @return the updated photo response DTO
     * @throws IllegalArgumentException if photo or tag not found
     */
    PhotoResponseDto removeTag(Long photoId, Long tagId);

    /**
     * Assign categories to photo.
     *
     * @param photoId the photo ID
     * @param categoryIds the list of category IDs to assign
     * @return the updated photo response DTO
     * @throws IllegalArgumentException if photo not found
     */
    PhotoResponseDto assignCategories(Long photoId, List<Long> categoryIds);

    /**
     * Add category to photo.
     *
     * @param photoId the photo ID
     * @param categoryId the category ID to add
     * @return the updated photo response DTO
     * @throws IllegalArgumentException if photo not found
     */
    PhotoResponseDto addCategory(Long photoId, Long categoryId);

    /**
     * Remove category from photo.
     *
     * @param photoId the photo ID
     * @param categoryId the category ID
     * @return the updated photo response DTO
     * @throws IllegalArgumentException if photo or category not found
     */
    PhotoResponseDto removeCategory(Long photoId, Long categoryId);

    /**
     * Increment photo view count.
     *
     * @param photoId the photo ID
     * @return the updated photo response DTO
     * @throws IllegalArgumentException if photo not found
     */
    PhotoResponseDto incrementViewCount(Long photoId);

    /**
     * Increment photo download count.
     *
     * @param photoId the photo ID
     * @return the updated photo response DTO
     * @throws IllegalArgumentException if photo not found
     */
    PhotoResponseDto incrementDownloadCount(Long photoId);

    /**
     * Increment photo like count.
     *
     * @param photoId the photo ID
     * @return the updated photo response DTO
     * @throws IllegalArgumentException if photo not found
     */
    PhotoResponseDto incrementLikeCount(Long photoId);

    /**
     * Decrement photo like count.
     *
     * @param photoId the photo ID
     * @return the updated photo response DTO
     * @throws IllegalArgumentException if photo not found
     */
    PhotoResponseDto decrementLikeCount(Long photoId);

    /**
     * Get most viewed photos.
     *
     * @param pageable pagination information
     * @return page of photo response DTOs
     */
    Page<PhotoResponseDto> getMostViewedPhotos(Pageable pageable);

    /**
     * Get most downloaded photos.
     *
     * @param pageable pagination information
     * @return page of photo response DTOs
     */
    Page<PhotoResponseDto> getMostDownloadedPhotos(Pageable pageable);

    /**
     * Get most liked photos.
     *
     * @param pageable pagination information
     * @return page of photo response DTOs
     */
    Page<PhotoResponseDto> getMostLikedPhotos(Pageable pageable);

    /**
     * Get recently uploaded photos.
     *
     * @param pageable pagination information
     * @return page of photo response DTOs
     */
    Page<PhotoResponseDto> getRecentlyUploadedPhotos(Pageable pageable);

    /**
     * Get similar photos based on tags.
     *
     * @param photoId the photo ID
     * @param limit the maximum number of similar photos
     * @return list of photo response DTOs
     */
    List<PhotoResponseDto> getSimilarPhotos(Long photoId, Integer limit);

    /**
     * Get photos by tag.
     *
     * @param tagName the tag name
     * @param pageable pagination information
     * @return page of photo response DTOs
     */
    Page<PhotoResponseDto> getPhotosByTag(String tagName, Pageable pageable);

    /**
     * Get photos by category.
     *
     * @param categorySlug the category slug
     * @param pageable pagination information
     * @return page of photo response DTOs
     */
    Page<PhotoResponseDto> getPhotosByCategory(String categorySlug, Pageable pageable);

    /**
     * Get photos by location.
     *
     * @param location the location
     * @param pageable pagination information
     * @return page of photo response DTOs
     */
    Page<PhotoResponseDto> getPhotosByLocation(String location, Pageable pageable);

    /**
     * Get photos by price range.
     *
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @param pageable pagination information
     * @return page of photo response DTOs
     */
    Page<PhotoResponseDto> getPhotosByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    /**
     * Get photos uploaded within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of photo response DTOs
     */
    Page<PhotoResponseDto> getPhotosUploadedBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Search photos by title or description.
     *
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of photo response DTOs
     */
    Page<PhotoResponseDto> searchPhotos(String searchTerm, Pageable pageable);

    /**
     * Advanced search with multiple criteria.
     *
     * @param searchCriteria the search criteria DTO
     * @param pageable pagination information
     * @return page of photo response DTOs
     */
    Page<PhotoResponseDto> advancedSearch(PhotoSearchCriteria searchCriteria, Pageable pageable);

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
     * Count total photos.
     *
     * @return total count of photos
     */
    long countTotalPhotos();

    /**
     * Process uploaded photo (generate thumbnails, watermarks, extract EXIF).
     *
     * @param photoId the photo ID
     * @return the processed photo response DTO
     * @throws IllegalArgumentException if photo not found
     */
    PhotoResponseDto processUploadedPhoto(Long photoId);

    /**
     * Check if photo exists by ID.
     *
     * @param photoId the photo ID
     * @return true if photo exists
     */
    boolean photoExists(Long photoId);
}
