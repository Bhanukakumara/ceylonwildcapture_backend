package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.UserRole;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for User API responses.
 * Contains user information safe to expose in API responses.
 * Excludes sensitive data like passwords.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private UserRole role;

    private Boolean isActive;

    private Boolean emailVerified;

    private String profileImageUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime lastLogin;

    /**
     * Static method to convert User entity to UserResponseDto.
     *
     * @param user the user entity
     * @return UserResponseDto
     */
    public static UserResponseDto fromEntity(User user) {
        if (user == null) {
            return null;
        }

        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .emailVerified(user.getEmailVerified())
                .profileImageUrl(user.getProfileImageUrl())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .lastLogin(user.getLastLogin())
                .build();
    }
}
