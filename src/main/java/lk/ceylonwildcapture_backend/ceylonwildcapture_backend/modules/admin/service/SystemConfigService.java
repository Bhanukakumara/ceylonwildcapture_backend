package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.SystemConfigDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service interface for system configuration management.
 */
public interface SystemConfigService {

    /**
     * Create or update a system configuration.
     *
     * @param configDto configuration DTO
     * @param adminId ID of the admin
     * @return saved configuration
     */
    SystemConfigDto saveConfig(SystemConfigDto configDto, Long adminId);

    /**
     * Get a configuration value by key.
     *
     * @param key configuration key
     * @return configuration value
     */
    Optional<String> getConfigValue(String key);

    /**
     * Get a configuration by key.
     *
     * @param key configuration key
     * @return configuration DTO
     */
    Optional<SystemConfigDto> getConfig(String key);

    /**
     * Get all configurations.
     *
     * @return list of all configurations
     */
    List<SystemConfigDto> getAllConfigs();

    /**
     * Get public configurations (accessible to all users).
     *
     * @return list of public configurations
     */
    List<SystemConfigDto> getPublicConfigs();

    /**
     * Get configurations by category.
     *
     * @param category configuration category
     * @return list of configurations in category
     */
    List<SystemConfigDto> getConfigsByCategory(String category);

    /**
     * Delete a configuration.
     *
     * @param key configuration key
     * @param adminId ID of the admin
     */
    void deleteConfig(String key, Long adminId);

    /**
     * Bulk update configurations.
     *
     * @param configs map of key-value pairs
     * @param adminId ID of the admin
     */
    void bulkUpdateConfigs(Map<String, String> configs, Long adminId);

    /**
     * Reset configuration to default value.
     *
     * @param key configuration key
     * @param adminId ID of the admin
     * @return reset configuration
     */
    SystemConfigDto resetToDefault(String key, Long adminId);

    /**
     * Get all configuration categories.
     *
     * @return list of categories
     */
    List<String> getAllCategories();
}
