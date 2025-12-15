package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.mapper;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.*;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.*;

public interface AuditMapper {
    DownloadAuditDto toDownloadAuditDto(DownloadAudit audit);

    LoginAuditDto toLoginAuditDto(LoginAudit audit);

    AdminActionAuditDto toAdminActionAuditDto(AdminActionAudit audit);

    PaymentAuditDto toPaymentAuditDto(PaymentAudit audit);

    UserActivityAuditDto toUserActivityAuditDto(UserActivityAudit audit);

    // Unified response mapping
    AuditEventResponse toAuditEventResponse(DownloadAudit audit);

    AuditEventResponse toAuditEventResponse(LoginAudit audit);

    AuditEventResponse toAuditEventResponse(AdminActionAudit audit);

    AuditEventResponse toAuditEventResponse(PaymentAudit audit);

    AuditEventResponse toAuditEventResponse(UserActivityAudit audit);
}
