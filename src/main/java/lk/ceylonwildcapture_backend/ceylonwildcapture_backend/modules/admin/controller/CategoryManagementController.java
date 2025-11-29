package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.CategoryManagementService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST controller for category and tag management.
 */
@RestController
@RequestMapping("/api/v1/admin/categories-tags")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class CategoryManagementController {

    private final CategoryManagementService categoryManagementService;

    // Category Management

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(
            @Valid @RequestBody Category category,
            @RequestAttribute("userId") Long adminId) {
        Category createdCategory = categoryManagementService.createCategory(category, adminId);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody Category category,
            @RequestAttribute("userId") Long adminId) {
        Category updatedCategory = categoryManagementService.updateCategory(categoryId, category, adminId);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long categoryId,
            @RequestAttribute("userId") Long adminId) {
        categoryManagementService.deleteCategory(categoryId, adminId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<Page<Category>> getAllCategories(Pageable pageable) {
        Page<Category> categories = categoryManagementService.getAllCategories(pageable);
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/categories/merge")
    public ResponseEntity<Category> mergeCategories(
            @RequestParam Long sourceCategoryId,
            @RequestParam Long targetCategoryId,
            @RequestAttribute("userId") Long adminId) {
        Category mergedCategory = categoryManagementService.mergeCategories(sourceCategoryId, targetCategoryId, adminId);
        return ResponseEntity.ok(mergedCategory);
    }

    @GetMapping("/categories/unused")
    public ResponseEntity<List<Category>> getUnusedCategories() {
        List<Category> unusedCategories = categoryManagementService.getUnusedCategories();
        return ResponseEntity.ok(unusedCategories);
    }

    @DeleteMapping("/categories/unused")
    public ResponseEntity<Integer> deleteUnusedCategories(@RequestAttribute("userId") Long adminId) {
        int deletedCount = categoryManagementService.deleteUnusedCategories(adminId);
        return ResponseEntity.ok(deletedCount);
    }

    // Tag Management

    @PostMapping("/tags")
    public ResponseEntity<Tag> createTag(
            @Valid @RequestBody Tag tag,
            @RequestAttribute("userId") Long adminId) {
        Tag createdTag = categoryManagementService.createTag(tag, adminId);
        return ResponseEntity.ok(createdTag);
    }

    @PutMapping("/tags/{tagId}")
    public ResponseEntity<Tag> updateTag(
            @PathVariable Long tagId,
            @Valid @RequestBody Tag tag,
            @RequestAttribute("userId") Long adminId) {
        Tag updatedTag = categoryManagementService.updateTag(tagId, tag, adminId);
        return ResponseEntity.ok(updatedTag);
    }

    @DeleteMapping("/tags/{tagId}")
    public ResponseEntity<Void> deleteTag(
            @PathVariable Long tagId,
            @RequestAttribute("userId") Long adminId) {
        categoryManagementService.deleteTag(tagId, adminId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tags")
    public ResponseEntity<Page<Tag>> getAllTags(Pageable pageable) {
        Page<Tag> tags = categoryManagementService.getAllTags(pageable);
        return ResponseEntity.ok(tags);
    }

    @PostMapping("/tags/merge")
    public ResponseEntity<Tag> mergeTags(
            @RequestParam Long sourceTagId,
            @RequestParam Long targetTagId,
            @RequestAttribute("userId") Long adminId) {
        Tag mergedTag = categoryManagementService.mergeTags(sourceTagId, targetTagId, adminId);
        return ResponseEntity.ok(mergedTag);
    }

    @GetMapping("/tags/unused")
    public ResponseEntity<List<Tag>> getUnusedTags() {
        List<Tag> unusedTags = categoryManagementService.getUnusedTags();
        return ResponseEntity.ok(unusedTags);
    }

    @DeleteMapping("/tags/unused")
    public ResponseEntity<Integer> deleteUnusedTags(@RequestAttribute("userId") Long adminId) {
        int deletedCount = categoryManagementService.deleteUnusedTags(adminId);
        return ResponseEntity.ok(deletedCount);
    }
}
