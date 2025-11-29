package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for User Update requests.
 * Contains fields that can be updated in user operations.
 * All fields are optional to allow partial updates.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phoneNumber;

    private UserRole role;

    private Boolean isActive;

    private Boolean emailVerified;

    @Size(max = 255, message = "Profile image URL must not exceed 255 characters")
    private String profileImageUrl;
}
