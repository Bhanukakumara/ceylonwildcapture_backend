package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.PayoutStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for payout audit records.
 * Used for transferring payout audit information in API responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayoutAuditDto {

    private Long id;

    private Long payoutId;

    private String payoutReference;

    private Long adminId;

    private String adminUsername;

    private String action;

    private PayoutStatus oldStatus;

    private PayoutStatus newStatus;

    private String reason;

    private String notes;

    private String metadata;

    private LocalDateTime createdAt;

    /**
     * Get a human-readable summary of the audit record.
     *
     * @return summary string
     */
    public String getSummary() {
        return String.format("Payout %s - %s: %s -> %s by %s",
                payoutReference,
                action,
                oldStatus,
                newStatus,
                adminUsername != null ? adminUsername : "System");
    }
}
