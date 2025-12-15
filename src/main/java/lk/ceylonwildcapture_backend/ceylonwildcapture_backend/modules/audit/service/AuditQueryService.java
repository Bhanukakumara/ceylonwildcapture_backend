package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.AuditEventResponse;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.AuditSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditQueryService {
    Page<AuditEventResponse> searchAudits(AuditSearchCriteria criteria, Pageable pageable);
}
