package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.UserRole;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.UserManagementDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service interface for admin user management operations.
 */
public interface UserManagementService {

    /**
     * Perform user management action (ban, activate, change role, etc.).
     *
     * @param managementDto user management details
     * @param adminId ID of the admin performing the action
     * @return updated user
     */
    User manageUser(UserManagementDto managementDto, Long adminId);

    /**
     * Ban a user.
     *
     * @param userId ID of the user to ban
     * @param reason ban reason
     * @param adminId ID of the admin
     * @return banned user
     */
    User banUser(Long userId, String reason, Long adminId);

    /**
     * Unban a user.
     *
     * @param userId ID of the user to unban
     * @param adminId ID of the admin
     * @return unbanned user
     */
    User unbanUser(Long userId, Long adminId);

    /**
     * Activate a user account.
     *
     * @param userId ID of the user
     * @param adminId ID of the admin
     * @return activated user
     */
    User activateUser(Long userId, Long adminId);

    /**
     * Deactivate a user account.
     *
     * @param userId ID of the user
     * @param adminId ID of the admin
     * @return deactivated user
     */
    User deactivateUser(Long userId, Long adminId);

    /**
     * Assign a new role to a user.
     *
     * @param userId ID of the user
     * @param newRole new role to assign
     * @param adminId ID of the admin
     * @return updated user
     */
    User assignRole(Long userId, UserRole newRole, Long adminId);

    /**
     * Get all verified photographers.
     *
     * @param pageable pagination parameters
     * @return page of verified photographers
     */
    Page<User> getVerifiedPhotographers(Pageable pageable);

    /**
     * Get all unverified photographers.
     *
     * @param pageable pagination parameters
     * @return page of unverified photographers
     */
    Page<User> getUnverifiedPhotographers(Pageable pageable);

    /**
     * Get all banned users.
     *
     * @param pageable pagination parameters
     * @return page of banned users
     */
    Page<User> getBannedUsers(Pageable pageable);

    /**
     * Get all inactive users.
     *
     * @param pageable pagination parameters
     * @return page of inactive users
     */
    Page<User> getInactiveUsers(Pageable pageable);

    /**
     * Verify a photographer profile.
     *
     * @param userId ID of the photographer
     * @param adminId ID of the admin
     * @return updated user
     */
    User verifyPhotographer(Long userId, Long adminId);

    /**
     * Unverify a photographer profile.
     *
     * @param userId ID of the photographer
     * @param adminId ID of the admin
     * @return updated user
     */
    User unverifyPhotographer(Long userId, Long adminId);

    /**
     * Get user details by ID.
     *
     * @param userId ID of the user
     * @return user details
     */
    Optional<User> getUserDetails(Long userId);

    /**
     * Get all users with filters.
     *
     * @param role filter by role (optional)
     * @param isActive filter by active status (optional)
     * @param pageable pagination parameters
     * @return page of users
     */
    Page<User> getAllUsers(UserRole role, Boolean isActive, Pageable pageable);
}
