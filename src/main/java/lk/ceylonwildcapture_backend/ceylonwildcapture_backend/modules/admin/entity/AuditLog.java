package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entity representing audit log entries for system actions.
 */
@Entity
@Table(name = "audit_logs", indexes = {
        @Index(name = "idx_event_type", columnList = "event_type"),
        @Index(name = "idx_entity_type", columnList = "entity_type"),
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_created_at", columnList = "created_at"),
        @Index(name = "idx_entity", columnList = "entity_type,entity_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_type", nullable = false, length = 50)
    private String eventType; // LOGIN, LOGOUT, PHOTO_UPLOAD, PHOTO_DOWNLOAD, MODERATION, USER_BAN, etc.

    @Column(name = "entity_type", length = 50)
    private String entityType; // USER, PHOTO, ORDER, PAYOUT, etc.

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "user_id")
    private Long userId; // User who performed the action

    @Column(name = "action", nullable = false, length = 100)
    private String action; // CREATE, UPDATE, DELETE, APPROVE, REJECT, etc.

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "metadata", columnDefinition = "JSON")
    private String metadata; // Additional data in JSON format

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @Column(name = "status", length = 20)
    private String status; // SUCCESS, FAILURE

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
