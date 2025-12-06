package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.DownloadAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.DownloadAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service interface for download audit operations.
 * Provides methods for recording and retrieving download audit logs.
 */
public interface DownloadAuditService {

    /**
     * Record a download audit event.
     *
     * @param downloadAudit the download audit entity
     * @return the saved download audit entity
     */
    DownloadAudit recordDownload(DownloadAudit downloadAudit);

    /**
     * Get download audit by ID.
     *
     * @param auditId the audit ID
     * @return optional containing the download audit DTO
     */
    Optional<DownloadAuditDto> getDownloadAuditById(Long auditId);

    /**
     * Get all download audits for a user.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of download audit DTOs
     */
    Page<DownloadAuditDto> getDownloadsByUser(Long userId, Pageable pageable);

    /**
     * Get successful downloads for a user.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of download audit DTOs
     */
    Page<DownloadAuditDto> getSuccessfulDownloadsByUser(Long userId, Pageable pageable);

    /**
     * Get downloads within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of download audit DTOs
     */
    Page<DownloadAuditDto> getDownloadsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Get downloads by country.
     *
     * @param country the country
     * @param pageable pagination information
     * @return page of download audit DTOs
     */
    Page<DownloadAuditDto> getDownloadsByCountry(String country, Pageable pageable);

    /**
     * Count total downloads for a user.
     *
     * @param userId the user ID
     * @return count of downloads
     */
    long countDownloadsForUser(Long userId);

    /**
     * Get most recent download for a user.
     *
     * @param userId the user ID
     * @return optional containing the most recent download audit DTO
     */
    Optional<DownloadAuditDto> getMostRecentDownloadForUser(Long userId);

    /**
     * Get download statistics for a user.
     *
     * @param userId the user ID
     * @return map containing download statistics
     */
    java.util.Map<String, Object> getDownloadStatistics(Long userId);
}
