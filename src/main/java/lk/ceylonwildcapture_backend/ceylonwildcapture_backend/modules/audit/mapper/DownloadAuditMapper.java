package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.mapper;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.DownloadAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.DownloadAudit;

import java.util.List;

/**
 * Mapper interface for converting DownloadAudit entities to DTOs.
 */
public interface DownloadAuditMapper {

    /**
     * Convert DownloadAudit entity to DTO.
     *
     * @param downloadAudit the download audit entity
     * @return the download audit DTO
     */
    DownloadAuditDto toDto(DownloadAudit downloadAudit);

    /**
     * Convert list of DownloadAudit entities to DTOs.
     *
     * @param downloadAudits list of download audit entities
     * @return list of download audit DTOs
     */
    List<DownloadAuditDto> toDtoList(List<DownloadAudit> downloadAudits);
}
