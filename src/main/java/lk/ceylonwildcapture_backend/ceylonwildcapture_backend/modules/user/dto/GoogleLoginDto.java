package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Google login/signup requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleLoginDto {

    @NotBlank(message = "ID token is required")
    private String idToken;

    private UserRole role; // Optional, defaults to BUYER if new user
}
