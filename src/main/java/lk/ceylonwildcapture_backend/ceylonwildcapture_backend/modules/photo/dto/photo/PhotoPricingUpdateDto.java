package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.photo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for updating photo pricing.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoPricingUpdateDto {

    @DecimalMin(value = "0.0", inclusive = false, message = "Base price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Base price must have at most 10 integer digits and 2 decimal places")
    private BigDecimal basePrice;

    @DecimalMin(value = "0.0", inclusive = false, message = "Commercial price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Commercial price must have at most 10 integer digits and 2 decimal places")
    private BigDecimal commercialPrice;

    @DecimalMin(value = "0.0", inclusive = false, message = "Editorial price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Editorial price must have at most 10 integer digits and 2 decimal places")
    private BigDecimal editorialPrice;

    @DecimalMin(value = "0.0", inclusive = false, message = "Extended price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Extended price must have at most 10 integer digits and 2 decimal places")
    private BigDecimal extendedPrice;
}
