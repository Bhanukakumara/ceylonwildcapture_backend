package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.mapper.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.*;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.*;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.AuditType;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.mapper.AuditMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuditMapperImpl implements AuditMapper {

    @Override
    public DownloadAuditDto toDownloadAuditDto(DownloadAudit audit) {
        if (audit == null)
            return null;
        return DownloadAuditDto.builder()
                .id(audit.getId())
                .licenseId(audit.getLicense() != null ? audit.getLicense().getId() : null)
                .userId(audit.getUser() != null ? audit.getUser().getId() : null)
                .userName(audit.getUser() != null ? audit.getUser().getUsername() : null) // Assuming user has username
                .ipAddress(audit.getIpAddress())
                .userAgent(audit.getUserAgent())
                .downloadUrl(audit.getDownloadUrl())
                .fileSize(audit.getFileSize())
                .downloadSuccessful(audit.getDownloadSuccessful())
                .errorMessage(audit.getErrorMessage())
                .device(audit.getDevice())
                .browser(audit.getBrowser())
                .operatingSystem(audit.getOperatingSystem())
                .country(audit.getCountry())
                .city(audit.getCity())
                .createdAt(audit.getCreatedAt())
                .build();
    }

    @Override
    public LoginAuditDto toLoginAuditDto(LoginAudit audit) {
        if (audit == null)
            return null;
        return LoginAuditDto.builder()
                .id(audit.getId())
                .userId(audit.getUser() != null ? audit.getUser().getId() : null)
                .userName(audit.getUser() != null ? audit.getUser().getUsername() : null)
                .actionResult(audit.getActionResult())
                .ipAddress(audit.getIpAddress())
                .userAgent(audit.getUserAgent())
                .device(audit.getDevice())
                .browser(audit.getBrowser())
                .operatingSystem(audit.getOperatingSystem())
                .country(audit.getCountry())
                .city(audit.getCity())
                .errorMessage(audit.getErrorMessage())
                .createdAt(audit.getCreatedAt())
                .build();
    }

    @Override
    public AdminActionAuditDto toAdminActionAuditDto(AdminActionAudit audit) {
        if (audit == null)
            return null;
        return AdminActionAuditDto.builder()
                .id(audit.getId())
                .adminId(audit.getAdmin() != null ? audit.getAdmin().getId() : null)
                .adminName(audit.getAdmin() != null ? audit.getAdmin().getUsername() : null)
                .entityType(audit.getEntityType() != null ? audit.getEntityType().name() : null)
                .entityId(audit.getEntityId())
                .action(audit.getAction())
                .actionResult(audit.getActionResult())
                .reason(audit.getReason())
                .metadata(audit.getMetadata())
                .ipAddress(audit.getIpAddress())
                .userAgent(audit.getUserAgent())
                .createdAt(audit.getCreatedAt())
                .build();
    }

    @Override
    public PaymentAuditDto toPaymentAuditDto(PaymentAudit audit) {
        if (audit == null)
            return null;
        return PaymentAuditDto.builder()
                .id(audit.getId())
                .userId(audit.getUser() != null ? audit.getUser().getId() : null)
                .userName(audit.getUser() != null ? audit.getUser().getUsername() : null)
                .paymentId(audit.getPaymentId())
                .orderId(audit.getOrderId())
                .action(audit.getAction())
                .actionResult(audit.getActionResult())
                .amount(audit.getAmount())
                .currency(audit.getCurrency())
                .paymentMethod(audit.getPaymentMethod())
                .transactionId(audit.getTransactionId())
                .metadata(audit.getMetadata())
                .errorMessage(audit.getErrorMessage())
                .ipAddress(audit.getIpAddress())
                .createdAt(audit.getCreatedAt())
                .build();
    }

    @Override
    public UserActivityAuditDto toUserActivityAuditDto(UserActivityAudit audit) {
        if (audit == null)
            return null;
        return UserActivityAuditDto.builder()
                .id(audit.getId())
                .userId(audit.getUser() != null ? audit.getUser().getId() : null)
                .userName(audit.getUser() != null ? audit.getUser().getUsername() : null)
                .action(audit.getAction())
                .actionResult(audit.getActionResult())
                .description(audit.getDescription())
                .oldValue(audit.getOldValue())
                .newValue(audit.getNewValue())
                .metadata(audit.getMetadata())
                .ipAddress(audit.getIpAddress())
                .userAgent(audit.getUserAgent())
                .createdAt(audit.getCreatedAt())
                .build();
    }

    @Override
    public AuditEventResponse toAuditEventResponse(DownloadAudit audit) {
        if (audit == null)
            return null;
        AuditEventResponse response = AuditEventResponse.builder()
                .id(audit.getId())
                .auditType(AuditType.DOWNLOAD)
                .actorId(audit.getUser() != null ? audit.getUser().getId() : null)
                .actorName(audit.getUser() != null ? audit.getUser().getUsername() : null)
                .actorType("USER")
                .entityType("LICENSE")
                .entityId(audit.getLicense() != null ? audit.getLicense().getId() : null)
                .action("DOWNLOAD")
                .actionResult(audit.getDownloadSuccessful()
                        ? lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult.SUCCESS
                        : lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult.FAILED)
                .description("Photo Download")
                .ipAddress(audit.getIpAddress())
                .createdAt(audit.getCreatedAt())
                .build();

        Map<String, Object> details = new HashMap<>();
        details.put("downloadUrl", audit.getDownloadUrl());
        details.put("fileSize", audit.getFileSize());
        response.setDetails(details);
        return response;
    }

    @Override
    public AuditEventResponse toAuditEventResponse(LoginAudit audit) {
        if (audit == null)
            return null;
        return AuditEventResponse.builder()
                .id(audit.getId())
                .auditType(audit
                        .getActionResult() == lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult.SUCCESS
                                ? AuditType.LOGIN
                                : AuditType.LOGIN_FAILED)
                .actorId(audit.getUser() != null ? audit.getUser().getId() : null)
                .actorName(audit.getUser() != null ? audit.getUser().getUsername() : null)
                .actorType("USER")
                .action("LOGIN")
                .actionResult(audit.getActionResult())
                .description("User Login")
                .ipAddress(audit.getIpAddress())
                .createdAt(audit.getCreatedAt())
                // .details(...)
                .build();
    }

    @Override
    public AuditEventResponse toAuditEventResponse(AdminActionAudit audit) {
        if (audit == null)
            return null;
        return AuditEventResponse.builder()
                .id(audit.getId())
                .auditType(AuditType.ADMIN_MODERATION)
                .actorId(audit.getAdmin() != null ? audit.getAdmin().getId() : null)
                .actorName(audit.getAdmin() != null ? audit.getAdmin().getUsername() : null)
                .actorType("ADMIN")
                .entityType(audit.getEntityType() != null ? audit.getEntityType().name() : null)
                .entityId(audit.getEntityId())
                .action(audit.getAction())
                .actionResult(audit.getActionResult())
                .description(audit.getReason())
                .metadata(audit.getMetadata())
                .ipAddress(audit.getIpAddress())
                .createdAt(audit.getCreatedAt())
                .build();
    }

    @Override
    public AuditEventResponse toAuditEventResponse(PaymentAudit audit) {
        if (audit == null)
            return null;
        return AuditEventResponse.builder()
                .id(audit.getId())
                .auditType(AuditType.PAYMENT_EVENT)
                .actorId(audit.getUser() != null ? audit.getUser().getId() : null)
                .actorName(audit.getUser() != null ? audit.getUser().getUsername() : null)
                .actorType("USER")
                .entityType("PAYMENT")
                .entityId(audit.getPaymentId())
                .action(audit.getAction())
                .actionResult(audit.getActionResult())
                .description("Payment Action: " + audit.getAction())
                .metadata(audit.getMetadata())
                .ipAddress(audit.getIpAddress())
                .createdAt(audit.getCreatedAt())
                .build();
    }

    @Override
    public AuditEventResponse toAuditEventResponse(UserActivityAudit audit) {
        if (audit == null)
            return null;
        return AuditEventResponse.builder()
                .id(audit.getId())
                .auditType(AuditType.USER_UPDATE)
                .actorId(audit.getUser() != null ? audit.getUser().getId() : null)
                .actorName(audit.getUser() != null ? audit.getUser().getUsername() : null)
                .actorType("USER")
                .entityType("USER")
                .entityId(audit.getUser() != null ? audit.getUser().getId() : null)
                .action(audit.getAction())
                .actionResult(audit.getActionResult())
                .description(audit.getDescription())
                .metadata(audit.getMetadata())
                .ipAddress(audit.getIpAddress())
                .createdAt(audit.getCreatedAt())
                .build();
    }
}
