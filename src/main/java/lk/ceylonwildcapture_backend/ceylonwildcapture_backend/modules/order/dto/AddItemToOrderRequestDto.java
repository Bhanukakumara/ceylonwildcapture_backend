package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto;

import jakarta.validation.constraints.NotNull;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.LicenseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for adding an item to an existing order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddItemToOrderRequestDto {

    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "Photo ID is required")
    private Long photoId;

    @NotNull(message = "License type is required")
    private LicenseType licenseType;
}
