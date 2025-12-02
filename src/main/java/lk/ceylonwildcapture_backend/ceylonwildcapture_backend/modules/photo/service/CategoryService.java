package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryCreateDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryUpdateDto;
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
     * @param category the category creation data
     * @return the created category response DTO
     * @throws IllegalArgumentException if category data is invalid or category already exists
     */
    CategoryResponseDto createCategory(CategoryCreateDto category);

    /**
     * Update an existing category.
     *
     * @param categoryId the category ID
     * @param category the updated category data
     * @return the updated category response DTO
     * @throws IllegalArgumentException if category not found
     */
    CategoryResponseDto updateCategory(Long categoryId, CategoryUpdateDto category);

    /**
     * Update category name and description.
     *
     * @param categoryId the category ID
     * @param name the new name
     * @param description the new description
     * @return the updated category response DTO
     * @throws IllegalArgumentException if category not found
     */
    CategoryResponseDto updateCategoryInfo(Long categoryId, String name, String description);

    /**
     * Update category image.
     *
     * @param categoryId the category ID
     * @param imageUrl the new image URL
     * @return the updated category response DTO
     * @throws IllegalArgumentException if category not found
     */
    CategoryResponseDto updateCategoryImage(Long categoryId, String imageUrl);

    /**
     * Update category display order.
     *
     * @param categoryId the category ID
     * @param displayOrder the new display order
     * @return the updated category response DTO
     * @throws IllegalArgumentException if category not found
     */
    CategoryResponseDto updateDisplayOrder(Long categoryId, Integer displayOrder);

    /**
     * Get category by ID.
     *
     * @param categoryId the category ID
     * @return Optional containing the category response DTO if found
     */
    Optional<CategoryResponseDto> getCategoryById(Long categoryId);

    /**
     * Get category by slug.
     *
     * @param slug the category slug
     * @return Optional containing the category response DTO if found
     */
    Optional<CategoryResponseDto> getCategoryBySlug(String slug);

    /**
     * Get category by name.
     *
     * @param name the category name
     * @return Optional containing the category response DTO if found
     */
    Optional<CategoryResponseDto> getCategoryByName(String name);

    /**
     * Get all categories with pagination.
     *
     * @param pageable pagination information
     * @return page of category response DTOs
     */
    Page<CategoryResponseDto> getAllCategories(Pageable pageable);

    /**
     * Get all active categories.
     *
     * @return list of active category response DTOs
     */
    List<CategoryResponseDto> getActiveCategories();

    /**
     * Get active categories with pagination.
     *
     * @param pageable pagination information
     * @return page of active category response DTOs
     */
    Page<CategoryResponseDto> getActiveCategories(Pageable pageable);

    /**
     * Get categories ordered by display order.
     *
     * @return list of category response DTOs ordered by display order
     */
    List<CategoryResponseDto> getCategoriesOrderedByDisplayOrder();

    /**
     * Get active categories ordered by display order.
     *
     * @return list of active category response DTOs ordered by display order
     */
    List<CategoryResponseDto> getActiveCategoriesOrderedByDisplayOrder();

    /**
     * Search categories by name or description.
     *
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of matching category response DTOs
     */
    Page<CategoryResponseDto> searchCategories(String searchTerm, Pageable pageable);

    /**
     * Get categories by IDs.
     *
     * @param categoryIds the list of category IDs
     * @return list of category response DTOs
     */
    List<CategoryResponseDto> getCategoriesByIds(List<Long> categoryIds);

    /**
     * Get categories by slugs.
     *
     * @param slugs the list of category slugs
     * @return list of category response DTOs
     */
    List<CategoryResponseDto> getCategoriesBySlugs(List<String> slugs);

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
     * @return the activated category response DTO
     * @throws IllegalArgumentException if category not found
     */
    CategoryResponseDto activateCategory(Long categoryId);

    /**
     * Deactivate category.
     *
     * @param categoryId the category ID
     * @return the deactivated category response DTO
     * @throws IllegalArgumentException if category not found
     */
    CategoryResponseDto deactivateCategory(Long categoryId);

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
     * @return list of updated category response DTOs
     */
    List<CategoryResponseDto> reorderCategories(List<Object> categoryOrders);

    /**
     * Get empty categories (no photos).
     *
     * @return list of empty category response DTOs
     */
    List<CategoryResponseDto> getEmptyCategories();

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
