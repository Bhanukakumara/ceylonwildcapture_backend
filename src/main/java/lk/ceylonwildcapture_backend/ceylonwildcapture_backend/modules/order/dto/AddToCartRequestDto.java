package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.LicenseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartRequestDto {

    @NotNull(message = "Photo ID is required")
    private Long photoId;

    @NotNull(message = "License type is required")
    private LicenseType licenseType;
}
