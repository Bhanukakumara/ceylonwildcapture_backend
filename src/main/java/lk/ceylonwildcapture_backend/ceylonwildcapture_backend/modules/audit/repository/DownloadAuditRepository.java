package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.DownloadAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface DownloadAuditRepository
        extends JpaRepository<DownloadAudit, Long>, JpaSpecificationExecutor<DownloadAudit> {
    Page<DownloadAudit> findByUserId(Long userId, Pageable pageable);

    Page<DownloadAudit> findByLicenseId(Long licenseId, Pageable pageable);

    Page<DownloadAudit> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
