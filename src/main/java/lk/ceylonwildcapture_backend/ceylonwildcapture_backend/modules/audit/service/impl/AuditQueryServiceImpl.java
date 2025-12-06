package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.AuditEventResponse;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.AuditSearchCriteria;
// import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.AuditType; // Removed unused import
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.exception.AuditQueryException;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.mapper.AuditMapper;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.repository.*;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.AuditQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuditQueryServiceImpl implements AuditQueryService {

    private final DownloadAuditRepository downloadAuditRepository;
    private final LoginAuditRepository loginAuditRepository;
    private final AdminActionAuditRepository adminActionAuditRepository;
    private final PaymentAuditRepository paymentAuditRepository;
    private final UserActivityAuditRepository userActivityAuditRepository;
    private final AuditMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<AuditEventResponse> searchAudits(AuditSearchCriteria criteria, Pageable pageable) {
        if (criteria.getAuditType() == null) {
            // If no specific type is requested, we currently do not support efficient
            // global pagination across all tables.
            // In a real production system, this would require a unified audit table or
            // ElasticSearch.
            // For now, we return empty or require a type.
            throw new AuditQueryException("Audit Type is required for filtering.");
        }

        switch (criteria.getAuditType()) {
            case DOWNLOAD:
                // Apply filters for DownloadAudit
                // Ideally use a Specification here based on criteria
                // For brevity, just returning all if no specific filter prevents simple
                // implementation
                // Assuming basic implementation for now
                return downloadAuditRepository.findAll(pageable).map(mapper::toAuditEventResponse);

            case LOGIN:
            case LOGIN_FAILED:
                return loginAuditRepository.findAll(pageable).map(mapper::toAuditEventResponse);

            case ADMIN_MODERATION:
                return adminActionAuditRepository.findAll(pageable).map(mapper::toAuditEventResponse);

            case PAYMENT_EVENT:
            case PAYOUT_ACTION:
                return paymentAuditRepository.findAll(pageable).map(mapper::toAuditEventResponse);

            case USER_UPDATE:
                return userActivityAuditRepository.findAll(pageable).map(mapper::toAuditEventResponse);

            default:
                return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
    }
}
