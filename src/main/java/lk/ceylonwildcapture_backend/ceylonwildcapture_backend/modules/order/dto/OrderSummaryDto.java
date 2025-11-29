package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for order summary (lightweight version for lists).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderSummaryDto {

    private Long id;
    private String orderNumber;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private Integer itemCount;
    private String firstPhotoThumbnail;
    private LocalDateTime createdAt;
}
