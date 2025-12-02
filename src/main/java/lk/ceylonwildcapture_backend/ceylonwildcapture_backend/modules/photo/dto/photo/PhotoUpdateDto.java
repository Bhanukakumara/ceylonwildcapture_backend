package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.photo;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for updating an existing photo.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoUpdateDto {

    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

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

    @Size(max = 200, message = "Location must not exceed 200 characters")
    private String location;

    private List<Long> tagIds;

    private List<Long> categoryIds;

    private Boolean isActive;

    private Boolean isFeatured;
}
