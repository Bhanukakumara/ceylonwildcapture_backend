package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for billing information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingInfoDto {

    @NotBlank(message = "Billing name is required")
    @Size(max = 100, message = "Billing name must not exceed 100 characters")
    private String billingName;

    @NotBlank(message = "Billing email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String billingEmail;

    @Size(max = 500, message = "Address must not exceed 500 characters")
    private String billingAddress;

    @Size(max = 100, message = "City must not exceed 100 characters")
    private String billingCity;

    @Size(max = 100, message = "State must not exceed 100 characters")
    private String billingState;

    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String billingCountry;

    @Size(max = 20, message = "ZIP code must not exceed 20 characters")
    private String billingZip;
}
