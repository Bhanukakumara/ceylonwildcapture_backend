package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.DownloadAudit;
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
 * Repository interface for DownloadAudit entity.
 * Provides database operations for download audit records.
 */
@Repository
public interface DownloadAuditRepository extends JpaRepository<DownloadAudit, Long> {

    /**
     * Find all download audits for a specific user.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of download audits
     */
    Page<DownloadAudit> findByUserId(Long userId, Pageable pageable);

    /**
     * Find all download audits for a specific license.
     *
     * @param licenseId the license ID
     * @param pageable pagination information
     * @return page of download audits
     */
    Page<DownloadAudit> findByLicenseId(Long licenseId, Pageable pageable);

    /**
     * Find download audits within a date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of download audits
     */
    Page<DownloadAudit> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find successful download audits for a user.
     *
     * @param userId the user ID
     * @param downloadSuccessful the success flag
     * @param pageable pagination information
     * @return page of download audits
     */
    Page<DownloadAudit> findByUserIdAndDownloadSuccessful(Long userId, Boolean downloadSuccessful, Pageable pageable);

    /**
     * Find download audits by IP address.
     *
     * @param ipAddress the IP address
     * @param pageable pagination information
     * @return page of download audits
     */
    Page<DownloadAudit> findByIpAddress(String ipAddress, Pageable pageable);

    /**
     * Find download audits by country.
     *
     * @param country the country
     * @param pageable pagination information
     * @return page of download audits
     */
    Page<DownloadAudit> findByCountry(String country, Pageable pageable);

    /**
     * Count total downloads for a user.
     *
     * @param userId the user ID
     * @return count of downloads
     */
    long countByUserId(Long userId);

    /**
     * Count successful downloads for a user.
     *
     * @param userId the user ID
     * @param downloadSuccessful the success flag
     * @return count of successful downloads
     */
    long countByUserIdAndDownloadSuccessful(Long userId, Boolean downloadSuccessful);

    /**
     * Find download audits by user and date range with success status.
     *
     * @param userId the user ID
     * @param startDate the start date
     * @param endDate the end date
     * @param downloadSuccessful the success flag
     * @param pageable pagination information
     * @return page of download audits
     */
    @Query("SELECT da FROM DownloadAudit da WHERE da.user.id = :userId " +
           "AND da.createdAt BETWEEN :startDate AND :endDate " +
           "AND da.downloadSuccessful = :downloadSuccessful " +
           "ORDER BY da.createdAt DESC")
    Page<DownloadAudit> findByUserIdAndDateRangeAndStatus(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("downloadSuccessful") Boolean downloadSuccessful,
            Pageable pageable);

    /**
     * Find most recent download audit for a user.
     *
     * @param userId the user ID
     * @return optional containing the most recent download audit
     */
    @Query(value = "SELECT * FROM download_audits WHERE user_id = :userId ORDER BY created_at DESC LIMIT 1",
           nativeQuery = true)
    Optional<DownloadAudit> findMostRecentByUserId(@Param("userId") Long userId);

    /**
     * Count downloads within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return count of downloads
     */
    long countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
