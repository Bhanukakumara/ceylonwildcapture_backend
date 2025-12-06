package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.mapper;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.BillingInfoDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderSummaryDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


/**
 * Implementation of OrderMapper interface.
 */
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderResponseDto toResponseDto(Order order) {
        if (order == null) {
            return null;
        }

        return OrderResponseDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .buyerId(order.getBuyer() != null ? order.getBuyer().getId() : null)
                .buyerName(order.getBillingName())
                .buyerEmail(order.getBillingEmail())
                .totalAmount(order.getTotalAmount())
                .subtotal(order.getSubtotal())
                .taxAmount(order.getTaxAmount())
                .discountAmount(order.getDiscountAmount())
                .couponCode(order.getCouponCode())
                .status(order.getStatus())
                .paymentMethod(order.getPaymentMethod())
                .paymentId(order.getPaymentId())
                .transactionId(order.getTransactionId())
                .billingInfo(order.getBillingName() != null ? 
                    BillingInfoDto.builder()
                        .billingName(order.getBillingName())
                        .billingEmail(order.getBillingEmail())
                        .billingAddress(order.getBillingAddress())
                        .billingCity(order.getBillingCity())
                        .billingState(order.getBillingState())
                        .billingCountry(order.getBillingCountry())
                        .billingZip(order.getBillingZip())
                        .build() : null)
                .ipAddress(order.getIpAddress())
                .notes(order.getNotes())
                .itemCount(order.getOrderItems() != null ? order.getOrderItems().size() : 0)
                .completedAt(order.getCompletedAt())
                .cancelledAt(order.getCancelledAt())
                .refundedAt(order.getRefundedAt())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    @Override
    public OrderSummaryDto toSummaryDto(Order order) {
        if (order == null) {
            return null;
        }

        return OrderSummaryDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .itemCount(order.getOrderItems() != null ? order.getOrderItems().size() : 0)
                .createdAt(order.getCreatedAt())
                .build();
    }

    @Override
    public Page<OrderResponseDto> toResponseDtoPage(Page<Order> orders) {
        return orders.map(this::toResponseDto);
    }

    @Override
    public Page<OrderSummaryDto> toSummaryDtoPage(Page<Order> orders) {
        return orders.map(this::toSummaryDto);
    }
}
