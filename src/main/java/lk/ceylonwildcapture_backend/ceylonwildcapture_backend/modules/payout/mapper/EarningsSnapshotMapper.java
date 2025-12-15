package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.mapper;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.EarningsResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.entity.EarningsSnapshot;

import java.util.List;

/**
 * Mapper interface for converting EarningsSnapshot entities to DTOs.
 */
public interface EarningsSnapshotMapper {

    /**
     * Convert EarningsSnapshot entity to response DTO.
     *
     * @param earningsSnapshot the earnings snapshot entity
     * @return the earnings response DTO
     */
    EarningsResponseDto toResponseDto(EarningsSnapshot earningsSnapshot);

    /**
     * Convert list of EarningsSnapshot entities to response DTOs.
     *
     * @param earningsSnapshots list of earnings snapshot entities
     * @return list of earnings response DTOs
     */
    List<EarningsResponseDto> toResponseDtoList(List<EarningsSnapshot> earningsSnapshots);
}
