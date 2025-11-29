package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.LicenseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for order item response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemResponseDto {

    private Long id;
    private Long orderId;
    private Long photoId;
    private String photoTitle;
    private String photoThumbnailUrl;
    private Long photographerId;
    private String photographerName;
    private LicenseType licenseType;
    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal finalPrice;
    private BigDecimal photographerEarnings;
    private BigDecimal platformCommission;
    private Boolean licenseGenerated;
    private String licenseKey;
    private LocalDateTime createdAt;
}
