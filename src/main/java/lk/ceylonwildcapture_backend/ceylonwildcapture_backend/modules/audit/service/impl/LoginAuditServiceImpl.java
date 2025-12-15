package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.LoginAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.LoginAudit;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.mapper.AuditMapper;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.repository.LoginAuditRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.LoginAuditService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginAuditServiceImpl implements LoginAuditService {

    private final LoginAuditRepository repository;
    private final AuditMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void recordLogin(Long userId, ActionResult result, String ipAddress, String userAgent, String errorMessage) {
        User user = null;
        if (userId != null) {
            user = entityManager.getReference(User.class, userId);
        }

        LoginAudit audit = LoginAudit.builder()
                .user(user)
                .actionResult(result)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .errorMessage(errorMessage)
                .build();

        repository.save(audit);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LoginAuditDto> getLoginHistoryByUser(Long userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable).map(mapper::toLoginAuditDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LoginAuditDto> getLoginHistoryByResult(ActionResult result, Pageable pageable) {
        return repository.findByActionResult(result, pageable).map(mapper::toLoginAuditDto);
    }
}
