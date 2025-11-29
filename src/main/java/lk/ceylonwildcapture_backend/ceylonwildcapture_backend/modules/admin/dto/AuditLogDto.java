package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for audit log entries.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDto {

    private Long id;
    private String eventType;
    private String entityType;
    private Long entityId;
    private Long userId;
    private String username;
    private String action;
    private String description;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime timestamp;
    private String metadata;
}
