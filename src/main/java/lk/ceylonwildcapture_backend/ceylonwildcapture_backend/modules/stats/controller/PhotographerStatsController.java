package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.dto.PhotographerStatsDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.service.PhotographerStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Photographer Stats Controller
 * Provides statistics for photographer dashboards
 */
@RestController
@RequestMapping("/api/v1/photographer/stats")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PhotographerStatsController {

    private final PhotographerStatsService photographerStatsService;

    /**
     * Get statistics for a specific photographer
     * 
     * @param userId the ID of the user (photographer)
     * @return PhotographerStatsDto containing photos, earnings, sales, and rating
     */
    @GetMapping("/{userId}")
    public ResponseEntity<PhotographerStatsDto> getPhotographerStats(@PathVariable Long userId) {
        PhotographerStatsDto stats = photographerStatsService.getPhotographerStats(userId);
        return ResponseEntity.ok(stats);
    }
}
