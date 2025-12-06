package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.mapper;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.AdminActionAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.AdminActionAudit;

import java.util.List;

/**
 * Mapper interface for converting AdminActionAudit entities to DTOs.
 */
public interface AdminActionAuditMapper {

    /**
     * Convert AdminActionAudit entity to DTO.
     *
     * @param adminActionAudit the admin action audit entity
     * @return the admin action audit DTO
     */
    AdminActionAuditDto toDto(AdminActionAudit adminActionAudit);

    /**
     * Convert list of AdminActionAudit entities to DTOs.
     *
     * @param adminActionAudits list of admin action audit entities
     * @return list of admin action audit DTOs
     */
    List<AdminActionAuditDto> toDtoList(List<AdminActionAudit> adminActionAudits);
}
