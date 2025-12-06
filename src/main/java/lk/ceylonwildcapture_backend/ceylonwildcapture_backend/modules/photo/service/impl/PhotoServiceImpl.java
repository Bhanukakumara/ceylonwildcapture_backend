package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.search.PhotoSearchCriteria;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.photo.*;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Photo;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Tag;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.repository.CategoryRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.repository.PhotoRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.repository.TagRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service.FileStorageService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service.PhotoService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final Cloudinary cloudinary;

    @Autowired(required = false)
    private FileStorageService fileStorageService;

    private static final String ERR_PHOTO_NOT_FOUND = "Photo not found: ";
    private static final String ERR_USER_NOT_FOUND = "User not found: ";
    private static final String ERR_TAG_NOT_FOUND = "Tag not found: ";
    private static final String ERR_CATEGORY_NOT_FOUND = "Category not found: ";

    public PhotoServiceImpl(PhotoRepository photoRepository,
            UserRepository userRepository,
            TagRepository tagRepository,
            CategoryRepository categoryRepository,
            Cloudinary cloudinary) {
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
        this.cloudinary = cloudinary;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PhotoResponseDto uploadPhoto(MultipartFile file, PhotoCreateDto photoCreateDto) {
        log.info("Uploading photo with title: {}", photoCreateDto.getTitle());
        Objects.requireNonNull(file, "File must not be null");
        Objects.requireNonNull(photoCreateDto, "PhotoCreateDto must not be null");

        // Validate file
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Get photographer
        User photographer = userRepository.findById(photoCreateDto.getPhotographerId())
                .orElseThrow(
                        () -> new IllegalArgumentException(ERR_USER_NOT_FOUND + photoCreateDto.getPhotographerId()));

        // ⭐ Upload to Cloudinary
        Map<String, Object> uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "photos",
                            "resource_type", "image"));
        } catch (IOException e) {
            throw new RuntimeException("Cloudinary upload failed: " + e.getMessage());
        }

        String imageUrl = (String) uploadResult.get("secure_url");

        // Build and save photo entity
        Photo photo = buildPhotoFromCreateDto(photoCreateDto, photographer, imageUrl);
        photo.setImageUrl(imageUrl);

        // Save photo
        Photo saved = photoRepository.save(photo);
        log.info("Photo uploaded successfully with ID: {}", saved.getId());

        return PhotoResponseDto.fromEntity(saved);
    }

    @Override
    public PhotoResponseDto createPhoto(PhotoCreateDto photoCreateDto) {
        log.info("Creating photo with title: {}", photoCreateDto.getTitle());
        Objects.requireNonNull(photoCreateDto, "PhotoCreateDto must not be null");

        User photographer = userRepository.findById(photoCreateDto.getPhotographerId())
                .orElseThrow(
                        () -> new IllegalArgumentException(ERR_USER_NOT_FOUND + photoCreateDto.getPhotographerId()));

        Photo photo = buildPhotoFromCreateDto(photoCreateDto, photographer, null);
        Photo saved = photoRepository.save(photo);

        log.info("Photo created successfully with ID: {}", saved.getId());
        return PhotoResponseDto.fromEntity(saved);
    }

    @Override
    public PhotoResponseDto updatePhoto(Long photoId, PhotoUpdateDto photoUpdateDto) {
        log.info("Updating photo with ID: {}", photoId);
        Objects.requireNonNull(photoUpdateDto, "PhotoUpdateDto must not be null");

        Photo existing = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        updatePhotoFromDto(existing, photoUpdateDto);

        Photo updated = photoRepository.save(existing);
        log.info("Photo updated successfully with ID: {}", updated.getId());
        return PhotoResponseDto.fromEntity(updated);
    }

    @Override
    public PhotoResponseDto updatePhotoMetadata(Long photoId, PhotoMetadataUpdateDto metadataUpdateDto) {
        log.info("Updating metadata for photo ID: {}", photoId);
        Objects.requireNonNull(metadataUpdateDto, "PhotoMetadataUpdateDto must not be null");

        Photo existing = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        if (metadataUpdateDto.getTitle() != null) {
            existing.setTitle(metadataUpdateDto.getTitle());
        }
        if (metadataUpdateDto.getDescription() != null) {
            existing.setDescription(metadataUpdateDto.getDescription());
        }
        if (metadataUpdateDto.getLocation() != null) {
            existing.setLocation(metadataUpdateDto.getLocation());
        }

        Photo updated = photoRepository.save(existing);
        return PhotoResponseDto.fromEntity(updated);
    }

    @Override
    public PhotoResponseDto updatePhotoPricing(Long photoId, PhotoPricingUpdateDto pricingUpdateDto) {
        log.info("Updating pricing for photo ID: {}", photoId);
        Objects.requireNonNull(pricingUpdateDto, "PhotoPricingUpdateDto must not be null");

        Photo existing = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        if (pricingUpdateDto.getBasePrice() != null) {
            existing.setBasePrice(pricingUpdateDto.getBasePrice());
        }
        if (pricingUpdateDto.getCommercialPrice() != null) {
            existing.setCommercialPrice(pricingUpdateDto.getCommercialPrice());
        }
        if (pricingUpdateDto.getEditorialPrice() != null) {
            existing.setEditorialPrice(pricingUpdateDto.getEditorialPrice());
        }
        if (pricingUpdateDto.getExtendedPrice() != null) {
            existing.setExtendedPrice(pricingUpdateDto.getExtendedPrice());
        }

        Photo updated = photoRepository.save(existing);
        return PhotoResponseDto.fromEntity(updated);
    }

    @Override
    public PhotoResponseDto updatePhotoExifData(Long photoId, PhotoExifUpdateDto exifUpdateDto) {
        log.info("Updating EXIF data for photo ID: {}", photoId);
        Objects.requireNonNull(exifUpdateDto, "PhotoExifUpdateDto must not be null");

        Photo existing = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        if (exifUpdateDto.getCameraModel() != null) {
            existing.setCameraModel(exifUpdateDto.getCameraModel());
        }
        if (exifUpdateDto.getLens() != null) {
            existing.setLens(exifUpdateDto.getLens());
        }
        if (exifUpdateDto.getFocalLength() != null) {
            existing.setFocalLength(exifUpdateDto.getFocalLength());
        }
        if (exifUpdateDto.getAperture() != null) {
            existing.setAperture(exifUpdateDto.getAperture());
        }
        if (exifUpdateDto.getShutterSpeed() != null) {
            existing.setShutterSpeed(exifUpdateDto.getShutterSpeed());
        }
        if (exifUpdateDto.getIso() != null) {
            existing.setIso(exifUpdateDto.getIso());
        }
        if (exifUpdateDto.getCaptureDate() != null) {
            existing.setCaptureDate(parseDateTime(exifUpdateDto.getCaptureDate()));
        }

        Photo updated = photoRepository.save(existing);
        return PhotoResponseDto.fromEntity(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PhotoResponseDto> getPhotoById(Long photoId) {
        log.debug("Getting photo by ID: {}", photoId);
        return photoRepository.findById(photoId)
                .map(PhotoResponseDto::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PhotoResponseDto> getPhotoWithPhotographer(Long photoId) {
        log.debug("Getting photo with photographer by ID: {}", photoId);
        return photoRepository.findByIdWithPhotographer(photoId)
                .map(PhotoResponseDto::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoResponseDto> getAllPhotos(Pageable pageable) {
        log.debug("Getting all photos with pagination");
        return photoRepository.findAll(pageable)
                .map(PhotoResponseDto::fromEntitySimple);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoResponseDto> getPhotosByPhotographer(Long photographerId, Pageable pageable) {
        log.debug("Getting photos by photographer ID: {}", photographerId);
        return photoRepository.findByPhotographerId(photographerId, pageable)
                .map(PhotoResponseDto::fromEntitySimple);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoResponseDto> getApprovedPhotosByPhotographer(Long photographerId, Pageable pageable) {
        log.debug("Getting approved photos by photographer ID: {}", photographerId);
        return photoRepository.findByPhotographerIdAndIsApproved(photographerId, true, pageable)
                .map(PhotoResponseDto::fromEntitySimple);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoResponseDto> getApprovedAndActivePhotos(Pageable pageable) {
        log.debug("Getting approved and active photos");
        return photoRepository.findByIsApprovedAndIsActive(true, true, pageable)
                .map(PhotoResponseDto::fromEntitySimple);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoResponseDto> getFeaturedPhotos(Pageable pageable) {
        log.debug("Getting featured photos");
        return photoRepository.findByIsFeatured(true, pageable)
                .map(PhotoResponseDto::fromEntitySimple);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoResponseDto> getPendingApprovalPhotos(Pageable pageable) {
        log.debug("Getting pending approval photos");
        return photoRepository.findPendingApprovalPhotos(false, pageable)
                .map(PhotoResponseDto::fromEntitySimple);
    }

    @Override
    public void deletePhoto(Long photoId) {
        log.info("Deleting photo with ID: {}", photoId);
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        // Delete files from storage
        if (fileStorageService != null) {
            if (photo.getImageUrl() != null) {
                fileStorageService.deleteFile(photo.getImageUrl());
            }
            if (photo.getThumbnailUrl() != null) {
                fileStorageService.deleteFile(photo.getThumbnailUrl());
            }
            if (photo.getWatermarkedUrl() != null) {
                fileStorageService.deleteFile(photo.getWatermarkedUrl());
            }
        } else {
            log.warn("FileStorageService not available, file deletion skipped");
        }

        photoRepository.delete(photo);
        log.info("Photo deleted successfully with ID: {}", photoId);
    }

    @Override
    public PhotoResponseDto deactivatePhoto(Long photoId) {
        log.info("Deactivating photo with ID: {}", photoId);
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        photo.setIsActive(false);
        Photo updated = photoRepository.save(photo);
        return PhotoResponseDto.fromEntity(updated);
    }

    @Override
    public PhotoResponseDto activatePhoto(Long photoId) {
        log.info("Activating photo with ID: {}", photoId);
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        photo.setIsActive(true);
        Photo updated = photoRepository.save(photo);
        return PhotoResponseDto.fromEntity(updated);
    }

    @Override
    public PhotoResponseDto approvePhoto(Long photoId) {
        log.info("Approving photo with ID: {}", photoId);
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        photo.setIsApproved(true);
        Photo updated = photoRepository.save(photo);
        return PhotoResponseDto.fromEntity(updated);
    }

    @Override
    public PhotoResponseDto rejectPhoto(Long photoId, String reason) {
        log.info("Rejecting photo with ID: {} for reason: {}", photoId, reason);
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        photo.setIsApproved(false);
        photo.setIsActive(false);
        Photo updated = photoRepository.save(photo);
        return PhotoResponseDto.fromEntity(updated);
    }

    @Override
    public PhotoResponseDto setFeatured(Long photoId, Boolean featured) {
        log.info("Setting featured status to {} for photo ID: {}", featured, photoId);
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        photo.setIsFeatured(featured);
        Photo updated = photoRepository.save(photo);
        return PhotoResponseDto.fromEntity(updated);
    }

    @Override
    public PhotoResponseDto assignTags(Long photoId, List<Long> tagIds) {
        log.info("Assigning tags to photo ID: {}", photoId);
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        List<Tag> tags = tagRepository.findAllById(tagIds);
        if (tags.size() != tagIds.size()) {
            throw new IllegalArgumentException("One or more tags not found");
        }

        photo.setTags(tags);
        Photo updated = photoRepository.save(photo);
        return PhotoResponseDto.fromEntity(updated);
    }

    @Override
    public PhotoResponseDto addTag(Long photoId, Long tagId) {
        log.info("Adding tag {} to photo ID: {}", tagId, photoId);
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_TAG_NOT_FOUND + tagId));

        if (!photo.getTags().contains(tag)) {
            photo.getTags().add(tag);
            tag.setUsageCount(tag.getUsageCount() + 1);
            tagRepository.save(tag);
        }

        Photo updated = photoRepository.save(photo);
        return PhotoResponseDto.fromEntity(updated);
    }

    @Override
    public PhotoResponseDto removeTag(Long photoId, Long tagId) {
        log.info("Removing tag {} from photo ID: {}", tagId, photoId);
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_TAG_NOT_FOUND + tagId));

        if (photo.getTags().remove(tag)) {
            tag.setUsageCount(Math.max(0, tag.getUsageCount() - 1));
            tagRepository.save(tag);
        }

        Photo updated = photoRepository.save(photo);
        return PhotoResponseDto.fromEntity(updated);
    }

    @Override
    public PhotoResponseDto assignCategories(Long photoId, List<Long> categoryIds) {
        log.info("Assigning categories to photo ID: {}", photoId);
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        List<Category> categories = categoryRepository.findAllById(categoryIds);
        if (categories.size() != categoryIds.size()) {
            throw new IllegalArgumentException("One or more categories not found");
        }

        photo.setCategories(categories);
        Photo updated = photoRepository.save(photo);
        return PhotoResponseDto.fromEntity(updated);
    }

    @Override
    public PhotoResponseDto addCategory(Long photoId, Long categoryId) {
        log.info("Adding category {} to photo ID: {}", categoryId, photoId);
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_CATEGORY_NOT_FOUND + categoryId));

        if (!photo.getCategories().contains(category)) {
            photo.getCategories().add(category);
        }

        Photo updated = photoRepository.save(photo);
        return PhotoResponseDto.fromEntity(updated);
    }

    @Override
    public PhotoResponseDto removeCategory(Long photoId, Long categoryId) {
        log.info("Removing category {} from photo ID: {}", categoryId, photoId);
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_CATEGORY_NOT_FOUND + categoryId));

        photo.getCategories().remove(category);

        Photo updated = photoRepository.save(photo);
        return PhotoResponseDto.fromEntity(updated);
    }

    @Override
    public PhotoResponseDto incrementViewCount(Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        photo.setViewCount(photo.getViewCount() + 1);
        Photo updated = photoRepository.save(photo);
        return PhotoResponseDto.fromEntitySimple(updated);
    }

    @Override
    public PhotoResponseDto incrementDownloadCount(Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        photo.setDownloadCount(photo.getDownloadCount() + 1);
        Photo updated = photoRepository.save(photo);
        return PhotoResponseDto.fromEntitySimple(updated);
    }

    @Override
    public PhotoResponseDto incrementLikeCount(Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        photo.setLikeCount(photo.getLikeCount() + 1);
        Photo updated = photoRepository.save(photo);
        return PhotoResponseDto.fromEntitySimple(updated);
    }

    @Override
    public PhotoResponseDto decrementLikeCount(Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        photo.setLikeCount(Math.max(0, photo.getLikeCount() - 1));
        Photo updated = photoRepository.save(photo);
        return PhotoResponseDto.fromEntitySimple(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoResponseDto> getMostViewedPhotos(Pageable pageable) {
        return photoRepository.findAllByOrderByViewCountDesc(pageable)
                .map(PhotoResponseDto::fromEntitySimple);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoResponseDto> getMostDownloadedPhotos(Pageable pageable) {
        return photoRepository.findAllByOrderByDownloadCountDesc(pageable)
                .map(PhotoResponseDto::fromEntitySimple);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoResponseDto> getMostLikedPhotos(Pageable pageable) {
        return photoRepository.findAllByOrderByLikeCountDesc(pageable)
                .map(PhotoResponseDto::fromEntitySimple);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoResponseDto> getRecentlyUploadedPhotos(Pageable pageable) {
        return photoRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(PhotoResponseDto::fromEntitySimple);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PhotoResponseDto> getSimilarPhotos(Long photoId, Integer limit) {
        log.debug("Getting similar photos for photo ID: {} with limit: {}", photoId, limit);
        List<Photo> photos = photoRepository.findSimilarPhotos(photoId, PageRequest.of(0, limit));
        return photos.stream()
                .map(PhotoResponseDto::fromEntitySimple)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoResponseDto> getPhotosByTag(String tagName, Pageable pageable) {
        log.debug("Getting photos by tag name: {}", tagName);
        return photoRepository.findByTagsName(tagName, pageable)
                .map(PhotoResponseDto::fromEntitySimple);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoResponseDto> getPhotosByCategory(String categorySlug, Pageable pageable) {
        log.debug("Getting photos by category slug: {}", categorySlug);
        return photoRepository.findByCategorySlug(categorySlug, pageable)
                .map(PhotoResponseDto::fromEntitySimple);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoResponseDto> getPhotosByLocation(String location, Pageable pageable) {
        log.debug("Getting photos by location: {}", location);
        return photoRepository.findByLocationContainingIgnoreCase(location, pageable)
                .map(PhotoResponseDto::fromEntitySimple);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoResponseDto> getPhotosByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        log.debug("Getting photos by price range: {} - {}", minPrice, maxPrice);
        return photoRepository.findByPriceRange(minPrice, maxPrice, pageable)
                .map(PhotoResponseDto::fromEntitySimple);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoResponseDto> getPhotosUploadedBetween(LocalDateTime startDate, LocalDateTime endDate,
            Pageable pageable) {
        log.debug("Getting photos uploaded between {} and {}", startDate, endDate);
        return photoRepository.findByCreatedAtBetween(startDate, endDate, pageable)
                .map(PhotoResponseDto::fromEntitySimple);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoResponseDto> searchPhotos(String searchTerm, Pageable pageable) {
        log.debug("Searching photos with term: {}", searchTerm);
        return photoRepository.searchByTitleOrDescription(searchTerm, pageable)
                .map(PhotoResponseDto::fromEntitySimple);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhotoResponseDto> advancedSearch(PhotoSearchCriteria searchCriteria, Pageable pageable) {
        log.debug("Advanced photo search with criteria: {}", searchCriteria);

        List<String> tagNames = null;
        if (searchCriteria.getTagNames() != null && !searchCriteria.getTagNames().isEmpty()) {
            tagNames = searchCriteria.getTagNames().stream()
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
        }

        return photoRepository.advancedSearch(
                searchCriteria.getKeyword(),
                searchCriteria.getCategoryIds(),
                tagNames,
                searchCriteria.getMinPrice(),
                searchCriteria.getMaxPrice(),
                searchCriteria.getLocation(),
                searchCriteria.getPhotographerId(),
                searchCriteria.getIsApproved(),
                searchCriteria.getIsActive(),
                pageable).map(PhotoResponseDto::fromEntitySimple);
    }

    @Override
    @Transactional(readOnly = true)
    public long countPhotosByPhotographer(Long photographerId) {
        return photoRepository.countByPhotographerId(photographerId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countApprovedPhotosByPhotographer(Long photographerId) {
        return photoRepository.countByPhotographerIdAndIsApproved(photographerId, true);
    }

    @Override
    @Transactional(readOnly = true)
    public long countPendingApprovalPhotos() {
        return photoRepository.countByIsApproved(false);
    }

    @Override
    @Transactional(readOnly = true)
    public long countTotalPhotos() {
        return photoRepository.count();
    }

    @Override
    public PhotoResponseDto processUploadedPhoto(Long photoId) {
        log.info("Processing uploaded photo ID: {}", photoId);
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PHOTO_NOT_FOUND + photoId));

        try {
            // Note: Actual image processing (thumbnail, watermark, EXIF extraction)
            // should be implemented using ImageProcessingService and FileStorageService
            // when those services are fully implemented

            Photo updated = photoRepository.save(photo);
            return PhotoResponseDto.fromEntity(updated);
        } catch (Exception e) {
            log.error("Error processing photo ID: {}", photoId, e);
            throw new IllegalArgumentException("Failed to process photo: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean photoExists(Long photoId) {
        return photoRepository.existsById(photoId);
    }

    // Helper methods

    private Photo buildPhotoFromCreateDto(PhotoCreateDto dto, User photographer, String imageUrl) {
        Photo.PhotoBuilder builder = Photo.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .photographer(photographer)
                .basePrice(dto.getBasePrice())
                .commercialPrice(dto.getCommercialPrice())
                .editorialPrice(dto.getEditorialPrice())
                .extendedPrice(dto.getExtendedPrice())
                .location(dto.getLocation())
                .cameraModel(dto.getCameraModel())
                .lens(dto.getLens())
                .focalLength(dto.getFocalLength())
                .aperture(dto.getAperture())
                .shutterSpeed(dto.getShutterSpeed())
                .iso(dto.getIso())
                .isApproved(false)
                .isFeatured(false)
                .isActive(true)
                .viewCount(0)
                .downloadCount(0)
                .likeCount(0);

        if (imageUrl != null) {
            builder.imageUrl(imageUrl);
        }

        if (dto.getCaptureDate() != null) {
            builder.captureDate(parseDateTime(dto.getCaptureDate()));
        }

        Photo photo = builder.build();

        // Assign tags
        if (dto.getTagIds() != null && !dto.getTagIds().isEmpty()) {
            List<Tag> tags = tagRepository.findAllById(dto.getTagIds());
            photo.setTags(tags);
        }

        // Assign categories
        if (dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
            photo.setCategories(categories);
        }

        return photo;
    }

    private void updatePhotoFromDto(Photo photo, PhotoUpdateDto dto) {
        if (dto.getTitle() != null) {
            photo.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            photo.setDescription(dto.getDescription());
        }
        if (dto.getBasePrice() != null) {
            photo.setBasePrice(dto.getBasePrice());
        }
        if (dto.getCommercialPrice() != null) {
            photo.setCommercialPrice(dto.getCommercialPrice());
        }
        if (dto.getEditorialPrice() != null) {
            photo.setEditorialPrice(dto.getEditorialPrice());
        }
        if (dto.getExtendedPrice() != null) {
            photo.setExtendedPrice(dto.getExtendedPrice());
        }
        if (dto.getLocation() != null) {
            photo.setLocation(dto.getLocation());
        }
        if (dto.getIsActive() != null) {
            photo.setIsActive(dto.getIsActive());
        }
        if (dto.getIsFeatured() != null) {
            photo.setIsFeatured(dto.getIsFeatured());
        }

        // Update tags
        if (dto.getTagIds() != null) {
            List<Tag> tags = tagRepository.findAllById(dto.getTagIds());
            photo.setTags(tags);
        }

        // Update categories
        if (dto.getCategoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
            photo.setCategories(categories);
        }
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isBlank()) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            log.warn("Failed to parse datetime: {}", dateTimeStr, e);
            return null;
        }
    }
}
