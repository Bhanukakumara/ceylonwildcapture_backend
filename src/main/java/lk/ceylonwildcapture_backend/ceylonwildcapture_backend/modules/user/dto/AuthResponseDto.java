package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Authentication responses.
 * Contains JWT tokens and user information after successful login.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private Long expiresIn;

    private UserInfoDto user;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoDto {
        private Long id;
        private String username;
        private String email;
        private String firstName;
        private String lastName;
        private UserRole role;
        private Boolean isActive;
        private Boolean emailVerified;
        private LocalDateTime lastLogin;
    }
}
