package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.dto.PublicStatsDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.dto.TopPhotographerDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.service.PublicStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Public Stats Controller
 * Provides public statistics without requiring authentication
 */
@RestController
@RequestMapping("/api/v1/public/stats")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PublicStatsController {

    private final PublicStatsService publicStatsService;

    /**
     * Get public platform statistics
     * 
     * @return PublicStatsDto containing photos, photographers, and categories
     *         counts
     */
    @GetMapping
    public ResponseEntity<PublicStatsDto> getPublicStats() {
        PublicStatsDto stats = publicStatsService.getPublicStats();
        return ResponseEntity.ok(stats);
    }

    /**
     * Get top photographers by photo count
     * 
     * @param limit number of photographers to return (default: 4)
     * @return List of top photographers with their photo counts
     */
    @GetMapping("/top-photographers")
    public ResponseEntity<List<TopPhotographerDto>> getTopPhotographers(
            @RequestParam(defaultValue = "4") int limit) {
        List<TopPhotographerDto> topPhotographers = publicStatsService.getTopPhotographers(limit);
        return ResponseEntity.ok(topPhotographers);
    }
}
