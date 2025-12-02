package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.tag.TagCreateDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.tag.TagMergeDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.tag.TagResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.tag.TagUpdateDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
@Slf4j
public class TagController {

    private final TagService tagService;

    // ------------------------------
    // Create Tag
    // ------------------------------
    @PostMapping
    public ResponseEntity<TagResponseDto> createTag(@Valid @RequestBody TagCreateDto tagCreateDto) {
        return ResponseEntity.ok(tagService.createTag(tagCreateDto));
    }

    // ------------------------------
    // Create Tag by Name (Simple)
    // ------------------------------
    @PostMapping("/simple")
    public ResponseEntity<TagResponseDto> createSimpleTag(@RequestParam("name") @NotBlank String name) {
        return ResponseEntity.ok(tagService.createTag(name));
    }

    // ------------------------------
    // Update Tag
    // ------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<TagResponseDto> updateTag(
            @PathVariable("id") Long id,
            @Valid @RequestBody TagUpdateDto tagUpdateDto) {
        return ResponseEntity.ok(tagService.updateTag(id, tagUpdateDto));
    }

    // ------------------------------
    // Update Tag Description
    // ------------------------------
    @PatchMapping("/{id}/description")
    public ResponseEntity<TagResponseDto> updateDescription(
            @PathVariable("id") Long id,
            @RequestParam("description") String description) {
        return ResponseEntity.ok(tagService.updateDescription(id, description));
    }

    // ------------------------------
    // Rename Tag
    // ------------------------------
    @PatchMapping("/{id}/rename")
    public ResponseEntity<TagResponseDto> renameTag(
            @PathVariable("id") Long id,
            @RequestParam("newName") @NotBlank String newName) {
        return ResponseEntity.ok(tagService.renameTag(id, newName));
    }

    // ------------------------------
    // Get Tag by ID
    // ------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDto> getById(@PathVariable("id") Long id) {
        return tagService.getTagById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------
    // Get Tag by Name
    // ------------------------------
    @GetMapping("/name/{name}")
    public ResponseEntity<TagResponseDto> getByName(@PathVariable("name") String name) {
        return tagService.getTagByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------
    // Get All Tags (paged)
    // ------------------------------
    @GetMapping
    public Page<TagResponseDto> getAll(Pageable pageable) {
        return tagService.getAllTags(pageable);
    }

    // ------------------------------
    // Get Top Tags
    // ------------------------------
    @GetMapping("/top")
    public ResponseEntity<List<TagResponseDto>> getTopTags(
            @RequestParam(value = "limit", defaultValue = "10") @Min(1) Integer limit) {
        return ResponseEntity.ok(tagService.getTopTags(limit));
    }

    // ------------------------------
    // Get Popular Tags
    // ------------------------------
    @GetMapping("/popular")
    public ResponseEntity<List<TagResponseDto>> getPopularTags(
            @RequestParam(value = "minUsageCount", defaultValue = "1") @Min(0) Integer minUsageCount) {
        return ResponseEntity.ok(tagService.getPopularTags(minUsageCount));
    }

    // ------------------------------
    // Get Unused Tags
    // ------------------------------
    @GetMapping("/unused")
    public ResponseEntity<List<TagResponseDto>> getUnusedTags() {
        return ResponseEntity.ok(tagService.getUnusedTags());
    }

    // ------------------------------
    // Search Tags
    // ------------------------------
    @GetMapping("/search")
    public Page<TagResponseDto> searchTags(
            @RequestParam("q") String searchTerm,
            Pageable pageable) {
        return tagService.searchTags(searchTerm, pageable);
    }

    // ------------------------------
    // Get Tags by IDs
    // ------------------------------
    @PostMapping("/by-ids")
    public ResponseEntity<List<TagResponseDto>> getByIds(@RequestBody List<Long> tagIds) {
        return ResponseEntity.ok(tagService.getTagsByIds(tagIds));
    }

    // ------------------------------
    // Get Tags by Names
    // ------------------------------
    @PostMapping("/by-names")
    public ResponseEntity<List<TagResponseDto>> getByNames(@RequestBody List<String> names) {
        return ResponseEntity.ok(tagService.getTagsByNames(names));
    }

    // ------------------------------
    // Get or Create Tags by Names
    // ------------------------------
    @PostMapping("/get-or-create")
    public ResponseEntity<List<TagResponseDto>> getOrCreateByNames(@RequestBody List<String> names) {
        return ResponseEntity.ok(tagService.getOrCreateTagsByNames(names));
    }

    // ------------------------------
    // Count Total Tags
    // ------------------------------
    @GetMapping("/count")
    public ResponseEntity<Long> countTotal() {
        return ResponseEntity.ok(tagService.countTotalTags());
    }

    // ------------------------------
    // Increment Usage Count
    // ------------------------------
    @PostMapping("/{id}/increment-usage")
    public ResponseEntity<TagResponseDto> incrementUsage(@PathVariable("id") Long id) {
        return ResponseEntity.ok(tagService.incrementUsageCount(id));
    }

    // ------------------------------
    // Decrement Usage Count
    // ------------------------------
    @PostMapping("/{id}/decrement-usage")
    public ResponseEntity<TagResponseDto> decrementUsage(@PathVariable("id") Long id) {
        return ResponseEntity.ok(tagService.decrementUsageCount(id));
    }

    // ------------------------------
    // Recalculate Usage Counts
    // ------------------------------
    @PostMapping("/recalculate-usage")
    public ResponseEntity<Long> recalculateUsage() {
        return ResponseEntity.ok(tagService.recalculateUsageCounts());
    }

    // ------------------------------
    // Merge Tags
    // ------------------------------
    @PostMapping("/merge")
    public ResponseEntity<TagResponseDto> mergeTags(@Valid @RequestBody TagMergeDto mergeDto) {
        return ResponseEntity.ok(tagService.mergeTags(
                mergeDto.getSourceTagIds(),
                mergeDto.getTargetTagId()
        ));
    }

    // ------------------------------
    // Delete Tag
    // ------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

    // ------------------------------
    // Delete Unused Tags
    // ------------------------------
    @DeleteMapping("/unused")
    public ResponseEntity<Long> deleteUnusedTags() {
        return ResponseEntity.ok(tagService.deleteUnusedTags());
    }

    // ------------------------------
    // Check Tag Exists
    // ------------------------------
    @GetMapping("/exists")
    public ResponseEntity<Boolean> tagExists(@RequestParam("name") String name) {
        return ResponseEntity.ok(tagService.tagExists(name));
    }
}
