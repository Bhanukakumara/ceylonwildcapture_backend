package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Category management operations.
 * Defines business logic for category CRUD, ordering, and category-based queries.
 */
public interface CategoryService {

    /**
     * Create a new category.
     *
     * @param category the category entity to create
     * @return the created category
     * @throws IllegalArgumentException if category data is invalid or category already exists
     */
    Category createCategory(Category category);

    /**
     * Create a category with basic information.
     *
     * @param name the category name
     * @param description the category description
     * @param slug the category slug
     * @return the created category
     * @throws IllegalArgumentException if category name or slug already exists
     */
    Category createCategory(String name, String description, String slug);

    /**
     * Update an existing category.
     *
     * @param categoryId the category ID
     * @param category the updated category data
     * @return the updated category
     * @throws IllegalArgumentException if category not found
     */
    Category updateCategory(Long categoryId, Category category);

    /**
     * Update category name and description.
     *
     * @param categoryId the category ID
     * @param name the new name
     * @param description the new description
     * @return the updated category
     * @throws IllegalArgumentException if category not found
     */
    Category updateCategoryInfo(Long categoryId, String name, String description);

    /**
     * Update category image.
     *
     * @param categoryId the category ID
     * @param imageUrl the new image URL
     * @return the updated category
     * @throws IllegalArgumentException if category not found
     */
    Category updateCategoryImage(Long categoryId, String imageUrl);

    /**
     * Update category display order.
     *
     * @param categoryId the category ID
     * @param displayOrder the new display order
     * @return the updated category
     * @throws IllegalArgumentException if category not found
     */
    Category updateDisplayOrder(Long categoryId, Integer displayOrder);

    /**
     * Get category by ID.
     *
     * @param categoryId the category ID
     * @return Optional containing the category if found
     */
    Optional<Category> getCategoryById(Long categoryId);

    /**
     * Get category by slug.
     *
     * @param slug the category slug
     * @return Optional containing the category if found
     */
    Optional<Category> getCategoryBySlug(String slug);

    /**
     * Get category by name.
     *
     * @param name the category name
     * @return Optional containing the category if found
     */
    Optional<Category> getCategoryByName(String name);

    /**
     * Get all categories with pagination.
     *
     * @param pageable pagination information
     * @return page of categories
     */
    Page<Category> getAllCategories(Pageable pageable);

    /**
     * Get all active categories.
     *
     * @return list of active categories
     */
    List<Category> getActiveCategories();

    /**
     * Get active categories with pagination.
     *
     * @param pageable pagination information
     * @return page of active categories
     */
    Page<Category> getActiveCategories(Pageable pageable);

    /**
     * Get categories ordered by display order.
     *
     * @return list of categories ordered by display order
     */
    List<Category> getCategoriesOrderedByDisplayOrder();

    /**
     * Get active categories ordered by display order.
     *
     * @return list of active categories ordered by display order
     */
    List<Category> getActiveCategoriesOrderedByDisplayOrder();

    /**
     * Search categories by name or description.
     *
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of matching categories
     */
    Page<Category> searchCategories(String searchTerm, Pageable pageable);

    /**
     * Get categories by IDs.
     *
     * @param categoryIds the list of category IDs
     * @return list of categories
     */
    List<Category> getCategoriesByIds(List<Long> categoryIds);

    /**
     * Get categories by slugs.
     *
     * @param slugs the list of category slugs
     * @return list of categories
     */
    List<Category> getCategoriesBySlugs(List<String> slugs);

    /**
     * Delete category.
     *
     * @param categoryId the category ID
     * @throws IllegalArgumentException if category not found
     */
    void deleteCategory(Long categoryId);

    /**
     * Activate category.
     *
     * @param categoryId the category ID
     * @return the activated category
     * @throws IllegalArgumentException if category not found
     */
    Category activateCategory(Long categoryId);

    /**
     * Deactivate category.
     *
     * @param categoryId the category ID
     * @return the deactivated category
     * @throws IllegalArgumentException if category not found
     */
    Category deactivateCategory(Long categoryId);

    /**
     * Check if category exists by name.
     *
     * @param name the category name
     * @return true if category exists
     */
    boolean categoryExistsByName(String name);

    /**
     * Check if category exists by slug.
     *
     * @param slug the category slug
     * @return true if category exists
     */
    boolean categoryExistsBySlug(String slug);

    /**
     * Generate unique slug from category name.
     *
     * @param name the category name
     * @return the generated slug
     */
    String generateSlug(String name);

    /**
     * Reorder categories.
     *
     * @param categoryOrders map of category ID to display order
     * @return list of updated categories
     */
    List<Category> reorderCategories(List<Object> categoryOrders);

    /**
     * Get empty categories (no photos).
     *
     * @return list of empty categories
     */
    List<Category> getEmptyCategories();

    /**
     * Get categories with photo count.
     *
     * @param pageable pagination information
     * @return page of categories with photo counts
     */
    Page<Object[]> getCategoriesWithPhotoCount(Pageable pageable);

    /**
     * Count active categories.
     *
     * @return count of active categories
     */
    long countActiveCategories();

    /**
     * Count total categories.
     *
     * @return total count of categories
     */
    long countTotalCategories();
}
