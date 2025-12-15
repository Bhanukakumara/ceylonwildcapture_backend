package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
// import jakarta.persistence.metamodel.EntityType; // Removed incorrect import
import jakarta.validation.constraints.NotNull;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entity representing an admin action audit record.
 * Tracks moderation actions performed by administrators.
 */
@Entity
@Table(name = "admin_action_audits", indexes = {
        @Index(name = "idx_admin_id", columnList = "admin_id"),
        @Index(name = "idx_entity_id", columnList = "entity_id"),
        @Index(name = "idx_entity_type", columnList = "entity_type"),
        @Index(name = "idx_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminActionAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Admin user is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    @JsonIgnore
    private User admin;

    @NotNull(message = "Entity type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", nullable = false)
    private EntityType entityType;

    @NotNull(message = "Entity ID is required")
    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @NotNull(message = "Action is required")
    @Column(name = "action", nullable = false, length = 100)
    private String action;

    @NotNull(message = "Action result is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "action_result", nullable = false)
    private ActionResult actionResult;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "metadata", columnDefinition = "JSON")
    private String metadata;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
