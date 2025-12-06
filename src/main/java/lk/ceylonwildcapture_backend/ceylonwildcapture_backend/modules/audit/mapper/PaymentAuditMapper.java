package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.mapper;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.PaymentAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.PaymentAudit;

import java.util.List;

/**
 * Mapper interface for converting PaymentAudit entities to DTOs.
 */
public interface PaymentAuditMapper {

    /**
     * Convert PaymentAudit entity to DTO.
     *
     * @param paymentAudit the payment audit entity
     * @return the payment audit DTO
     */
    PaymentAuditDto toDto(PaymentAudit paymentAudit);

    /**
     * Convert list of PaymentAudit entities to DTOs.
     *
     * @param paymentAudits list of payment audit entities
     * @return list of payment audit DTOs
     */
    List<PaymentAuditDto> toDtoList(List<PaymentAudit> paymentAudits);
}
