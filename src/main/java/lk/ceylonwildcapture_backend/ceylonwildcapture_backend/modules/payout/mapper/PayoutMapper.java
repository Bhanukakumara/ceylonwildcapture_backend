package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.mapper;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.PayoutResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.entity.Payout;

import java.util.List;

/**
 * Mapper interface for converting Payout entities to DTOs.
 */
public interface PayoutMapper {

    /**
     * Convert Payout entity to response DTO.
     *
     * @param payout the payout entity
     * @return the payout response DTO
     */
    PayoutResponseDto toResponseDto(Payout payout);

    /**
     * Convert list of Payout entities to response DTOs.
     *
     * @param payouts list of payout entities
     * @return list of payout response DTOs
     */
    List<PayoutResponseDto> toResponseDtoList(List<Payout> payouts);
}
