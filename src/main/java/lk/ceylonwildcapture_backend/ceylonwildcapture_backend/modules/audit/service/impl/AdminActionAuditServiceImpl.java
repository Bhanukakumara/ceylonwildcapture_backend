package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.AdminActionAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.AdminActionAudit;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.EntityType;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.mapper.AuditMapper;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.repository.AdminActionAuditRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.AdminActionAuditService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminActionAuditServiceImpl implements AdminActionAuditService {

    private final AdminActionAuditRepository repository;
    private final AuditMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void recordAdminAction(Long adminId, String entityTypeStr, Long entityId, String action, ActionResult result,
            String reason, String metadata, String ipAddress) {
        User admin = entityManager.getReference(User.class, adminId);

        EntityType entityType = EntityType.valueOf(entityTypeStr); // Convert String to Enum

        AdminActionAudit audit = AdminActionAudit.builder()
                .admin(admin)
                .entityType(entityType)
                .entityId(entityId)
                .action(action)
                .actionResult(result)
                .reason(reason)
                .metadata(metadata)
                .ipAddress(ipAddress)
                .build();

        repository.save(audit);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdminActionAuditDto> getActionsByAdmin(Long adminId, Pageable pageable) {
        return repository.findByAdminId(adminId, pageable).map(mapper::toAdminActionAuditDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdminActionAuditDto> getActionsByEntity(Long entityId, String entityTypeStr, Pageable pageable) {
        EntityType entityType = EntityType.valueOf(entityTypeStr);
        return repository.findByEntityIdAndEntityType(entityId, entityType, pageable)
                .map(mapper::toAdminActionAuditDto);
    }
}
