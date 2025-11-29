package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.mapper;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.Payment;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.PaymentResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Mapper interface for converting Payment entities to DTOs.
 */
public interface PaymentMapper {

    /**
     * Convert Payment entity to PaymentResponseDto.
     *
     * @param payment payment entity
     * @return payment response DTO
     */
    PaymentResponseDto toResponseDto(Payment payment);

    /**
     * Convert list of Payment entities to list of PaymentResponseDto.
     *
     * @param payments list of payment entities
     * @return list of payment response DTOs
     */
    List<PaymentResponseDto> toResponseDtoList(List<Payment> payments);

    /**
     * Convert Page of Payment entities to Page of PaymentResponseDto.
     *
     * @param payments page of payment entities
     * @return page of payment response DTOs
     */
    Page<PaymentResponseDto> toResponseDtoPage(Page<Payment> payments);
}
