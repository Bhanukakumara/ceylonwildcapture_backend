package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.DownloadAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.DownloadAudit;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.mapper.AuditMapper;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.repository.DownloadAuditRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.DownloadAuditService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.License;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DownloadAuditServiceImpl implements DownloadAuditService {

    private final DownloadAuditRepository repository;
    private final AuditMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void recordDownload(Long userId, Long licenseId, String downloadUrl, Long fileSize, boolean success,
            String errorMessage, String ipAddress, String userAgent) {
        User user = entityManager.getReference(User.class, userId);
        License license = entityManager.getReference(License.class, licenseId);

        DownloadAudit audit = DownloadAudit.builder()
                .user(user)
                .license(license)
                .downloadUrl(downloadUrl)
                .fileSize(fileSize)
                .downloadSuccessful(success)
                .errorMessage(errorMessage)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .build();

        repository.save(audit);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DownloadAuditDto> getDownloadsByUser(Long userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable).map(mapper::toDownloadAuditDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DownloadAuditDto> getDownloadsByLicense(Long licenseId, Pageable pageable) {
        return repository.findByLicenseId(licenseId, pageable).map(mapper::toDownloadAuditDto);
    }
}
