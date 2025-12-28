package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.dto.PhotographerStatsDto;

/**
 * Photographer Stats Service Interface
 * Provides statistics for photographer dashboards
 */
public interface PhotographerStatsService {

    /**
     * Get statistics for a specific photographer
     * 
     * @param userId the ID of the user (photographer)
     * @return PhotographerStatsDto containing photos, earnings, sales, and rating
     */
    PhotographerStatsDto getPhotographerStats(Long userId);
}
