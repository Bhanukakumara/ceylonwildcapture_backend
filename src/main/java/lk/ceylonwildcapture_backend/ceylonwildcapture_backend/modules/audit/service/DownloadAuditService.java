package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.DownloadAuditDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DownloadAuditService {
    void recordDownload(Long userId, Long licenseId, String downloadUrl, Long fileSize, boolean success,
            String errorMessage, String ipAddress, String userAgent);

    Page<DownloadAuditDto> getDownloadsByUser(Long userId, Pageable pageable);

    Page<DownloadAuditDto> getDownloadsByLicense(Long licenseId, Pageable pageable);
}
