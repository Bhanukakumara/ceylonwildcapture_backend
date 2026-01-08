package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.search.PhotoSearchCriteria;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.photo.*;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service.PhotoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/photos")
@RequiredArgsConstructor
@Slf4j
public class PhotoController {

    private final PhotoService photoService;

    // ------------------------------
    // Upload Photo
    // ------------------------------
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PhotoResponseDto> uploadPhoto(
            @RequestPart("file") @NotNull MultipartFile file,
            @RequestPart("data") @Valid PhotoCreateDto photoCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(photoService.uploadPhoto(file, photoCreateDto));
    }

    // ------------------------------
    // Create Photo (without file upload)
    // ------------------------------
    @PostMapping
    public ResponseEntity<PhotoResponseDto> createPhoto(@Valid @RequestBody PhotoCreateDto photoCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(photoService.createPhoto(photoCreateDto));
    }

    // ------------------------------
    // Update Photo
    // ------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<PhotoResponseDto> updatePhoto(
            @PathVariable("id") Long id,
            @Valid @RequestBody PhotoUpdateDto photoUpdateDto) {
        return ResponseEntity.ok(photoService.updatePhoto(id, photoUpdateDto));
    }

    // ------------------------------
    // Update Photo Metadata
    // ------------------------------
    @PatchMapping("/{id}/metadata")
    public ResponseEntity<PhotoResponseDto> updateMetadata(
            @PathVariable("id") Long id,
            @Valid @RequestBody PhotoMetadataUpdateDto metadataUpdateDto) {
        return ResponseEntity.ok(photoService.updatePhotoMetadata(id, metadataUpdateDto));
    }

    // ------------------------------
    // Update Photo Pricing
    // ------------------------------
    @PatchMapping("/{id}/pricing")
    public ResponseEntity<PhotoResponseDto> updatePricing(
            @PathVariable("id") Long id,
            @RequestParam("basePrice") @NotNull BigDecimal basePrice) {
        return ResponseEntity.ok(photoService.updatePhotoPricing(id, basePrice));
    }

    // ------------------------------
    // Update Photo EXIF Data
    // ------------------------------
    @PatchMapping("/{id}/exif")
    public ResponseEntity<PhotoResponseDto> updateExifData(
            @PathVariable("id") Long id,
            @Valid @RequestBody PhotoExifUpdateDto exifUpdateDto) {
        return ResponseEntity.ok(photoService.updatePhotoExifData(id, exifUpdateDto));
    }

    // ------------------------------
    // Get Photo by ID
    // ------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<PhotoResponseDto> getById(@PathVariable("id") Long id) {
        return photoService.getPhotoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------
    // Get Photo by ID with Photographer
    // ------------------------------
    @GetMapping("/{id}/with-photographer")
    public ResponseEntity<PhotoResponseDto> getByIdWithPhotographer(@PathVariable("id") Long id) {
        return photoService.getPhotoWithPhotographer(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------
    // Get All Photos (paged)
    // ------------------------------
    @GetMapping
    public Page<PhotoResponseDto> getAll(Pageable pageable) {
        return photoService.getAllPhotos(pageable);
    }

    // ------------------------------
    // Get Photos by Photographer
    // ------------------------------
    @GetMapping("/photographer/{photographerId}")
    public Page<PhotoResponseDto> getByPhotographer(
            @PathVariable("photographerId") Long photographerId,
            Pageable pageable) {
        return photoService.getPhotosByPhotographer(photographerId, pageable);
    }

    // ------------------------------
    // Get Approved Photos by Photographer
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/approved")
    public Page<PhotoResponseDto> getApprovedByPhotographer(
            @PathVariable("photographerId") Long photographerId,
            Pageable pageable) {
        return photoService.getApprovedPhotosByPhotographer(photographerId, pageable);
    }

    // ------------------------------
    // Get Approved and Active Photos
    // ------------------------------
    @GetMapping("/approved-active")
    public Page<PhotoResponseDto> getApprovedAndActive(Pageable pageable) {
        return photoService.getApprovedAndActivePhotos(pageable);
    }

    // ------------------------------
    // Get Featured Photos
    // ------------------------------
    @GetMapping("/featured")
    public Page<PhotoResponseDto> getFeatured(Pageable pageable) {
        return photoService.getFeaturedPhotos(pageable);
    }

    // ------------------------------
    // Get Pending Approval Photos
    // ------------------------------
    @GetMapping("/pending-approval")
    public Page<PhotoResponseDto> getPendingApproval(Pageable pageable) {
        return photoService.getPendingApprovalPhotos(pageable);
    }

    // ------------------------------
    // Get Most Viewed Photos
    // ------------------------------
    @GetMapping("/most-viewed")
    public Page<PhotoResponseDto> getMostViewed(Pageable pageable) {
        return photoService.getMostViewedPhotos(pageable);
    }

    // ------------------------------
    // Get Most Downloaded Photos
    // ------------------------------
    @GetMapping("/most-downloaded")
    public Page<PhotoResponseDto> getMostDownloaded(Pageable pageable) {
        return photoService.getMostDownloadedPhotos(pageable);
    }

    // ------------------------------
    // Get Most Liked Photos
    // ------------------------------
    @GetMapping("/most-liked")
    public Page<PhotoResponseDto> getMostLiked(Pageable pageable) {
        return photoService.getMostLikedPhotos(pageable);
    }

    // ------------------------------
    // Get Recently Uploaded Photos
    // ------------------------------
    @GetMapping("/recent")
    public Page<PhotoResponseDto> getRecent(Pageable pageable) {
        return photoService.getRecentlyUploadedPhotos(pageable);
    }

    // ------------------------------
    // Get Similar Photos
    // ------------------------------
    @GetMapping("/{id}/similar")
    public ResponseEntity<List<PhotoResponseDto>> getSimilar(
            @PathVariable("id") Long id,
            @RequestParam(value = "limit", defaultValue = "10") @Min(1) Integer limit) {
        return ResponseEntity.ok(photoService.getSimilarPhotos(id, limit));
    }

    // ------------------------------
    // Get Photos by Tag
    // ------------------------------
    @GetMapping("/by-tag/{tagName}")
    public Page<PhotoResponseDto> getByTag(
            @PathVariable("tagName") String tagName,
            Pageable pageable) {
        return photoService.getPhotosByTag(tagName, pageable);
    }

    // ------------------------------
    // Get Photos by Category
    // ------------------------------
    @GetMapping("/by-category/{categorySlug}")
    public Page<PhotoResponseDto> getByCategory(
            @PathVariable("categorySlug") String categorySlug,
            Pageable pageable) {
        return photoService.getPhotosByCategory(categorySlug, pageable);
    }

    // ------------------------------
    // Get Photos by Location
    // ------------------------------
    @GetMapping("/by-location")
    public Page<PhotoResponseDto> getByLocation(
            @RequestParam("location") String location,
            Pageable pageable) {
        return photoService.getPhotosByLocation(location, pageable);
    }

    // ------------------------------
    // Get Photos by Price Range
    // ------------------------------
    @GetMapping("/by-price-range")
    public Page<PhotoResponseDto> getByPriceRange(
            @RequestParam("minPrice") BigDecimal minPrice,
            @RequestParam("maxPrice") BigDecimal maxPrice,
            Pageable pageable) {
        return photoService.getPhotosByPriceRange(minPrice, maxPrice, pageable);
    }

    // ------------------------------
    // Get Photos Uploaded Between Dates
    // ------------------------------
    @GetMapping("/uploaded-between")
    public Page<PhotoResponseDto> getUploadedBetween(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable) {
        return photoService.getPhotosUploadedBetween(startDate, endDate, pageable);
    }

    // ------------------------------
    // Search Photos
    // ------------------------------
    @GetMapping("/search")
    public Page<PhotoResponseDto> search(
            @RequestParam("q") String searchTerm,
            Pageable pageable) {
        return photoService.searchPhotos(searchTerm, pageable);
    }

    // ------------------------------
    // Advanced Search
    // ------------------------------
    @PostMapping("/advanced-search")
    public Page<PhotoResponseDto> advancedSearch(
            @Valid @RequestBody PhotoSearchCriteria searchCriteria,
            Pageable pageable) {
        return photoService.advancedSearch(searchCriteria, pageable);
    }

    // ------------------------------
    // Deactivate Photo
    // ------------------------------
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<PhotoResponseDto> deactivate(@PathVariable("id") Long id) {
        return ResponseEntity.ok(photoService.deactivatePhoto(id));
    }

    // ------------------------------
    // Activate Photo
    // ------------------------------
    @PatchMapping("/{id}/activate")
    public ResponseEntity<PhotoResponseDto> activate(@PathVariable("id") Long id) {
        return ResponseEntity.ok(photoService.activatePhoto(id));
    }

    // ------------------------------
    // Approve Photo
    // ------------------------------
    @PatchMapping("/{id}/approve")
    public ResponseEntity<PhotoResponseDto> approve(@PathVariable("id") Long id) {
        return ResponseEntity.ok(photoService.approvePhoto(id));
    }

    // ------------------------------
    // Reject Photo
    // ------------------------------
    @PatchMapping("/{id}/reject")
    public ResponseEntity<PhotoResponseDto> reject(
            @PathVariable("id") Long id,
            @RequestParam("reason") String reason) {
        return ResponseEntity.ok(photoService.rejectPhoto(id, reason));
    }

    // ------------------------------
    // Set Featured Status
    // ------------------------------
    @PatchMapping("/{id}/featured")
    public ResponseEntity<PhotoResponseDto> setFeatured(
            @PathVariable("id") Long id,
            @RequestParam("featured") Boolean featured) {
        return ResponseEntity.ok(photoService.setFeatured(id, featured));
    }

    // ------------------------------
    // Assign Tags
    // ------------------------------
    @PutMapping("/{id}/tags")
    public ResponseEntity<PhotoResponseDto> assignTags(
            @PathVariable("id") Long id,
            @RequestBody List<Long> tagIds) {
        return ResponseEntity.ok(photoService.assignTags(id, tagIds));
    }

    // ------------------------------
    // Add Tag
    // ------------------------------
    @PostMapping("/{id}/tags/{tagId}")
    public ResponseEntity<PhotoResponseDto> addTag(
            @PathVariable("id") Long id,
            @PathVariable("tagId") Long tagId) {
        return ResponseEntity.ok(photoService.addTag(id, tagId));
    }

    // ------------------------------
    // Remove Tag
    // ------------------------------
    @DeleteMapping("/{id}/tags/{tagId}")
    public ResponseEntity<PhotoResponseDto> removeTag(
            @PathVariable("id") Long id,
            @PathVariable("tagId") Long tagId) {
        return ResponseEntity.ok(photoService.removeTag(id, tagId));
    }

    // ------------------------------
    // Assign Categories
    // ------------------------------
    @PutMapping("/{id}/categories")
    public ResponseEntity<PhotoResponseDto> assignCategories(
            @PathVariable("id") Long id,
            @RequestBody List<Long> categoryIds) {
        return ResponseEntity.ok(photoService.assignCategories(id, categoryIds));
    }

    // ------------------------------
    // Add Category
    // ------------------------------
    @PostMapping("/{id}/categories/{categoryId}")
    public ResponseEntity<PhotoResponseDto> addCategory(
            @PathVariable("id") Long id,
            @PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(photoService.addCategory(id, categoryId));
    }

    // ------------------------------
    // Remove Category
    // ------------------------------
    @DeleteMapping("/{id}/categories/{categoryId}")
    public ResponseEntity<PhotoResponseDto> removeCategory(
            @PathVariable("id") Long id,
            @PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(photoService.removeCategory(id, categoryId));
    }

    // ------------------------------
    // Increment View Count
    // ------------------------------
    @PostMapping("/{id}/increment-views")
    public ResponseEntity<PhotoResponseDto> incrementViews(@PathVariable("id") Long id) {
        return ResponseEntity.ok(photoService.incrementViewCount(id));
    }

    // ------------------------------
    // Increment Download Count
    // ------------------------------
    @PostMapping("/{id}/increment-downloads")
    public ResponseEntity<PhotoResponseDto> incrementDownloads(@PathVariable("id") Long id) {
        return ResponseEntity.ok(photoService.incrementDownloadCount(id));
    }

    // ------------------------------
    // Increment Like Count
    // ------------------------------
    @PostMapping("/{id}/like")
    public ResponseEntity<PhotoResponseDto> like(@PathVariable("id") Long id) {
        return ResponseEntity.ok(photoService.incrementLikeCount(id));
    }

    // ------------------------------
    // Decrement Like Count
    // ------------------------------
    @PostMapping("/{id}/unlike")
    public ResponseEntity<PhotoResponseDto> unlike(@PathVariable("id") Long id) {
        return ResponseEntity.ok(photoService.decrementLikeCount(id));
    }

    // ------------------------------
    // Count Photos by Photographer
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/count")
    public ResponseEntity<Long> countByPhotographer(@PathVariable("photographerId") Long photographerId) {
        return ResponseEntity.ok(photoService.countPhotosByPhotographer(photographerId));
    }

    // ------------------------------
    // Count Approved Photos by Photographer
    // ------------------------------
    @GetMapping("/photographer/{photographerId}/count-approved")
    public ResponseEntity<Long> countApprovedByPhotographer(@PathVariable("photographerId") Long photographerId) {
        return ResponseEntity.ok(photoService.countApprovedPhotosByPhotographer(photographerId));
    }

    // ------------------------------
    // Count Pending Approval Photos
    // ------------------------------
    @GetMapping("/count-pending")
    public ResponseEntity<Long> countPending() {
        return ResponseEntity.ok(photoService.countPendingApprovalPhotos());
    }

    // ------------------------------
    // Count Total Photos
    // ------------------------------
    @GetMapping("/count")
    public ResponseEntity<Long> countTotal() {
        return ResponseEntity.ok(photoService.countTotalPhotos());
    }

    // ------------------------------
    // Process Uploaded Photo
    // ------------------------------
    @PostMapping("/{id}/process")
    public ResponseEntity<PhotoResponseDto> process(@PathVariable("id") Long id) {
        return ResponseEntity.ok(photoService.processUploadedPhoto(id));
    }

    // ------------------------------
    // Check Photo Exists
    // ------------------------------
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> exists(@PathVariable("id") Long id) {
        return ResponseEntity.ok(photoService.photoExists(id));
    }

    // ------------------------------
    // Delete Photo
    // ------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        photoService.deletePhoto(id);
        return ResponseEntity.noContent().build();
    }
}
