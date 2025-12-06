package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing a payment audit record.
 * Tracks payment-related events including transactions and payouts.
 */
@Entity
@Table(name = "payment_audits", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_order_id", columnList = "order_id"),
    @Index(name = "idx_payment_id", columnList = "payment_id"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @NotNull(message = "Payment ID is required")
    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @Column(name = "order_id")
    private Long orderId;

    @NotNull(message = "Action is required")
    @Column(name = "action", nullable = false, length = 100)
    private String action;

    @NotNull(message = "Action result is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "action_result", nullable = false)
    private ActionResult actionResult;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency", length = 3)
    private String currency;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(name = "transaction_id", length = 100)
    private String transactionId;

    @Column(name = "metadata", columnDefinition = "JSON")
    private String metadata;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
