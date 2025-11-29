package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.ModerationAction;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Photo;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "moderation_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModerationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Photo is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", nullable = false)
    @JsonIgnore
    private Photo photo;

    @NotNull(message = "Moderator is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", nullable = false)
    @JsonIgnore
    private User moderator;

    @NotNull(message = "Action is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ModerationAction action;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "previous_status", length = 20)
    private String previousStatus;

    @Column(name = "new_status", length = 20)
    private String newStatus;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
