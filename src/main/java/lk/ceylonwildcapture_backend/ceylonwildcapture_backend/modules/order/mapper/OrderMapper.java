package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.mapper;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderSummaryDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * Mapper interface for converting Order entities to DTOs.
 */
@Component
public interface OrderMapper {

    /**
     * Convert Order entity to OrderResponseDto.
     *
     * @param order order entity
     * @return order response DTO
     */
    OrderResponseDto toResponseDto(Order order);

    /**
     * Convert Order entity to OrderSummaryDto.
     *
     * @param order order entity
     * @return order summary DTO
     */
    OrderSummaryDto toSummaryDto(Order order);

    /**
     * Convert Page of Order entities to Page of OrderResponseDto.
     *
     * @param orders page of order entities
     * @return page of order response DTOs
     */
    Page<OrderResponseDto> toResponseDtoPage(Page<Order> orders);

    /**
     * Convert Page of Order entities to Page of OrderSummaryDto.
     *
     * @param orders page of order entities
     * @return page of order summary DTOs
     */
    Page<OrderSummaryDto> toSummaryDtoPage(Page<Order> orders);
}
