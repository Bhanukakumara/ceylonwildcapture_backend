package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.CategoryManagementService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CategoryManagementServiceImpl implements CategoryManagementService {

    @Override
    public Category createCategory(Category category, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Category updateCategory(Long categoryId, Category category, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void deleteCategory(Long categoryId, Long adminId) {
        // TODO: Implement actual business logic
    }

    @Override
    public Page<Category> getAllCategories(Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public Tag createTag(Tag tag, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Tag updateTag(Long tagId, Tag tag, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void deleteTag(Long tagId, Long adminId) {
        // TODO: Implement actual business logic
    }

    @Override
    public Page<Tag> getAllTags(Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public Category mergeCategories(Long sourceCategoryId, Long targetCategoryId, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Tag mergeTags(Long sourceTagId, Long targetTagId, Long adminId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Category> getUnusedCategories() {
        // TODO: Implement actual business logic
        return Collections.emptyList();
    }

    @Override
    public List<Tag> getUnusedTags() {
        // TODO: Implement actual business logic
        return Collections.emptyList();
    }

    @Override
    public int deleteUnusedCategories(Long adminId) {
        // TODO: Implement actual business logic
        return 0;
    }

    @Override
    public int deleteUnusedTags(Long adminId) {
        // TODO: Implement actual business logic
        return 0;
    }
}
