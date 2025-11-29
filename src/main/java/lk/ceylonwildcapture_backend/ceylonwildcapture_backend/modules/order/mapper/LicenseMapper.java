package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.mapper;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.LicenseResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.License;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Mapper interface for converting License entities to DTOs.
 */
public interface LicenseMapper {

    /**
     * Convert License entity to LicenseResponseDto.
     *
     * @param license license entity
     * @return license response DTO
     */
    LicenseResponseDto toResponseDto(License license);

    /**
     * Convert list of License entities to list of LicenseResponseDto.
     *
     * @param licenses list of license entities
     * @return list of license response DTOs
     */
    List<LicenseResponseDto> toResponseDtoList(List<License> licenses);

    /**
     * Convert Page of License entities to Page of LicenseResponseDto.
     *
     * @param licenses page of license entities
     * @return page of license response DTOs
     */
    Page<LicenseResponseDto> toResponseDtoPage(Page<License> licenses);
}
