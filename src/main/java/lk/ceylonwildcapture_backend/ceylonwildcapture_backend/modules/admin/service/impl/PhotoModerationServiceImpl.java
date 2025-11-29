package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.PhotoModerationDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.PhotoModerationService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Stub implementation of PhotoModerationService.
 * TODO: Implement actual business logic
 */
@Service
public class PhotoModerationServiceImpl implements PhotoModerationService {

    @Override
    public Photo moderatePhoto(PhotoModerationDto moderationDto, Long moderatorId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Page<Photo> getPendingPhotos(Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public Page<Photo> getFlaggedPhotos(Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public Page<Photo> getRejectedPhotos(Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public void bulkApprove(List<Long> photoIds, Long moderatorId) {
        // TODO: Implement actual business logic
    }

    @Override
    public void bulkReject(List<Long> photoIds, String reason, Long moderatorId) {
        // TODO: Implement actual business logic
    }

    @Override
    public Photo applyWatermark(Long photoId, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Photo removeWatermark(Long photoId, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Photo featurePhoto(Long photoId, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Photo unfeaturePhoto(Long photoId, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void softDeletePhoto(Long photoId, Long moderatorId) {
        // TODO: Implement actual business logic
    }

    @Override
    public Photo restorePhoto(Long photoId, Long moderatorId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Object> getModerationHistory(Long photoId) {
        // TODO: Implement actual business logic
        return Collections.emptyList();
    }

    @Override
    public Photo approvePhoto(Long photoId, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Photo rejectPhoto(Long photoId, String reason, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Photo flagPhoto(Long photoId, String reason, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Photo unflagPhoto(Long photoId, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Page<Photo> getFeaturedPhotos(Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public Page<Photo> getPhotographerPhotos(Long photographerId, Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }
}
