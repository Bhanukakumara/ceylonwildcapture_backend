package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.LicenseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDto {

    private Long id;
    private Long photoId;
    private String photoTitle;
    private String photoImageUrl;
    private String photoThumbnailUrl;
    private String photographerName;
    private Long photographerId;
    private LicenseType licenseType;
    private BigDecimal price;
    private LocalDateTime addedAt;
}
