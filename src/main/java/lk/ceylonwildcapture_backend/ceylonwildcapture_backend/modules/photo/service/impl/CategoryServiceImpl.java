package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryCreateDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryUpdateDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.repository.CategoryRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private static final String ERR_CATEGORY_NOT_FOUND = "Category not found: ";

	@Override
	public lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto createCategory(CategoryCreateDto category) {
		log.info("Creating new category with name: {}", category.getName());
		Objects.requireNonNull(category, "CategoryCreateDto must not be null");

		// Validate name
		String name = category.getName().trim();
		if (categoryRepository.existsByName(name)) {
			throw new IllegalArgumentException("Category name already exists: " + name);
		}

		// Handle slug - use provided or generate from name
		String slug = (category.getSlug() != null && !category.getSlug().isBlank())
				? category.getSlug().trim().toLowerCase()
				: generateSlug(name);

		if (categoryRepository.existsBySlug(slug)) {
			throw new IllegalArgumentException("Category slug already exists: " + slug);
		}

		// Build category entity
		lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category categoryEntity = lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category.builder()
				.name(name)
				.description(category.getDescription())
				.slug(slug)
				.imageUrl(category.getImageUrl())
				.isActive(Boolean.TRUE.equals(category.getIsActive()) || category.getIsActive() == null)
				.displayOrder(category.getDisplayOrder())
				.build();

		lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category saved = categoryRepository.save(categoryEntity);
		log.info("Category created successfully with ID: {}", saved.getId());
		return lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto.fromEntity(saved);
	}

	@Override
	public lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto updateCategory(Long categoryId, CategoryUpdateDto category) {
		Objects.requireNonNull(category, "CategoryUpdateDto must not be null");
		
		lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category existing = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new IllegalArgumentException(ERR_CATEGORY_NOT_FOUND + categoryId));

		updateCategoryName(category, existing);
		updateCategorySlug(category, existing);
		updateCategoryFields(category, existing);

		lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category updated = categoryRepository.save(existing);
		log.info("Updated category id={}, name={}", updated.getId(), updated.getName());
		return lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto.fromEntity(updated);
	}

	private void updateCategoryName(CategoryUpdateDto category, lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category existing) {
		if (category.getName() != null && !category.getName().isBlank()) {
			String newName = category.getName().trim();
			if (!newName.equals(existing.getName())) {
				if (categoryRepository.existsByName(newName)) {
					throw new IllegalArgumentException("Category name already exists: " + newName);
				}
				existing.setName(newName);
			}
		}
	}

	private void updateCategorySlug(CategoryUpdateDto category, lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category existing) {
		if (category.getSlug() != null && !category.getSlug().isBlank()) {
			String newSlug = category.getSlug().trim().toLowerCase();
			if (!newSlug.equals(existing.getSlug())) {
				if (categoryRepository.existsBySlug(newSlug)) {
					throw new IllegalArgumentException("Category slug already exists: " + newSlug);
				}
				existing.setSlug(newSlug);
			}
		}
	}

	private void updateCategoryFields(CategoryUpdateDto category, lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category existing) {
		if (category.getDescription() != null) {
			existing.setDescription(category.getDescription());
		}
		if (category.getImageUrl() != null) {
			existing.setImageUrl(category.getImageUrl());
		}
		if (category.getIsActive() != null) {
			existing.setIsActive(category.getIsActive());
		}
		if (category.getDisplayOrder() != null) {
			existing.setDisplayOrder(category.getDisplayOrder());
		}
	}

	@Override
	public lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto updateCategoryInfo(Long categoryId, String name, String description) {
		lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category existing = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new IllegalArgumentException(ERR_CATEGORY_NOT_FOUND + categoryId));

		if (name != null && !name.equals(existing.getName())) {
			if (categoryRepository.existsByName(name)) {
				throw new IllegalArgumentException("Category name already exists: " + name);
			}
			existing.setName(name);
			// Keep slug consistent if it was derived from name and not manually set. We won't attempt to auto-update here.
		}
		if (description != null) {
			existing.setDescription(description);
		}
		return lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto.fromEntity(categoryRepository.save(existing));
	}

	@Override
	public lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto updateCategoryImage(Long categoryId, String imageUrl) {
		lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category existing = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new IllegalArgumentException(ERR_CATEGORY_NOT_FOUND + categoryId));
		existing.setImageUrl(imageUrl);
		return lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto.fromEntity(categoryRepository.save(existing));
	}

	@Override
	public lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto updateDisplayOrder(Long categoryId, Integer displayOrder) {
		lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category existing = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new IllegalArgumentException(ERR_CATEGORY_NOT_FOUND + categoryId));
		existing.setDisplayOrder(displayOrder);
		return lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto.fromEntity(categoryRepository.save(existing));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto> getCategoryById(Long categoryId) {
		return categoryRepository.findById(categoryId)
				.map(lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto::fromEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto> getCategoryBySlug(String slug) {
		return categoryRepository.findBySlug(slug)
				.map(lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto::fromEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto> getCategoryByName(String name) {
		return categoryRepository.findByName(name)
				.map(lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto::fromEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto> getAllCategories(Pageable pageable) {
		return categoryRepository.findAll(pageable)
				.map(lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto::fromEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto> getActiveCategories() {
		return categoryRepository.findByIsActive(true).stream()
				.map(lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto::fromEntity)
				.collect(java.util.stream.Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Page<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto> getActiveCategories(Pageable pageable) {
		return categoryRepository.findByIsActive(true, pageable)
				.map(lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto::fromEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto> getCategoriesOrderedByDisplayOrder() {
		return categoryRepository.findAllByOrderByDisplayOrderAsc().stream()
				.map(lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto::fromEntity)
				.collect(java.util.stream.Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto> getActiveCategoriesOrderedByDisplayOrder() {
		return categoryRepository.findByIsActiveOrderByDisplayOrderAsc(true).stream()
				.map(lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto::fromEntity)
				.collect(java.util.stream.Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Page<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto> searchCategories(String searchTerm, Pageable pageable) {
		String term = (searchTerm == null) ? "" : searchTerm.trim();
		return categoryRepository.search(term, pageable)
				.map(lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto::fromEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto> getCategoriesByIds(List<Long> categoryIds) {
		if (categoryIds == null || categoryIds.isEmpty()) return Collections.emptyList();
		return categoryRepository.findByIdIn(categoryIds).stream()
				.map(lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto::fromEntity)
				.collect(java.util.stream.Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto> getCategoriesBySlugs(List<String> slugs) {
		if (slugs == null || slugs.isEmpty()) return Collections.emptyList();
		return categoryRepository.findBySlugIn(slugs).stream()
				.map(lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto::fromEntity)
				.collect(java.util.stream.Collectors.toList());
	}

	@Override
	public void deleteCategory(Long categoryId) {
		lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category existing = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new IllegalArgumentException(ERR_CATEGORY_NOT_FOUND + categoryId));

		// Prevent deletion if the category has associated photos (to avoid constraint issues)
		if (existing.getPhotos() != null && !existing.getPhotos().isEmpty()) {
			throw new IllegalArgumentException("Cannot delete category with associated photos");
		}

		categoryRepository.deleteById(categoryId);
		log.info("Deleted category id={}", categoryId);
	}

	@Override
	public lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto activateCategory(Long categoryId) {
		lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category existing = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new IllegalArgumentException(ERR_CATEGORY_NOT_FOUND + categoryId));
		existing.setIsActive(true);
		return lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto.fromEntity(categoryRepository.save(existing));
	}

	@Override
	public lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto deactivateCategory(Long categoryId) {
		lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category existing = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new IllegalArgumentException(ERR_CATEGORY_NOT_FOUND + categoryId));
		existing.setIsActive(false);
		return lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto.fromEntity(categoryRepository.save(existing));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean categoryExistsByName(String name) {
		return categoryRepository.existsByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean categoryExistsBySlug(String slug) {
		return categoryRepository.existsBySlug(slug);
	}

	@Override
	@Transactional(readOnly = true)
	public String generateSlug(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name is required to generate slug");
		}

		String base = slugify(name);
		String unique = base;
		int suffix = 1;
		while (categoryRepository.existsBySlug(unique)) {
			unique = base + "-" + suffix++;
		}
		return unique;
	}

	@Override
	public List<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto> reorderCategories(List<Object> categoryOrders) {
		if (categoryOrders == null || categoryOrders.isEmpty()) return Collections.emptyList();

		Map<Long, Integer> orderMap = parseOrderMap(categoryOrders);
		if (orderMap.isEmpty()) return Collections.emptyList();

		List<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category> toUpdate = categoryRepository.findByIdIn(new ArrayList<>(orderMap.keySet()));
		for (lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Category c : toUpdate) {
			Integer newOrder = orderMap.get(c.getId());
			if (newOrder != null) {
				c.setDisplayOrder(newOrder);
			}
		}
		return categoryRepository.saveAll(toUpdate).stream()
				.map(lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto::fromEntity)
				.collect(java.util.stream.Collectors.toList());
	}

	private Map<Long, Integer> parseOrderMap(List<Object> categoryOrders) {
		Map<Long, Integer> orderMap = new HashMap<>();
		for (Object obj : categoryOrders) {
			if (obj instanceof Map<?, ?> map) {
				Object idObj = map.get("id");
				Object orderObj = map.get("displayOrder");
				if (idObj != null && orderObj != null) {
					Long id = (idObj instanceof Number) ? ((Number) idObj).longValue() : toLong(idObj.toString());
					Integer order = (orderObj instanceof Number) ? ((Number) orderObj).intValue() : toInt(orderObj.toString());
					orderMap.put(id, order);
				}
			}
		}
		return orderMap;
	}

	private Long toLong(String s) { return Long.valueOf(s); }
	private Integer toInt(String s) { return Integer.valueOf(s); }

	@Override
	@Transactional(readOnly = true)
	public List<lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto> getEmptyCategories() {
		return categoryRepository.findEmptyCategories().stream()
				.map(lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.category.CategoryResponseDto::fromEntity)
				.collect(java.util.stream.Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Object[]> getCategoriesWithPhotoCount(Pageable pageable) {
		return categoryRepository.findCategoriesWithPhotoCount(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public long countActiveCategories() {
		return categoryRepository.countByIsActive(true);
	}

	@Override
	@Transactional(readOnly = true)
	public long countTotalCategories() {
		return categoryRepository.count();
	}

	private String slugify(String input) {
		String nowhitespace = input.trim().toLowerCase().replaceAll("\\s+", "-");
		String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD)
				.replaceAll("[^a-z0-9-]", "");
		String collapsed = normalized.replaceAll("-+", "-");
		String noLeading = collapsed.replaceAll("^-+", "");
		return noLeading.replaceAll("-+$", "");
	}
}

