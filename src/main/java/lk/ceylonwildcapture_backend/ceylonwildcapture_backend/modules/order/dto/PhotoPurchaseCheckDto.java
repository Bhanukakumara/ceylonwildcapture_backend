package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for checking if a photo has been purchased.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhotoPurchaseCheckDto {

    private Long photoId;
    private Boolean purchased;
    private String licenseKey;
    private LocalDateTime purchaseDate;
    private String orderNumber;
}
