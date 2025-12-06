package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.mapper;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.UserActivityAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.UserActivityAudit;

import java.util.List;

/**
 * Mapper interface for converting UserActivityAudit entities to DTOs.
 */
public interface UserActivityAuditMapper {

    /**
     * Convert UserActivityAudit entity to DTO.
     *
     * @param userActivityAudit the user activity audit entity
     * @return the user activity audit DTO
     */
    UserActivityAuditDto toDto(UserActivityAudit userActivityAudit);

    /**
     * Convert list of UserActivityAudit entities to DTOs.
     *
     * @param userActivityAudits list of user activity audit entities
     * @return list of user activity audit DTOs
     */
    List<UserActivityAuditDto> toDtoList(List<UserActivityAudit> userActivityAudits);
}
