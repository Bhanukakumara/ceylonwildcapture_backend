package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.tag.TagCreateDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.tag.TagResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.tag.TagUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Tag management operations.
 * Defines business logic for tag CRUD, usage tracking, and tag-based queries.
 */
public interface TagService {

    /**
     * Create a new tag.
     *
     * @param tagCreateDto the tag creation data
     * @return the created tag response DTO
     * @throws IllegalArgumentException if tag data is invalid or tag already exists
     */
    TagResponseDto createTag(TagCreateDto tagCreateDto);

    /**
     * Create a tag by name.
     *
     * @param name the tag name
     * @return the created tag response DTO
     * @throws IllegalArgumentException if tag name is invalid or already exists
     */
    TagResponseDto createTag(String name);

    /**
     * Create a tag with name and description.
     *
     * @param name the tag name
     * @param description the tag description
     * @return the created tag response DTO
     * @throws IllegalArgumentException if tag name is invalid or already exists
     */
    TagResponseDto createTag(String name, String description);

    /**
     * Update an existing tag.
     *
     * @param tagId the tag ID
     * @param tagUpdateDto the updated tag data
     * @return the updated tag response DTO
     * @throws IllegalArgumentException if tag not found
     */
    TagResponseDto updateTag(Long tagId, TagUpdateDto tagUpdateDto);

    /**
     * Update tag description.
     *
     * @param tagId the tag ID
     * @param description the new description
     * @return the updated tag response DTO
     * @throws IllegalArgumentException if tag not found
     */
    TagResponseDto updateDescription(Long tagId, String description);

    /**
     * Get tag by ID.
     *
     * @param tagId the tag ID
     * @return Optional containing the tag response DTO if found
     */
    Optional<TagResponseDto> getTagById(Long tagId);

    /**
     * Get tag by name.
     *
     * @param name the tag name
     * @return Optional containing the tag response DTO if found
     */
    Optional<TagResponseDto> getTagByName(String name);

    /**
     * Get or create tag by name.
     *
     * @param name the tag name
     * @return the existing or newly created tag response DTO
     */
    TagResponseDto getOrCreateTag(String name);

    /**
     * Get all tags with pagination.
     *
     * @param pageable pagination information
     * @return page of tag response DTOs
     */
    Page<TagResponseDto> getAllTags(Pageable pageable);

    /**
     * Get top tags by usage count.
     *
     * @param limit the number of tags to return
     * @return list of top tag response DTOs
     */
    List<TagResponseDto> getTopTags(Integer limit);

    /**
     * Get popular tags (usage count above threshold).
     *
     * @param minUsageCount the minimum usage count
     * @return list of popular tag response DTOs
     */
    List<TagResponseDto> getPopularTags(Integer minUsageCount);

    /**
     * Get unused tags.
     *
     * @return list of tag response DTOs with zero usage
     */
    List<TagResponseDto> getUnusedTags();

    /**
     * Search tags by name or description.
     *
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of matching tag response DTOs
     */
    Page<TagResponseDto> searchTags(String searchTerm, Pageable pageable);

    /**
     * Get tags by IDs.
     *
     * @param tagIds the list of tag IDs
     * @return list of tag response DTOs
     */
    List<TagResponseDto> getTagsByIds(List<Long> tagIds);

    /**
     * Get tags by names.
     *
     * @param names the list of tag names
     * @return list of tag response DTOs
     */
    List<TagResponseDto> getTagsByNames(List<String> names);

    /**
     * Get or create tags by names.
     *
     * @param names the list of tag names
     * @return list of existing or newly created tag response DTOs
     */
    List<TagResponseDto> getOrCreateTagsByNames(List<String> names);

    /**
     * Delete tag.
     *
     * @param tagId the tag ID
     * @throws IllegalArgumentException if tag not found
     */
    void deleteTag(Long tagId);

    /**
     * Delete unused tags.
     *
     * @return count of deleted tags
     */
    long deleteUnusedTags();

    /**
     * Check if tag exists by name.
     *
     * @param name the tag name
     * @return true if tag exists
     */
    boolean tagExists(String name);

    /**
     * Increment tag usage count.
     *
     * @param tagId the tag ID
     * @return the updated tag response DTO
     * @throws IllegalArgumentException if tag not found
     */
    TagResponseDto incrementUsageCount(Long tagId);

    /**
     * Decrement tag usage count.
     *
     * @param tagId the tag ID
     * @return the updated tag response DTO
     * @throws IllegalArgumentException if tag not found
     */
    TagResponseDto decrementUsageCount(Long tagId);

    /**
     * Recalculate tag usage counts.
     *
     * @return count of tags updated
     */
    long recalculateUsageCounts();

    /**
     * Count total tags.
     *
     * @return total count of tags
     */
    long countTotalTags();

    /**
     * Merge tags (combine multiple tags into one).
     *
     * @param sourceTagIds the IDs of tags to merge
     * @param targetTagId the ID of the target tag
     * @return the merged tag response DTO
     * @throws IllegalArgumentException if tags not found
     */
    TagResponseDto mergeTags(List<Long> sourceTagIds, Long targetTagId);

    /**
     * Rename tag.
     *
     * @param tagId the tag ID
     * @param newName the new tag name
     * @return the updated tag response DTO
     * @throws IllegalArgumentException if tag not found or new name already exists
     */
    TagResponseDto renameTag(Long tagId, String newName);
}
