package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.mapper;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.LoginAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.LoginAudit;

import java.util.List;

/**
 * Mapper interface for converting LoginAudit entities to DTOs.
 */
public interface LoginAuditMapper {

    /**
     * Convert LoginAudit entity to DTO.
     *
     * @param loginAudit the login audit entity
     * @return the login audit DTO
     */
    LoginAuditDto toDto(LoginAudit loginAudit);

    /**
     * Convert list of LoginAudit entities to DTOs.
     *
     * @param loginAudits list of login audit entities
     * @return list of login audit DTOs
     */
    List<LoginAuditDto> toDtoList(List<LoginAudit> loginAudits);
}
