package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.AdminActionAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminActionAuditService {
    void recordAdminAction(Long adminId, String entityType, Long entityId, String action, ActionResult result,
            String reason, String metadata, String ipAddress);

    Page<AdminActionAuditDto> getActionsByAdmin(Long adminId, Pageable pageable);

    Page<AdminActionAuditDto> getActionsByEntity(Long entityId, String entityType, Pageable pageable);
}
