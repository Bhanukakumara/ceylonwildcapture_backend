package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.PhotoModerationDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for photo moderation operations.
 * Handles approval, rejection, flagging, and visibility management of photos.
 */
public interface PhotoModerationService {

    /**
     * Moderate a photo (approve, reject, flag, etc.).
     *
     * @param moderationDto moderation action details
     * @param moderatorId ID of the moderator performing the action
     * @return moderated photo
     */
    Photo moderatePhoto(PhotoModerationDto moderationDto, Long moderatorId);

    /**
     * Get all photos pending moderation.
     *
     * @param pageable pagination parameters
     * @return page of pending photos
     */
    Page<Photo> getPendingPhotos(Pageable pageable);

    /**
     * Get all flagged photos.
     *
     * @param pageable pagination parameters
     * @return page of flagged photos
     */
    Page<Photo> getFlaggedPhotos(Pageable pageable);

    /**
     * Get all rejected photos.
     *
     * @param pageable pagination parameters
     * @return page of rejected photos
     */
    Page<Photo> getRejectedPhotos(Pageable pageable);

    /**
     * Approve multiple photos in bulk.
     *
     * @param photoIds list of photo IDs to approve
     * @param moderatorId ID of the moderator
     */
    void bulkApprove(java.util.List<Long> photoIds, Long moderatorId);

    /**
     * Reject multiple photos in bulk.
     *
     * @param photoIds list of photo IDs to reject
     * @param reason rejection reason
     * @param moderatorId ID of the moderator
     */
    void bulkReject(java.util.List<Long> photoIds, String reason, Long moderatorId);

    /**
     * Apply watermark to a photo.
     *
     * @param photoId ID of the photo
     * @param adminId
     * @return updated photo
     */
    Photo applyWatermark(Long photoId, Long adminId);

    /**
     * Remove watermark from a photo.
     *
     * @param photoId ID of the photo
     * @param adminId
     * @return updated photo
     */
    Photo removeWatermark(Long photoId, Long adminId);

    /**
     * Feature a photo on the platform.
     *
     * @param photoId ID of the photo
     * @param adminId
     * @return updated photo
     */
    Photo featurePhoto(Long photoId, Long adminId);

    /**
     * Unfeature a photo.
     *
     * @param photoId ID of the photo
     * @param adminId
     * @return updated photo
     */
    Photo unfeaturePhoto(Long photoId, Long adminId);

    /**
     * Soft delete a photo.
     *
     * @param photoId ID of the photo
     * @param moderatorId ID of the moderator
     */
    void softDeletePhoto(Long photoId, Long moderatorId);

    /**
     * Restore a soft-deleted photo.
     *
     * @param photoId ID of the photo
     * @param moderatorId ID of the moderator
     * @return restored photo
     */
    Photo restorePhoto(Long photoId, Long moderatorId);

    /**
     * Get moderation history for a photo.
     *
     * @param photoId ID of the photo
     * @return list of moderation records
     */
    java.util.List<Object> getModerationHistory(Long photoId);

    Photo approvePhoto(Long photoId, Long adminId);

    Photo rejectPhoto(Long photoId, String reason, Long adminId);

    Photo flagPhoto(Long photoId, String reason, Long adminId);

    Photo unflagPhoto(Long photoId, Long adminId);

    Page<Photo> getFeaturedPhotos(Pageable pageable);

    Page<Photo> getPhotographerPhotos(Long photographerId, Pageable pageable);
}
