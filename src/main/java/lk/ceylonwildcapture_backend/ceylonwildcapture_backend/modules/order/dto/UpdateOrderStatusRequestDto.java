package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto;

import jakarta.validation.constraints.NotNull;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for updating order status.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusRequestDto {

    @NotNull(message = "Order status is required")
    private OrderStatus status;

    private String paymentId;
    private String transactionId;
    private String notes;
}
