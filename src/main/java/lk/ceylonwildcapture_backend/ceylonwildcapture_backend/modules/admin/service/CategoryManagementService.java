package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for category and tag management.
 */
public interface CategoryManagementService {

    /**
     * Create a new category.
     *
     * @param category category entity
     * @param adminId ID of the admin
     * @return created category
     */
    Category createCategory(Category category, Long adminId);

    /**
     * Update a category.
     *
     * @param categoryId ID of the category
     * @param category updated category data
     * @param adminId ID of the admin
     * @return updated category
     */
    Category updateCategory(Long categoryId, Category category, Long adminId);

    /**
     * Delete a category.
     *
     * @param categoryId ID of the category
     * @param adminId ID of the admin
     */
    void deleteCategory(Long categoryId, Long adminId);

    /**
     * Get all categories.
     *
     * @param pageable pagination parameters
     * @return page of categories
     */
    Page<Category> getAllCategories(Pageable pageable);

    /**
     * Create a new tag.
     *
     * @param tag tag entity
     * @param adminId ID of the admin
     * @return created tag
     */
    Tag createTag(Tag tag, Long adminId);

    /**
     * Update a tag.
     *
     * @param tagId ID of the tag
     * @param tag updated tag data
     * @param adminId ID of the admin
     * @return updated tag
     */
    Tag updateTag(Long tagId, Tag tag, Long adminId);

    /**
     * Delete a tag.
     *
     * @param tagId ID of the tag
     * @param adminId ID of the admin
     */
    void deleteTag(Long tagId, Long adminId);

    /**
     * Get all tags.
     *
     * @param pageable pagination parameters
     * @return page of tags
     */
    Page<Tag> getAllTags(Pageable pageable);

    /**
     * Merge two categories.
     *
     * @param sourceCategoryId source category ID
     * @param targetCategoryId target category ID
     * @param adminId ID of the admin
     * @return merged category
     */
    Category mergeCategories(Long sourceCategoryId, Long targetCategoryId, Long adminId);

    /**
     * Merge two tags.
     *
     * @param sourceTagId source tag ID
     * @param targetTagId target tag ID
     * @param adminId ID of the admin
     * @return merged tag
     */
    Tag mergeTags(Long sourceTagId, Long targetTagId, Long adminId);

    /**
     * Get unused categories (categories with no photos).
     *
     * @return list of unused categories
     */
    List<Category> getUnusedCategories();

    /**
     * Get unused tags (tags with no photos).
     *
     * @return list of unused tags
     */
    List<Tag> getUnusedTags();

    /**
     * Delete all unused categories.
     *
     * @param adminId ID of the admin
     * @return number of deleted categories
     */
    int deleteUnusedCategories(Long adminId);

    /**
     * Delete all unused tags.
     *
     * @param adminId ID of the admin
     * @return number of deleted tags
     */
    int deleteUnusedTags(Long adminId);
}
