package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.AuditEventResponse;
// import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.repository.*; // Removed unused import
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    @Override
    public Page<AuditEventResponse> getAllGlobalAudits(Pageable pageable) {
        // Since audits are in separate tables, returning a global paginated list is
        // complex.
        // Returning empty for now or could implement a union query if crucial.
        return new PageImpl<>(Collections.emptyList(), pageable, 0);
    }
}
