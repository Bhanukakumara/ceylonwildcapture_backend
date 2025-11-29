package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.entity.Payout;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "photographer_profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotographerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnore
    private User user;

    @Column(columnDefinition = "TEXT")
    @Size(max = 2000)
    private String bio;

    @Column(name = "portfolio_url")
    private String portfolioUrl;

    @Column(name = "social_media_links", columnDefinition = "TEXT")
    private String socialMediaLinks;

    @Column(name = "bank_account_number", length = 50)
    private String bankAccountNumber;

    @Column(name = "bank_name", length = 100)
    private String bankName;

    @Column(name = "paypal_email", length = 100)
    private String paypalEmail;

    @Column(name = "total_earnings", precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal totalEarnings = BigDecimal.ZERO;

    @Column(name = "pending_earnings", precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal pendingEarnings = BigDecimal.ZERO;

    @Column(name = "commission_rate", precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal commissionRate = new BigDecimal("15.00");

    @Column(name = "verified_photographer", nullable = false)
    @Builder.Default
    private Boolean verifiedPhotographer = false;

    @Column(name = "total_sales", nullable = false)
    @Builder.Default
    private Integer totalSales = 0;

    @Column(name = "rating", precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(name = "total_reviews", nullable = false)
    @Builder.Default
    private Integer totalReviews = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "photographer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<Payout> payouts = new ArrayList<>();
}
