package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service.CategoryService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryCreateDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryUpdateDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryReorderItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

	private final CategoryService categoryService;

	// ------------------------------
	// Create Category
	// ------------------------------
	@PostMapping
	public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryCreateDto category) {
		return ResponseEntity.ok(categoryService.createCategory(category));
	}

	// ------------------------------
	// Update Category
	// ------------------------------
	@PutMapping("/{id}")
	public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable("id") Long id,
														  @Valid @RequestBody CategoryUpdateDto category) {
		return ResponseEntity.ok(categoryService.updateCategory(id, category));
	}

    // ------------------------------
	// Update Category Info
	// ------------------------------
	@PatchMapping("/{id}/info")
	public ResponseEntity<CategoryResponseDto> updateCategoryInfo(@PathVariable("id") Long id,
														  @RequestParam(required = false) String name,
														  @RequestParam(required = false) String description) {
		return ResponseEntity.ok(categoryService.updateCategoryInfo(id, name, description));
	}

    // ------------------------------
	// Update Category Image
	// ------------------------------
	@PatchMapping("/{id}/image")
	public ResponseEntity<CategoryResponseDto> updateCategoryImage(@PathVariable("id") Long id,
														   @RequestParam("imageUrl") @NotBlank String imageUrl) {
		return ResponseEntity.ok(categoryService.updateCategoryImage(id, imageUrl));
	}

    // ------------------------------
	// Update Category Display Order
	// ------------------------------
	@PatchMapping("/{id}/order")
	public ResponseEntity<CategoryResponseDto> updateDisplayOrder(@PathVariable("id") Long id,
														  @RequestParam("displayOrder") @Min(0) Integer displayOrder) {
		return ResponseEntity.ok(categoryService.updateDisplayOrder(id, displayOrder));
	}	// ------------------------------
	// Get Category by ID
	// ------------------------------
	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponseDto> getById(@PathVariable("id") Long id) {
		return categoryService.getCategoryById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	// ------------------------------
	// Get Category by Slug
	// ------------------------------
	@GetMapping("/slug/{slug}")
	public ResponseEntity<CategoryResponseDto> getBySlug(@PathVariable("slug") String slug) {
		return categoryService.getCategoryBySlug(slug)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	// ------------------------------
	// Get Category by Name
	// ------------------------------
	@GetMapping("/name/{name}")
	public ResponseEntity<CategoryResponseDto> getByName(@PathVariable("name") String name) {
		return categoryService.getCategoryByName(name)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	// ------------------------------
	// Get All Categories (paged)
	// ------------------------------
	@GetMapping
	public Page<CategoryResponseDto> getAll(Pageable pageable) {
		return categoryService.getAllCategories(pageable);
	}

	// ------------------------------
	// Get Active Categories (paged)
	// ------------------------------
	@GetMapping("/active")
	public Page<CategoryResponseDto> getActive(Pageable pageable) {
		return categoryService.getActiveCategories(pageable);
	}

	// ------------------------------
	// Get Active Categories (list)
	// ------------------------------
	@GetMapping("/active/list")
	public ResponseEntity<List<CategoryResponseDto>> getActiveList() {
		return ResponseEntity.ok(categoryService.getActiveCategories());
	}

	// ------------------------------
	// Get Ordered Categories
	// ------------------------------
	@GetMapping("/ordered")
	public ResponseEntity<List<CategoryResponseDto>> getOrdered() {
		return ResponseEntity.ok(categoryService.getCategoriesOrderedByDisplayOrder());
	}

	// ------------------------------
	// Get Active Ordered Categories
	// ------------------------------
	@GetMapping("/active/ordered")
	public ResponseEntity<List<CategoryResponseDto>> getActiveOrdered() {
		return ResponseEntity.ok(categoryService.getActiveCategoriesOrderedByDisplayOrder());
	}

	// ------------------------------
	// Search Categories
	// ------------------------------
	@GetMapping("/search")
	public Page<CategoryResponseDto> search(@RequestParam("q") String q, Pageable pageable) {
		return categoryService.searchCategories(q, pageable);
	}

	// ------------------------------
	// Get Empty Categories
	// ------------------------------
	@GetMapping("/empty")
	public ResponseEntity<List<CategoryResponseDto>> getEmpty() {
		return ResponseEntity.ok(categoryService.getEmptyCategories());
	}

	// ------------------------------
	// Get Categories with Photo Count
	// ------------------------------
	@GetMapping("/with-photo-count")
	public Page<Object[]> getWithPhotoCount(Pageable pageable) {
		return categoryService.getCategoriesWithPhotoCount(pageable);
	}

	// ------------------------------
	// Count Active Categories
	// ------------------------------
	@GetMapping("/counts/active")
	public ResponseEntity<Long> countActive() {
		return ResponseEntity.ok(categoryService.countActiveCategories());
	}

	// ------------------------------
	// Count Total Categories
	// ------------------------------
	@GetMapping("/counts/total")
	public ResponseEntity<Long> countTotal() {
		return ResponseEntity.ok(categoryService.countTotalCategories());
	}

	// ------------------------------
	// Reorder Categories
	// ------------------------------
	@PostMapping("/reorder")
	public ResponseEntity<List<CategoryResponseDto>> reorder(@Valid @RequestBody List<CategoryReorderItemDto> items) {
		List<Object> raw = items.stream()
			.map(i -> java.util.Map.of("id", i.getId(), "displayOrder", i.getDisplayOrder()))
			.collect(Collectors.toList());
		return ResponseEntity.ok(categoryService.reorderCategories(raw));
	}

	// ------------------------------
	// Delete Category
	// ------------------------------
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}

	// ------------------------------
	// Activate Category
	// ------------------------------
	@PostMapping("/{id}/activate")
	public ResponseEntity<CategoryResponseDto> activate(@PathVariable("id") Long id) {
		return ResponseEntity.ok(categoryService.activateCategory(id));
	}

	// ------------------------------
	// Deactivate Category
	// ------------------------------
	@PostMapping("/{id}/deactivate")
	public ResponseEntity<CategoryResponseDto> deactivate(@PathVariable("id") Long id) {
		return ResponseEntity.ok(categoryService.deactivateCategory(id));
	}
}

