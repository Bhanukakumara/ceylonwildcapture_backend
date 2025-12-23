package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.impl;

import jakarta.transaction.Transactional;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.AuditLogDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.entity.AuditLog;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.repository.AuditLogRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Implementation of AuditLogService for managing audit logs.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    @Transactional
    public void createAuditLog(String eventType, String entityType, Long entityId, Long userId,
            String action, String description, String metadata) {
        try {
            AuditLog auditLog = AuditLog.builder()
                    .eventType(eventType)
                    .entityType(entityType)
                    .entityId(entityId)
                    .userId(userId)
                    .action(action)
                    .description(description)
                    .metadata(metadata)
                    .status("SUCCESS")
                    .build();

            auditLogRepository.save(auditLog);
            log.debug("Created audit log: {} - {} - {}", eventType, action, description);
        } catch (Exception e) {
            log.error("Failed to create audit log: {}", e.getMessage(), e);
        }
    }

    @Override
    public Page<AuditLogDto> getAllAuditLogs(Pageable pageable) {
        Page<AuditLog> auditLogs = auditLogRepository.findAll(pageable);
        return auditLogs.map(this::mapToDto);
    }

    @Override
    public Page<AuditLogDto> getAuditLogsByEventType(String eventType, Pageable pageable) {
        Page<AuditLog> auditLogs = auditLogRepository.findByEventType(eventType, pageable);
        return auditLogs.map(this::mapToDto);
    }

    @Override
    public Page<AuditLogDto> getAuditLogsByEntityType(String entityType, Pageable pageable) {
        Page<AuditLog> auditLogs = auditLogRepository.findByEntityType(entityType, pageable);
        return auditLogs.map(this::mapToDto);
    }

    @Override
    public Page<AuditLogDto> getAuditLogsByUser(Long userId, Pageable pageable) {
        Page<AuditLog> auditLogs = auditLogRepository.findByUserId(userId, pageable);
        return auditLogs.map(this::mapToDto);
    }

    @Override
    public Page<AuditLogDto> getAuditLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate,
            Pageable pageable) {
        Page<AuditLog> auditLogs = auditLogRepository.findByCreatedAtBetween(startDate, endDate, pageable);
        return auditLogs.map(this::mapToDto);
    }

    @Override
    public Page<AuditLogDto> getAuditLogsForEntity(String entityType, Long entityId, Pageable pageable) {
        Page<AuditLog> auditLogs = auditLogRepository.findByEntityTypeAndEntityId(entityType, entityId, pageable);
        return auditLogs.map(this::mapToDto);
    }

    @Override
    public Page<AuditLogDto> getDownloadAuditLogs(Pageable pageable) {
        Page<AuditLog> auditLogs = auditLogRepository.findByEventType("PHOTO_DOWNLOAD", pageable);
        return auditLogs.map(this::mapToDto);
    }

    @Override
    public Page<AuditLogDto> getLoginAuditLogs(Pageable pageable) {
        Page<AuditLog> auditLogs = auditLogRepository.findByEventType("LOGIN", pageable);
        return auditLogs.map(this::mapToDto);
    }

    @Override
    public Page<AuditLogDto> getFailedLoginAttempts(Pageable pageable) {
        Page<AuditLog> auditLogs = auditLogRepository.findFailedLoginAttempts(pageable);
        return auditLogs.map(this::mapToDto);
    }

    @Override
    public Page<AuditLogDto> getAuditLogsByAction(String action, Pageable pageable) {
        Page<AuditLog> auditLogs = auditLogRepository.findByAction(action, pageable);
        return auditLogs.map(this::mapToDto);
    }

    @Override
    public Page<AuditLogDto> searchAuditLogs(String searchTerm, Pageable pageable) {
        Page<AuditLog> auditLogs = auditLogRepository.searchAuditLogs(searchTerm, pageable);
        return auditLogs.map(this::mapToDto);
    }

    @Override
    @Transactional
    public long deleteOldAuditLogs(LocalDateTime olderThan) {
        long deletedCount = auditLogRepository.deleteByCreatedAtBefore(olderThan);
        log.info("Deleted {} audit logs older than {}", deletedCount, olderThan);
        return deletedCount;
    }

    /**
     * Maps an AuditLog entity to AuditLogDto.
     *
     * @param auditLog the audit log entity
     * @return the mapped DTO
     */
    private AuditLogDto mapToDto(AuditLog auditLog) {
        return AuditLogDto.builder()
                .id(auditLog.getId())
                .eventType(auditLog.getEventType())
                .entityType(auditLog.getEntityType())
                .entityId(auditLog.getEntityId())
                .userId(auditLog.getUserId())
                .username(null) // To be populated if needed with user lookup
                .action(auditLog.getAction())
                .description(auditLog.getDescription())
                .ipAddress(auditLog.getIpAddress())
                .userAgent(auditLog.getUserAgent())
                .timestamp(auditLog.getCreatedAt())
                .metadata(auditLog.getMetadata())
                .build();
    }
}
