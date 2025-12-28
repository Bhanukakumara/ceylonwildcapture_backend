package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.repository.PhotoRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.dto.PhotographerStatsDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.service.PhotographerStatsService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.PhotographerProfile;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.repository.PhotographerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Photographer Stats Service Implementation
 * Provides statistics for photographer dashboards
 */
@Service
@RequiredArgsConstructor
public class PhotographerStatsServiceImpl implements PhotographerStatsService {

    private final PhotoRepository photoRepository;
    private final PhotographerProfileRepository photographerProfileRepository;

    @Override
    public PhotographerStatsDto getPhotographerStats(Long userId) {
        // Count total photos by photographer
        Long totalPhotos = photoRepository.countByPhotographerId(userId);

        // Get photographer profile for earnings, sales, and rating
        PhotographerProfile profile = photographerProfileRepository.findByUserId(userId)
                .orElse(null);

        BigDecimal totalEarnings = BigDecimal.ZERO;
        Integer totalSales = 0;
        BigDecimal averageRating = BigDecimal.ZERO;

        if (profile != null) {
            totalEarnings = profile.getTotalEarnings() != null ? profile.getTotalEarnings() : BigDecimal.ZERO;
            totalSales = profile.getTotalSales() != null ? profile.getTotalSales() : 0;
            averageRating = profile.getRating() != null ? profile.getRating() : BigDecimal.ZERO;
        }

        return PhotographerStatsDto.builder()
                .totalPhotos(totalPhotos)
                .totalEarnings(totalEarnings)
                .totalSales(totalSales)
                .averageRating(averageRating)
                .build();
    }
}
