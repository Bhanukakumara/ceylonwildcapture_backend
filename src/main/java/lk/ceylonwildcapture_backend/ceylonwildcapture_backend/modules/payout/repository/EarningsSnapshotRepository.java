package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.entity.EarningsSnapshot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for EarningsSnapshot entity.
 * Provides database operations for earnings snapshot records.
 */
@Repository
public interface EarningsSnapshotRepository extends JpaRepository<EarningsSnapshot, Long> {

    /**
     * Find all earnings snapshots for a photographer.
     *
     * @param photographerId the photographer ID
     * @param pageable pagination information
     * @return page of earnings snapshots
     */
    Page<EarningsSnapshot> findByPhotographerId(Long photographerId, Pageable pageable);

    /**
     * Find earnings snapshots within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of earnings snapshots
     */
    Page<EarningsSnapshot> findBySnapshotDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find earnings snapshots for photographer within date range.
     *
     * @param photographerId the photographer ID
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of earnings snapshots
     */
    @Query("SELECT es FROM EarningsSnapshot es WHERE es.photographer.id = :photographerId " +
           "AND es.snapshotDate BETWEEN :startDate AND :endDate " +
           "ORDER BY es.snapshotDate DESC")
    Page<EarningsSnapshot> findByPhotographerIdAndDateRange(
            @Param("photographerId") Long photographerId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    /**
     * Find most recent earnings snapshot for a photographer.
     *
     * @param photographerId the photographer ID
     * @return optional containing the most recent earnings snapshot
     */
    @Query(value = "SELECT * FROM earnings_snapshots WHERE photographer_id = :photographerId ORDER BY snapshot_date DESC LIMIT 1",
           nativeQuery = true)
    Optional<EarningsSnapshot> findMostRecentByPhotographerId(@Param("photographerId") Long photographerId);

    /**
     * Find earnings snapshots for a photographer.
     *
     * @param photographerId the photographer ID
     * @return list of earnings snapshots
     */
    List<EarningsSnapshot> findByPhotographerIdOrderBySnapshotDateDesc(Long photographerId);

    /**
     * Count earnings snapshots for a photographer.
     *
     * @param photographerId the photographer ID
     * @return count of earnings snapshots
     */
    long countByPhotographerId(Long photographerId);

    /**
     * Find earnings snapshots created after a specific date.
     *
     * @param snapshotDate the snapshot date
     * @param pageable pagination information
     * @return page of earnings snapshots
     */
    Page<EarningsSnapshot> findBySnapshotDateAfter(LocalDateTime snapshotDate, Pageable pageable);

    /**
     * Find earnings snapshots created before a specific date.
     *
     * @param snapshotDate the snapshot date
     * @param pageable pagination information
     * @return page of earnings snapshots
     */
    Page<EarningsSnapshot> findBySnapshotDateBefore(LocalDateTime snapshotDate, Pageable pageable);
}
