package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for admin order management operations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderManagementDto {

    @NotNull(message = "Order ID is required")
    private Long orderId;

    private OrderAction action;

    private String refundReason;

    private BigDecimal refundAmount;

    private String adminNotes;

    public enum OrderAction {
        CANCEL,
        REFUND_FULL,
        REFUND_PARTIAL,
        MARK_COMPLETED,
        MARK_DISPUTED,
        RESOLVE_DISPUTE
    }
}
