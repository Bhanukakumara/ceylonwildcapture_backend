package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.PhotographerProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing a snapshot of photographer earnings.
 * Stores historical earnings data for reporting and analytics.
 */
@Entity
@Table(name = "earnings_snapshots", indexes = {
    @Index(name = "idx_photographer_id", columnList = "photographer_id"),
    @Index(name = "idx_snapshot_date", columnList = "snapshot_date")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EarningsSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Photographer is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photographer_id", nullable = false)
    @JsonIgnore
    private PhotographerProfile photographer;

    @NotNull(message = "Total earnings is required")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalEarnings;

    @NotNull(message = "Pending earnings is required")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal pendingEarnings;

    @NotNull(message = "Paid earnings is required")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal paidEarnings;

    @Column(precision = 12, scale = 2)
    private BigDecimal completedOrdersAmount;

    @Column(precision = 12, scale = 2)
    private BigDecimal refundedAmount;

    @Column(name = "total_orders")
    private Long totalOrders;

    @Column(name = "completed_orders")
    private Long completedOrders;

    @Column(name = "refunded_orders")
    private Long refundedOrders;

    @CreationTimestamp
    @Column(name = "snapshot_date", nullable = false, updatable = false)
    private LocalDateTime snapshotDate;
}
