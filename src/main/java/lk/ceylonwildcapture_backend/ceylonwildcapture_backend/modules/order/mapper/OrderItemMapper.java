package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.mapper;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderItemResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.OrderItem;

import java.util.List;

/**
 * Mapper interface for converting OrderItem entities to DTOs.
 */
public interface OrderItemMapper {

    /**
     * Convert OrderItem entity to OrderItemResponseDto.
     *
     * @param orderItem order item entity
     * @return order item response DTO
     */
    OrderItemResponseDto toResponseDto(OrderItem orderItem);

    /**
     * Convert list of OrderItem entities to list of OrderItemResponseDto.
     *
     * @param orderItems list of order item entities
     * @return list of order item response DTOs
     */
    List<OrderItemResponseDto> toResponseDtoList(List<OrderItem> orderItems);
}
