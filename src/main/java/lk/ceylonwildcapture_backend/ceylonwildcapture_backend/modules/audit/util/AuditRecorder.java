package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.util;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.AdminActionAuditService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.DownloadAuditService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.LoginAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditRecorder {

    private final DownloadAuditService downloadAuditService;
    private final LoginAuditService loginAuditService;
    private final AdminActionAuditService adminActionAuditService;

    public void recordDownload(Long userId, Long licenseId, String downloadUrl, Long fileSize, boolean success,
            String errorMessage, String ipAddress, String userAgent) {
        downloadAuditService.recordDownload(userId, licenseId, downloadUrl, fileSize, success, errorMessage, ipAddress,
                userAgent);
    }

    public void recordLoginSuccess(Long userId, String ipAddress, String userAgent) {
        loginAuditService.recordLogin(userId, ActionResult.SUCCESS, ipAddress, userAgent, null);
    }

    public void recordLoginFailure(Long userId, String ipAddress, String userAgent, String errorMessage) {
        loginAuditService.recordLogin(userId, ActionResult.FAILED, ipAddress, userAgent, errorMessage);
    }

    public void recordAdminModerationAction(Long adminId, String entityType, Long entityId, String action,
            ActionResult result, String reason, String metadata, String ipAddress) {
        adminActionAuditService.recordAdminAction(adminId, entityType, entityId, action, result, reason, metadata,
                ipAddress);
    }
}
