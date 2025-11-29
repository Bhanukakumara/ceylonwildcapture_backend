package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.entity.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for SystemConfig entity.
 */
@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, String> {

    /**
     * Find configuration by key.
     *
     * @param key configuration key
     * @return optional configuration
     */
    Optional<SystemConfig> findByKey(String key);

    /**
     * Find all public configurations.
     *
     * @return list of public configurations
     */
    List<SystemConfig> findByIsPublicTrue();

    /**
     * Find configurations by category.
     *
     * @param category configuration category
     * @return list of configurations
     */
    List<SystemConfig> findByCategory(String category);

    /**
     * Find all distinct categories.
     *
     * @return list of categories
     */
    @Query("SELECT DISTINCT s.category FROM SystemConfig s WHERE s.category IS NOT NULL")
    List<String> findDistinctCategories();

    /**
     * Check if configuration exists by key.
     *
     * @param key configuration key
     * @return true if exists
     */
    boolean existsByKey(String key);
}
