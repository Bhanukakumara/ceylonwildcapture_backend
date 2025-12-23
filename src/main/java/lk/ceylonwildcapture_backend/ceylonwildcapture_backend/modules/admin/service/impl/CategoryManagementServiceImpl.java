package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.AuditLogService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.CategoryManagementService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Photo;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Tag;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.repository.CategoryRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of CategoryManagementService for managing categories and tags.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryManagementServiceImpl implements CategoryManagementService {

    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final AuditLogService auditLogService;

    @Override
    @Transactional
    public Category createCategory(Category category, Long adminId) {
        log.info("Creating category: {} by admin: {}", category.getName(), adminId);

        // Check if category with same name already exists
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category with name '" + category.getName() + "' already exists");
        }

        // Generate slug if not provided
        if (category.getSlug() == null || category.getSlug().isEmpty()) {
            category.setSlug(generateSlug(category.getName()));
        }

        // Ensure slug is unique
        if (categoryRepository.existsBySlug(category.getSlug())) {
            category.setSlug(category.getSlug() + "-" + System.currentTimeMillis());
        }

        Category savedCategory = categoryRepository.save(category);

        // Create audit log
        auditLogService.createAuditLog(
                "CATEGORY_CREATE",
                "CATEGORY",
                savedCategory.getId(),
                adminId,
                "CREATE",
                "Created new category: " + savedCategory.getName(),
                null);

        log.info("Category created successfully: {}", savedCategory.getId());
        return savedCategory;
    }

    @Override
    @Transactional
    public Category updateCategory(Long categoryId, Category category, Long adminId) {
        log.info("Updating category: {} by admin: {}", categoryId, adminId);

        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));

        // Check if name is being changed and if new name already exists
        if (!existingCategory.getName().equals(category.getName()) &&
                categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category with name '" + category.getName() + "' already exists");
        }

        // Update fields
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setImageUrl(category.getImageUrl());
        existingCategory.setIsActive(category.getIsActive());
        existingCategory.setDisplayOrder(category.getDisplayOrder());

        // Update slug if name changed
        if (!existingCategory.getSlug().equals(generateSlug(category.getName()))) {
            String newSlug = generateSlug(category.getName());
            if (!categoryRepository.existsBySlug(newSlug) || existingCategory.getSlug().equals(newSlug)) {
                existingCategory.setSlug(newSlug);
            }
        }

        Category updatedCategory = categoryRepository.save(existingCategory);

        // Create audit log
        auditLogService.createAuditLog(
                "CATEGORY_UPDATE",
                "CATEGORY",
                updatedCategory.getId(),
                adminId,
                "UPDATE",
                "Updated category: " + updatedCategory.getName(),
                null);

        log.info("Category updated successfully: {}", categoryId);
        return updatedCategory;
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId, Long adminId) {
        log.info("Deleting category: {} by admin: {}", categoryId, adminId);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));

        // Check if category has photos
        if (!category.getPhotos().isEmpty()) {
            throw new IllegalStateException("Cannot delete category with " + category.getPhotos().size() + " photos");
        }

        String categoryName = category.getName();
        categoryRepository.delete(category);

        // Create audit log
        auditLogService.createAuditLog(
                "CATEGORY_DELETE",
                "CATEGORY",
                categoryId,
                adminId,
                "DELETE",
                "Deleted category: " + categoryName,
                null);

        log.info("Category deleted successfully: {}", categoryId);
    }

    @Override
    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Tag createTag(Tag tag, Long adminId) {
        log.info("Creating tag: {} by admin: {}", tag.getName(), adminId);

        // Check if tag with same name already exists
        if (tagRepository.existsByName(tag.getName())) {
            throw new IllegalArgumentException("Tag with name '" + tag.getName() + "' already exists");
        }

        Tag savedTag = tagRepository.save(tag);

        // Create audit log
        auditLogService.createAuditLog(
                "TAG_CREATE",
                "TAG",
                savedTag.getId(),
                adminId,
                "CREATE",
                "Created new tag: " + savedTag.getName(),
                null);

        log.info("Tag created successfully: {}", savedTag.getId());
        return savedTag;
    }

    @Override
    @Transactional
    public Tag updateTag(Long tagId, Tag tag, Long adminId) {
        log.info("Updating tag: {} by admin: {}", tagId, adminId);

        Tag existingTag = tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + tagId));

        // Check if name is being changed and if new name already exists
        if (!existingTag.getName().equals(tag.getName()) &&
                tagRepository.existsByName(tag.getName())) {
            throw new IllegalArgumentException("Tag with name '" + tag.getName() + "' already exists");
        }

        // Update fields
        existingTag.setName(tag.getName());
        existingTag.setDescription(tag.getDescription());

        Tag updatedTag = tagRepository.save(existingTag);

        // Create audit log
        auditLogService.createAuditLog(
                "TAG_UPDATE",
                "TAG",
                updatedTag.getId(),
                adminId,
                "UPDATE",
                "Updated tag: " + updatedTag.getName(),
                null);

        log.info("Tag updated successfully: {}", tagId);
        return updatedTag;
    }

    @Override
    @Transactional
    public void deleteTag(Long tagId, Long adminId) {
        log.info("Deleting tag: {} by admin: {}", tagId, adminId);

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + tagId));

        // Check if tag has photos
        if (tag.getUsageCount() > 0 || !tag.getPhotos().isEmpty()) {
            throw new IllegalStateException("Cannot delete tag with " + tag.getUsageCount() + " usages");
        }

        String tagName = tag.getName();
        tagRepository.delete(tag);

        // Create audit log
        auditLogService.createAuditLog(
                "TAG_DELETE",
                "TAG",
                tagId,
                adminId,
                "DELETE",
                "Deleted tag: " + tagName,
                null);

        log.info("Tag deleted successfully: {}", tagId);
    }

    @Override
    public Page<Tag> getAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Category mergeCategories(Long sourceCategoryId, Long targetCategoryId, Long adminId) {
        log.info("Merging category {} into {} by admin: {}", sourceCategoryId, targetCategoryId, adminId);

        Category sourceCategory = categoryRepository.findById(sourceCategoryId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Source category not found with id: " + sourceCategoryId));

        Category targetCategory = categoryRepository.findById(targetCategoryId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Target category not found with id: " + targetCategoryId));

        // Move all photos from source to target
        List<Photo> photosToMove = sourceCategory.getPhotos();
        for (Photo photo : photosToMove) {
            photo.getCategories().remove(sourceCategory);
            if (!photo.getCategories().contains(targetCategory)) {
                photo.getCategories().add(targetCategory);
            }
        }

        // Delete source category
        categoryRepository.delete(sourceCategory);

        // Create audit log
        auditLogService.createAuditLog(
                "CATEGORY_MERGE",
                "CATEGORY",
                targetCategoryId,
                adminId,
                "MERGE",
                "Merged category '" + sourceCategory.getName() + "' into '" + targetCategory.getName() + "'",
                String.format("{\"sourceId\":%d,\"targetId\":%d,\"photosMoved\":%d}",
                        sourceCategoryId, targetCategoryId, photosToMove.size()));

        log.info("Categories merged successfully. {} photos moved", photosToMove.size());
        return categoryRepository.save(targetCategory);
    }

    @Override
    @Transactional
    public Tag mergeTags(Long sourceTagId, Long targetTagId, Long adminId) {
        log.info("Merging tag {} into {} by admin: {}", sourceTagId, targetTagId, adminId);

        Tag sourceTag = tagRepository.findById(sourceTagId)
                .orElseThrow(() -> new EntityNotFoundException("Source tag not found with id: " + sourceTagId));

        Tag targetTag = tagRepository.findById(targetTagId)
                .orElseThrow(() -> new EntityNotFoundException("Target tag not found with id: " + targetTagId));

        // Move all photos from source to target
        List<Photo> photosToMove = sourceTag.getPhotos();
        for (Photo photo : photosToMove) {
            photo.getTags().remove(sourceTag);
            if (!photo.getTags().contains(targetTag)) {
                photo.getTags().add(targetTag);
            }
        }

        // Update target tag usage count
        targetTag.setUsageCount(targetTag.getUsageCount() + sourceTag.getUsageCount());

        // Delete source tag
        tagRepository.delete(sourceTag);

        // Create audit log
        auditLogService.createAuditLog(
                "TAG_MERGE",
                "TAG",
                targetTagId,
                adminId,
                "MERGE",
                "Merged tag '" + sourceTag.getName() + "' into '" + targetTag.getName() + "'",
                String.format("{\"sourceId\":%d,\"targetId\":%d,\"photosMoved\":%d}",
                        sourceTagId, targetTagId, photosToMove.size()));

        log.info("Tags merged successfully. {} photos moved", photosToMove.size());
        return tagRepository.save(targetTag);
    }

    @Override
    public List<Category> getUnusedCategories() {
        return categoryRepository.findEmptyCategories();
    }

    @Override
    public List<Tag> getUnusedTags() {
        return tagRepository.findUnusedTags();
    }

    @Override
    @Transactional
    public int deleteUnusedCategories(Long adminId) {
        log.info("Deleting unused categories by admin: {}", adminId);

        List<Category> unusedCategories = categoryRepository.findEmptyCategories();
        int count = unusedCategories.size();

        categoryRepository.deleteAll(unusedCategories);

        // Create audit log
        auditLogService.createAuditLog(
                "CATEGORY_CLEANUP",
                "CATEGORY",
                null,
                adminId,
                "DELETE",
                "Deleted " + count + " unused categories",
                null);

        log.info("Deleted {} unused categories", count);
        return count;
    }

    @Override
    @Transactional
    public int deleteUnusedTags(Long adminId) {
        log.info("Deleting unused tags by admin: {}", adminId);

        List<Tag> unusedTags = tagRepository.findUnusedTags();
        int count = unusedTags.size();

        tagRepository.deleteAll(unusedTags);

        // Create audit log
        auditLogService.createAuditLog(
                "TAG_CLEANUP",
                "TAG",
                null,
                adminId,
                "DELETE",
                "Deleted " + count + " unused tags",
                null);

        log.info("Deleted {} unused tags", count);
        return count;
    }

    /**
     * Generate a URL-friendly slug from a string.
     */
    private String generateSlug(String text) {
        return text.toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");
    }
}
