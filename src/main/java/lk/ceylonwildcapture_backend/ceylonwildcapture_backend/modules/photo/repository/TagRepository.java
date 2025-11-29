package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Tag entity.
 * Provides database operations for tag management and photo tagging.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * Find tag by name.
     *
     * @param name the tag name
     * @return Optional containing the tag if found
     */
    Optional<Tag> findByName(String name);

    /**
     * Find tag by name (case-insensitive).
     *
     * @param name the tag name
     * @return Optional containing the tag if found
     */
    Optional<Tag> findByNameIgnoreCase(String name);

    /**
     * Check if tag exists by name.
     *
     * @param name the tag name
     * @return true if tag exists
     */
    boolean existsByName(String name);

    /**
     * Check if tag exists by name (case-insensitive).
     *
     * @param name the tag name
     * @return true if tag exists
     */
    boolean existsByNameIgnoreCase(String name);

    /**
     * Find all tags ordered by usage count.
     *
     * @param pageable pagination information
     * @return page of tags ordered by usage count
     */
    Page<Tag> findAllByOrderByUsageCountDesc(Pageable pageable);

    /**
     * Find tags by name containing search term.
     *
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of matching tags
     */
    Page<Tag> findByNameContainingIgnoreCase(String searchTerm, Pageable pageable);

    /**
     * Find tags by description containing search term.
     *
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of matching tags
     */
    Page<Tag> findByDescriptionContainingIgnoreCase(String searchTerm, Pageable pageable);

    /**
     * Find tags with usage count greater than threshold.
     *
     * @param minUsageCount the minimum usage count
     * @return list of popular tags
     */
    List<Tag> findByUsageCountGreaterThanEqual(Integer minUsageCount);

    /**
     * Find top N most used tags.
     *
     * @param limit the number of tags to return
     * @return list of top tags
     */
    @Query("SELECT t FROM Tag t ORDER BY t.usageCount DESC")
    List<Tag> findTopTags(Pageable pageable);

    /**
     * Find tags by IDs.
     *
     * @param ids the list of tag IDs
     * @return list of tags
     */
    List<Tag> findByIdIn(List<Long> ids);

    /**
     * Find tags by names.
     *
     * @param names the list of tag names
     * @return list of tags
     */
    List<Tag> findByNameIn(List<String> names);

    /**
     * Find tags by names (case-insensitive).
     *
     * @param names the list of tag names
     * @return list of tags
     */
    @Query("SELECT t FROM Tag t WHERE LOWER(t.name) IN :names")
    List<Tag> findByNameInIgnoreCase(@Param("names") List<String> names);

    /**
     * Find unused tags (no photos tagged).
     *
     * @return list of unused tags
     */
    @Query("SELECT t FROM Tag t WHERE t.usageCount = 0")
    List<Tag> findUnusedTags();

    /**
     * Search tags by name or description.
     *
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of matching tags
     */
    @Query("SELECT t FROM Tag t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Tag> search(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Count total tags.
     *
     * @return total count of tags
     */
    @Query("SELECT COUNT(t) FROM Tag t")
    long countTotalTags();

    /**
     * Find recently created tags.
     *
     * @param pageable pagination information
     * @return page of recent tags
     */
    Page<Tag> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
