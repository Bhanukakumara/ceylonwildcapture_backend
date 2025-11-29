package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.LicenseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Criteria class for filtering licenses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LicenseSearchCriteria {

    private Long userId;
    private Long photoId;
    private Long photographerId;
    private LicenseType licenseType;
    private Boolean isActive;
    private Boolean isExpired;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime issuedAfter;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime issuedBefore;

    private String searchTerm;
}
