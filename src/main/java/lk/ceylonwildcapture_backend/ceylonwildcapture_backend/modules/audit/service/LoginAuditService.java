package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.LoginAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoginAuditService {
    void recordLogin(Long userId, ActionResult result, String ipAddress, String userAgent, String errorMessage);

    Page<LoginAuditDto> getLoginHistoryByUser(Long userId, Pageable pageable);

    Page<LoginAuditDto> getLoginHistoryByResult(ActionResult result, Pageable pageable);
}
