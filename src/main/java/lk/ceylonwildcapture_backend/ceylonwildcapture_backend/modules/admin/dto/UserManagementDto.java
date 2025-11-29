package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto;

import jakarta.validation.constraints.NotNull;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for admin user management operations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserManagementDto {

    @NotNull(message = "User ID is required")
    private Long userId;

    private UserRole newRole;

    private Boolean isActive;

    private Boolean isBanned;

    private String banReason;

    private Boolean emailVerified;

    private String adminNotes;
}
