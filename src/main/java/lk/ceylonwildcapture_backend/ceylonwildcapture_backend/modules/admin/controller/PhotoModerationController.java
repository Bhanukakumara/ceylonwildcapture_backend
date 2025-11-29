package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.PhotoModerationDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.PhotoModerationService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * REST controller for photo moderation operations.
 */
@RestController
@RequestMapping("/api/v1/admin/moderation")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class PhotoModerationController {

    private final PhotoModerationService photoModerationService;

    @PostMapping
    public ResponseEntity<Photo> moderatePhoto(
            @Valid @RequestBody PhotoModerationDto moderationDto,
            @RequestAttribute("userId") Long adminId) {
        Photo moderatedPhoto = photoModerationService.moderatePhoto(moderationDto, adminId);
        return ResponseEntity.ok(moderatedPhoto);
    }

    @PostMapping("/{photoId}/approve")
    public ResponseEntity<Photo> approvePhoto(
            @PathVariable Long photoId,
            @RequestAttribute("userId") Long adminId) {
        Photo approvedPhoto = photoModerationService.approvePhoto(photoId, adminId);
        return ResponseEntity.ok(approvedPhoto);
    }

    @PostMapping("/{photoId}/reject")
    public ResponseEntity<Photo> rejectPhoto(
            @PathVariable Long photoId,
            @RequestParam String reason,
            @RequestAttribute("userId") Long adminId) {
        Photo rejectedPhoto = photoModerationService.rejectPhoto(photoId, reason, adminId);
        return ResponseEntity.ok(rejectedPhoto);
    }

    @PostMapping("/{photoId}/flag")
    public ResponseEntity<Photo> flagPhoto(
            @PathVariable Long photoId,
            @RequestParam String reason,
            @RequestAttribute("userId") Long adminId) {
        Photo flaggedPhoto = photoModerationService.flagPhoto(photoId, reason, adminId);
        return ResponseEntity.ok(flaggedPhoto);
    }

    @PostMapping("/{photoId}/unflag")
    public ResponseEntity<Photo> unflagPhoto(
            @PathVariable Long photoId,
            @RequestAttribute("userId") Long adminId) {
        Photo unflaggedPhoto = photoModerationService.unflagPhoto(photoId, adminId);
        return ResponseEntity.ok(unflaggedPhoto);
    }

    @PostMapping("/{photoId}/feature")
    public ResponseEntity<Photo> featurePhoto(
            @PathVariable Long photoId,
            @RequestAttribute("userId") Long adminId) {
        Photo featuredPhoto = photoModerationService.featurePhoto(photoId, adminId);
        return ResponseEntity.ok(featuredPhoto);
    }

    @PostMapping("/{photoId}/unfeature")
    public ResponseEntity<Photo> unfeaturePhoto(
            @PathVariable Long photoId,
            @RequestAttribute("userId") Long adminId) {
        Photo unfeaturedPhoto = photoModerationService.unfeaturePhoto(photoId, adminId);
        return ResponseEntity.ok(unfeaturedPhoto);
    }

    @PostMapping("/{photoId}/watermark")
    public ResponseEntity<Photo> applyWatermark(
            @PathVariable Long photoId,
            @RequestAttribute("userId") Long adminId) {
        Photo watermarkedPhoto = photoModerationService.applyWatermark(photoId, adminId);
        return ResponseEntity.ok(watermarkedPhoto);
    }

    @PostMapping("/{photoId}/remove-watermark")
    public ResponseEntity<Photo> removeWatermark(
            @PathVariable Long photoId,
            @RequestAttribute("userId") Long adminId) {
        Photo photo = photoModerationService.removeWatermark(photoId, adminId);
        return ResponseEntity.ok(photo);
    }

    @GetMapping("/pending")
    public ResponseEntity<Page<Photo>> getPendingPhotos(Pageable pageable) {
        Page<Photo> pendingPhotos = photoModerationService.getPendingPhotos(pageable);
        return ResponseEntity.ok(pendingPhotos);
    }

    @GetMapping("/flagged")
    public ResponseEntity<Page<Photo>> getFlaggedPhotos(Pageable pageable) {
        Page<Photo> flaggedPhotos = photoModerationService.getFlaggedPhotos(pageable);
        return ResponseEntity.ok(flaggedPhotos);
    }

    @GetMapping("/rejected")
    public ResponseEntity<Page<Photo>> getRejectedPhotos(Pageable pageable) {
        Page<Photo> rejectedPhotos = photoModerationService.getRejectedPhotos(pageable);
        return ResponseEntity.ok(rejectedPhotos);
    }

    @GetMapping("/featured")
    public ResponseEntity<Page<Photo>> getFeaturedPhotos(Pageable pageable) {
        Page<Photo> featuredPhotos = photoModerationService.getFeaturedPhotos(pageable);
        return ResponseEntity.ok(featuredPhotos);
    }

    @GetMapping("/photographer/{photographerId}")
    public ResponseEntity<Page<Photo>> getPhotographerPhotos(
            @PathVariable Long photographerId,
            Pageable pageable) {
        Page<Photo> photos = photoModerationService.getPhotographerPhotos(photographerId, pageable);
        return ResponseEntity.ok(photos);
    }
}
