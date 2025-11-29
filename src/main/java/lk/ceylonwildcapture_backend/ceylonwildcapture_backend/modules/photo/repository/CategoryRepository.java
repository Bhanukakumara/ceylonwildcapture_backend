package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Category entity.
 * Provides database operations for category management and photo categorization.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Find category by name.
     *
     * @param name the category name
     * @return Optional containing the category if found
     */
    Optional<Category> findByName(String name);

    /**
     * Find category by name (case-insensitive).
     *
     * @param name the category name
     * @return Optional containing the category if found
     */
    Optional<Category> findByNameIgnoreCase(String name);

    /**
     * Find category by slug.
     *
     * @param slug the category slug
     * @return Optional containing the category if found
     */
    Optional<Category> findBySlug(String slug);

    /**
     * Check if category exists by name.
     *
     * @param name the category name
     * @return true if category exists
     */
    boolean existsByName(String name);

    /**
     * Check if category exists by slug.
     *
     * @param slug the category slug
     * @return true if category exists
     */
    boolean existsBySlug(String slug);

    /**
     * Find active categories.
     *
     * @param isActive the active status
     * @param pageable pagination information
     * @return page of active categories
     */
    Page<Category> findByIsActive(Boolean isActive, Pageable pageable);

    /**
     * Find all active categories.
     *
     * @param isActive the active status
     * @return list of active categories
     */
    List<Category> findByIsActive(Boolean isActive);

    /**
     * Find categories ordered by display order.
     *
     * @return list of categories ordered by display order
     */
    List<Category> findAllByOrderByDisplayOrderAsc();

    /**
     * Find active categories ordered by display order.
     *
     * @param isActive the active status
     * @return list of active categories ordered by display order
     */
    List<Category> findByIsActiveOrderByDisplayOrderAsc(Boolean isActive);

    /**
     * Find categories by name containing search term.
     *
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of matching categories
     */
    Page<Category> findByNameContainingIgnoreCase(String searchTerm, Pageable pageable);

    /**
     * Find categories by description containing search term.
     *
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of matching categories
     */
    Page<Category> findByDescriptionContainingIgnoreCase(String searchTerm, Pageable pageable);

    /**
     * Search categories by name or description.
     *
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of matching categories
     */
    @Query("SELECT c FROM Category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(c.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Category> search(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Find categories by IDs.
     *
     * @param ids the list of category IDs
     * @return list of categories
     */
    List<Category> findByIdIn(List<Long> ids);

    /**
     * Find categories by slugs.
     *
     * @param slugs the list of category slugs
     * @return list of categories
     */
    List<Category> findBySlugIn(List<String> slugs);

    /**
     * Find recently created categories.
     *
     * @param pageable pagination information
     * @return page of recent categories
     */
    Page<Category> findAllByOrderByCreatedAtDesc(Pageable pageable);

    /**
     * Find recently updated categories.
     *
     * @param pageable pagination information
     * @return page of recently updated categories
     */
    Page<Category> findAllByOrderByUpdatedAtDesc(Pageable pageable);

    /**
     * Count active categories.
     *
     * @param isActive the active status
     * @return count of active categories
     */
    long countByIsActive(Boolean isActive);

    /**
     * Find categories with photos count.
     *
     * @param pageable pagination information
     * @return page of categories with photo counts
     */
    @Query("SELECT c, COUNT(p) as photoCount FROM Category c LEFT JOIN c.photos p " +
           "GROUP BY c ORDER BY photoCount DESC")
    Page<Object[]> findCategoriesWithPhotoCount(Pageable pageable);

    /**
     * Find categories with no photos.
     *
     * @return list of empty categories
     */
    @Query("SELECT c FROM Category c WHERE SIZE(c.photos) = 0")
    List<Category> findEmptyCategories();
}
