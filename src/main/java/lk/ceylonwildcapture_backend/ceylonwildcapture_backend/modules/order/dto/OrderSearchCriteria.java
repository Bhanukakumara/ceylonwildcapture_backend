package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Criteria class for filtering orders.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchCriteria {

    private Long buyerId;
    private Long photographerId;
    private Long photoId;
    private OrderStatus status;
    private String orderNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    private BigDecimal minAmount;
    private BigDecimal maxAmount;

    private String paymentMethod;
    private String searchTerm;
    private String billingEmail;

    private Boolean hasRefund;
    private Boolean isCancelled;
    private Boolean isCompleted;
}
