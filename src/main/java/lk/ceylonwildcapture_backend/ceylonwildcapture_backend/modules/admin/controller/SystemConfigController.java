package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.SystemConfigDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.SystemConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * REST controller for system configuration management.
 */
@RestController
@RequestMapping("/api/v1/admin/config")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class SystemConfigController {

    private final SystemConfigService systemConfigService;

    @PostMapping
    public ResponseEntity<SystemConfigDto> saveConfig(
            @Valid @RequestBody SystemConfigDto configDto,
            @RequestAttribute("userId") Long adminId) {
        SystemConfigDto savedConfig = systemConfigService.saveConfig(configDto, adminId);
        return ResponseEntity.ok(savedConfig);
    }

    @GetMapping("/{key}")
    public ResponseEntity<SystemConfigDto> getConfig(@PathVariable String key) {
        return systemConfigService.getConfig(key)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{key}/value")
    public ResponseEntity<String> getConfigValue(@PathVariable String key) {
        return systemConfigService.getConfigValue(key)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SystemConfigDto>> getAllConfigs() {
        List<SystemConfigDto> configs = systemConfigService.getAllConfigs();
        return ResponseEntity.ok(configs);
    }

    @GetMapping("/public")
    public ResponseEntity<List<SystemConfigDto>> getPublicConfigs() {
        List<SystemConfigDto> configs = systemConfigService.getPublicConfigs();
        return ResponseEntity.ok(configs);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<SystemConfigDto>> getConfigsByCategory(@PathVariable String category) {
        List<SystemConfigDto> configs = systemConfigService.getConfigsByCategory(category);
        return ResponseEntity.ok(configs);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = systemConfigService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> deleteConfig(
            @PathVariable String key,
            @RequestAttribute("userId") Long adminId) {
        systemConfigService.deleteConfig(key, adminId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/bulk")
    public ResponseEntity<Void> bulkUpdateConfigs(
            @RequestBody Map<String, String> configs,
            @RequestAttribute("userId") Long adminId) {
        systemConfigService.bulkUpdateConfigs(configs, adminId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{key}/reset")
    public ResponseEntity<SystemConfigDto> resetToDefault(
            @PathVariable String key,
            @RequestAttribute("userId") Long adminId) {
        SystemConfigDto resetConfig = systemConfigService.resetToDefault(key, adminId);
        return ResponseEntity.ok(resetConfig);
    }
}
