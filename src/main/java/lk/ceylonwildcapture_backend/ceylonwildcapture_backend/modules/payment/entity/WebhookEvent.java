package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.entity;

import jakarta.persistence.*;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentProvider;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.WebhookEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entity representing webhook events from payment gateways.
 */
@Entity
@Table(name = "webhook_events", indexes = {
        @Index(name = "idx_event_id", columnList = "event_id"),
        @Index(name = "idx_event_type", columnList = "event_type"),
        @Index(name = "idx_provider", columnList = "provider"),
        @Index(name = "idx_processed", columnList = "processed"),
        @Index(name = "idx_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebhookEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false, unique = true, length = 200)
    private String eventId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private PaymentProvider provider;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 50)
    private WebhookEventType eventType;

    @Column(name = "payment_id", length = 100)
    private String paymentId;

    @Column(name = "event_payload", columnDefinition = "TEXT", nullable = false)
    private String eventPayload;

    @Column(name = "signature", columnDefinition = "TEXT")
    private String signature;

    @Column(nullable = false)
    @Builder.Default
    private Boolean processed = false;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Column(name = "processing_error", columnDefinition = "TEXT")
    private String processingError;

    @Column(name = "retry_count")
    @Builder.Default
    private Integer retryCount = 0;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
