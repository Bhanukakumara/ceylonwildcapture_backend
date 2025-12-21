package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.dto.PublicStatsDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.dto.TopPhotographerDto;

import java.util.List;

/**
 * Public Stats Service Interface
 * Provides public statistics without requiring authentication
 */
public interface PublicStatsService {

    /**
     * Get public platform statistics
     * 
     * @return PublicStatsDto containing photos, photographers, and categories
     *         counts
     */
    PublicStatsDto getPublicStats();

    /**
     * Get top photographers by photo count
     * 
     * @param limit number of photographers to return
     * @return List of top photographers with their photo counts
     */
    List<TopPhotographerDto> getTopPhotographers(int limit);
}
