package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.tag.TagCreateDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.tag.TagResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.tag.TagUpdateDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Tag;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.repository.TagRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private static final String ERR_TAG_NOT_FOUND = "Tag not found: ";

    @Override
    public TagResponseDto createTag(TagCreateDto tagCreateDto) {
        log.info("Creating new tag with name: {}", tagCreateDto.getName());
        Objects.requireNonNull(tagCreateDto, "TagCreateDto must not be null");

        String name = tagCreateDto.getName().trim().toLowerCase();

        if (tagRepository.existsByName(name)) {
            throw new IllegalArgumentException("Tag already exists with name: " + name);
        }

        Tag tag = Tag.builder()
                .name(name)
                .description(tagCreateDto.getDescription())
                .usageCount(0)
                .build();

        Tag saved = tagRepository.save(tag);
        log.info("Tag created successfully with ID: {}", saved.getId());
        return TagResponseDto.fromEntity(saved);
    }

    @Override
    public TagResponseDto createTag(String name) {
        log.info("Creating new tag with name: {}", name);
        return createTag(TagCreateDto.builder().name(name).build());
    }

    @Override
    public TagResponseDto createTag(String name, String description) {
        log.info("Creating new tag with name: {} and description", name);
        return createTag(TagCreateDto.builder().name(name).description(description).build());
    }

    @Override
    public TagResponseDto updateTag(Long tagId, TagUpdateDto tagUpdateDto) {
        Objects.requireNonNull(tagUpdateDto, "TagUpdateDto must not be null");
        log.info("Updating tag with ID: {}", tagId);

        Tag existing = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_TAG_NOT_FOUND + tagId));

        if (tagUpdateDto.getName() != null && !tagUpdateDto.getName().isBlank()) {
            String newName = tagUpdateDto.getName().trim().toLowerCase();
            if (!existing.getName().equals(newName) && tagRepository.existsByName(newName)) {
                throw new IllegalArgumentException("Tag already exists with name: " + newName);
            }
            existing.setName(newName);
        }

        if (tagUpdateDto.getDescription() != null) {
            existing.setDescription(tagUpdateDto.getDescription());
        }

        Tag updated = tagRepository.save(existing);
        log.info("Tag updated successfully with ID: {}", updated.getId());
        return TagResponseDto.fromEntity(updated);
    }

    @Override
    public TagResponseDto updateDescription(Long tagId, String description) {
        log.info("Updating description for tag ID: {}", tagId);
        Tag existing = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_TAG_NOT_FOUND + tagId));

        existing.setDescription(description);
        return TagResponseDto.fromEntity(tagRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TagResponseDto> getTagById(Long tagId) {
        return tagRepository.findById(tagId).map(TagResponseDto::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TagResponseDto> getTagByName(String name) {
        return tagRepository.findByName(name.trim().toLowerCase()).map(TagResponseDto::fromEntity);
    }

    @Override
    public TagResponseDto getOrCreateTag(String name) {
        String normalizedName = name.trim().toLowerCase();
        return tagRepository.findByName(normalizedName)
                .map(TagResponseDto::fromEntity)
                .orElseGet(() -> createTag(normalizedName));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TagResponseDto> getAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable).map(TagResponseDto::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponseDto> getTopTags(Integer limit) {
      return tagRepository.findTopByOrderByUsageCountDesc(limit != null ? limit : 10)
          .stream()
          .map(tag -> TagResponseDto.fromEntity((Tag) tag))
          .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponseDto> getPopularTags(Integer minUsageCount) {
        int threshold = minUsageCount != null ? minUsageCount : 1;
        return tagRepository.findByUsageCountGreaterThanEqual(threshold)
                .stream()
                .map(TagResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponseDto> getUnusedTags() {
        return tagRepository.findByUsageCount(0)
                .stream()
                .map(tag -> TagResponseDto.fromEntity((Tag) tag))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TagResponseDto> searchTags(String searchTerm, Pageable pageable) {
        // Don't touch the searchTerm! Let DB handle case-insensitivity
        Page<Tag> tags = tagRepository.searchTags(searchTerm, pageable);
        return tags.map(TagResponseDto::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponseDto> getTagsByIds(List<Long> tagIds) {
        return tagRepository.findAllById(tagIds)
                .stream()
                .map(TagResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponseDto> getTagsByNames(List<String> names) {
        List<String> normalizedNames = names.stream()
                .map(name -> name.trim().toLowerCase())
                .collect(Collectors.toList());
        return tagRepository.findByNameIn(normalizedNames)
                .stream()
                .map(TagResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<TagResponseDto> getOrCreateTagsByNames(List<String> names) {
        return names.stream()
                .map(this::getOrCreateTag)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTag(Long tagId) {
        log.info("Deleting tag with ID: {}", tagId);
        if (!tagRepository.existsById(tagId)) {
            throw new IllegalArgumentException(ERR_TAG_NOT_FOUND + tagId);
        }
        tagRepository.deleteById(tagId);
        log.info("Tag deleted successfully with ID: {}", tagId);
    }

    @Override
    public long deleteUnusedTags() {
        log.info("Deleting unused tags");
        long count = tagRepository.countByUsageCount(0);
        tagRepository.deleteByUsageCount(0);
        log.info("Deleted {} unused tags", count);
        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean tagExists(String name) {
        return tagRepository.existsByName(name.trim().toLowerCase());
    }

    @Override
    public TagResponseDto incrementUsageCount(Long tagId) {
        log.info("Incrementing usage count for tag ID: {}", tagId);
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_TAG_NOT_FOUND + tagId));

        tag.setUsageCount(tag.getUsageCount() + 1);
        return TagResponseDto.fromEntity(tagRepository.save(tag));
    }

    @Override
    public TagResponseDto decrementUsageCount(Long tagId) {
        log.info("Decrementing usage count for tag ID: {}", tagId);
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_TAG_NOT_FOUND + tagId));

        int newCount = Math.max(0, tag.getUsageCount() - 1);
        tag.setUsageCount(newCount);
        return TagResponseDto.fromEntity(tagRepository.save(tag));
    }

    @Override
    public long recalculateUsageCounts() {
        log.info("Recalculating usage counts for all tags");
        List<Tag> allTags = tagRepository.findAll();
        allTags.forEach(tag -> {
            // This would require PhotoTag repository to count actual usage
            // For now, just log
            log.debug("Recalculating usage count for tag: {}", tag.getName());
        });
        return allTags.size();
    }

    @Override
    @Transactional(readOnly = true)
    public long countTotalTags() {
        return tagRepository.count();
    }

    @Override
    public TagResponseDto mergeTags(List<Long> sourceTagIds, Long targetTagId) {
        log.info("Merging {} tags into target tag ID: {}", sourceTagIds.size(), targetTagId);

        Tag targetTag = tagRepository.findById(targetTagId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_TAG_NOT_FOUND + targetTagId));

        List<Tag> sourceTags = tagRepository.findAllById(sourceTagIds);
        if (sourceTags.size() != sourceTagIds.size()) {
            throw new IllegalArgumentException("Some source tags not found");
        }

        // Sum up usage counts
        int totalUsage = sourceTags.stream()
                .mapToInt(Tag::getUsageCount)
                .sum() + targetTag.getUsageCount();

        targetTag.setUsageCount(totalUsage);

        // Delete source tags
        tagRepository.deleteAll(sourceTags);
        
        Tag merged = tagRepository.save(targetTag);
        log.info("Tags merged successfully into ID: {}", merged.getId());
        return TagResponseDto.fromEntity(merged);
    }

    @Override
    public TagResponseDto renameTag(Long tagId, String newName) {
        log.info("Renaming tag ID {} to: {}", tagId, newName);
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException(ERR_TAG_NOT_FOUND + tagId));

        String normalizedName = newName.trim().toLowerCase();
        if (tagRepository.existsByName(normalizedName)) {
            throw new IllegalArgumentException("Tag already exists with name: " + normalizedName);
        }

        tag.setName(normalizedName);
        Tag renamed = tagRepository.save(tag);
        log.info("Tag renamed successfully with ID: {}", renamed.getId());
        return TagResponseDto.fromEntity(renamed);
    }
}
