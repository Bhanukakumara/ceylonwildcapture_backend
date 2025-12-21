package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.UserRole;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.repository.PhotoRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.repository.CategoryRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.repository.UserRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.dto.PublicStatsDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.dto.TopPhotographerDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.service.PublicStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Public Stats Service Implementation
 * Provides public statistics without requiring authentication
 */
@Service
@RequiredArgsConstructor
public class PublicStatsServiceImpl implements PublicStatsService {

    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    /**
     * Get public platform statistics
     * 
     * @return PublicStatsDto containing photos, photographers, and categories
     *         counts
     */
    @Override
    public PublicStatsDto getPublicStats() {
        // Count total photos
        Long totalPhotos = photoRepository.count();

        // Count photographers (users with PHOTOGRAPHER role)
        Long totalPhotographers = userRepository.countByRole(UserRole.PHOTOGRAPHER);

        // Count total categories
        Long totalCategories = categoryRepository.count();

        return PublicStatsDto.builder()
                .totalPhotos(totalPhotos)
                .totalPhotographers(totalPhotographers)
                .totalCategories(totalCategories)
                .build();
    }

    /**
     * Get top photographers by photo count
     * 
     * @param limit number of photographers to return
     * @return List of top photographers with their photo counts
     */
    @Override
    public List<TopPhotographerDto> getTopPhotographers(int limit) {
        Pageable pageable = PageRequest.of(0, limit);

        // Get photographers with most photos
        List<User> photographers = userRepository.findByRole(UserRole.PHOTOGRAPHER, pageable).getContent();

        return photographers.stream()
                .map(user -> {
                    long photoCount = photoRepository.countByPhotographerId(user.getId());
                    return TopPhotographerDto.builder()
                            .id(user.getId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .email(user.getEmail())
                            .profileImageUrl(user.getProfileImageUrl())
                            .photoCount(photoCount)
                            .build();
                })
                .filter(dto -> dto.getPhotoCount() > 0) // Only include photographers with photos
                .sorted((a, b) -> Long.compare(b.getPhotoCount(), a.getPhotoCount())) // Sort by photo count descending
                .limit(limit)
                .collect(Collectors.toList());
    }
}
