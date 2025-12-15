package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.mapper;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.PayoutAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.entity.PayoutAudit;

import java.util.List;

/**
 * Mapper interface for converting PayoutAudit entities to DTOs.
 */
public interface PayoutAuditMapper {

    /**
     * Convert PayoutAudit entity to DTO.
     *
     * @param payoutAudit the payout audit entity
     * @return the payout audit DTO
     */
    PayoutAuditDto toDto(PayoutAudit payoutAudit);

    /**
     * Convert list of PayoutAudit entities to DTOs.
     *
     * @param payoutAudits list of payout audit entities
     * @return list of payout audit DTOs
     */
    List<PayoutAuditDto> toDtoList(List<PayoutAudit> payoutAudits);
}
