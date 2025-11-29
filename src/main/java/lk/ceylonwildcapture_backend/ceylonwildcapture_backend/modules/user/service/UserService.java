package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.UserRole;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for User management operations.
 * Defines business logic for user CRUD, profile management, and user queries.
 */
public interface UserService {

    /**
     * Create a new user.
     *
     * @param userCreateDto the user creation DTO
     * @return the created user response DTO
     * @throws IllegalArgumentException if user data is invalid
     */
    UserResponseDto createUser(UserCreateDto userCreateDto);

    /**
     * Create the first admin user (no authentication required).
     * This method should only be used for initial setup when no admin users exist.
     *
     * @param userCreateDto the admin user creation DTO
     * @return the created admin user response DTO
     * @throws IllegalArgumentException if admin data is invalid or admin already exists
     */
    UserResponseDto createFirstAdmin(UserCreateDto userCreateDto);

    /**
     * Update an existing user.
     *
     * @param userId the user ID
     * @param userUpdateDto the updated user data
     * @return the updated user response DTO
     * @throws IllegalArgumentException if user not found
     */
    UserResponseDto updateUser(Long userId, UserUpdateDto userUpdateDto);

    /**
     * Get user by ID.
     *
     * @param userId the user ID
     * @return user response DTO if found, null otherwise
     */
    UserResponseDto getUserById(Long userId);

    /**
     * Get user by email.
     *
     * @param email the email address
     * @return user response DTO if found, null otherwise
     */
    UserResponseDto getUserByEmail(String email);

    /**
     * Get user by username.
     *
     * @param username the username
     * @return user response DTO if found, null otherwise
     */
    UserResponseDto getUserByUsername(String username);

    /**
     * Get all users with pagination.
     *
     * @param pageable pagination information
     * @return page of user response DTOs
     */
    Page<UserResponseDto> getAllUsers(Pageable pageable);

    /**
     * Get users by role.
     *
     * @param role the user role
     * @param pageable pagination information
     * @return page of user response DTOs with specified role
     */
    Page<UserResponseDto> getUsersByRole(UserRole role, Pageable pageable);

    /**
     * Get active users.
     *
     * @param isActive the active status
     * @param pageable pagination information
     * @return page of user response DTOs
     */
    Page<UserResponseDto> getActiveUsers(Boolean isActive, Pageable pageable);

    /**
     * Search users by username or email.
     *
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of user response DTOs matching search criteria
     */
    Page<UserResponseDto> searchUsers(String searchTerm, Pageable pageable);

    /**
     * Delete user by ID (soft delete - set inactive).
     *
     * @param userId the user ID
     * @throws IllegalArgumentException if user not found
     */
    void deleteUser(Long userId);

    /**
     * Permanently delete user by ID.
     *
     * @param userId the user ID
     * @throws IllegalArgumentException if user not found
     */
    void permanentlyDeleteUser(Long userId);

    /**
     * Activate a user account.
     *
     * @param userId the user ID
     * @return the activated user response DTO
     * @throws IllegalArgumentException if user not found
     */
    UserResponseDto activateUser(Long userId);

    /**
     * Deactivate a user account.
     *
     * @param userId the user ID
     * @return the deactivated user response DTO
     * @throws IllegalArgumentException if user not found
     */
    UserResponseDto deactivateUser(Long userId);

    /**
     * Verify user email.
     *
     * @param userId the user ID
     * @return the user response DTO with verified email
     * @throws IllegalArgumentException if user not found
     */
    UserResponseDto verifyEmail(Long userId);

    /**
     * Update user password.
     *
     * @param userId the user ID
     * @param passwordChangeDto the password change DTO
     * @return the updated user response DTO
     * @throws IllegalArgumentException if user not found or old password incorrect
     */
    UserResponseDto updatePassword(Long userId, PasswordChangeDto passwordChangeDto);

    /**
     * Reset user password.
     *
     * @param passwordResetDto the password reset DTO
     * @return the updated user response DTO
     * @throws IllegalArgumentException if user not found
     */
    UserResponseDto resetPassword(PasswordResetDto passwordResetDto);

    /**
     * Update user profile information.
     *
     * @param userId the user ID
     * @param userProfileDto the profile update DTO
     * @return the updated user response DTO
     * @throws IllegalArgumentException if user not found
     */
    UserResponseDto updateProfile(Long userId, UserProfileDto userProfileDto);

    /**
     * Update user profile image.
     *
     * @param userId the user ID
     * @param profileImageUrl the profile image URL
     * @return the updated user response DTO
     * @throws IllegalArgumentException if user not found
     */
    UserResponseDto updateProfileImage(Long userId, String profileImageUrl);

    /**
     * Update user last login timestamp.
     *
     * @param userId the user ID
     * @return the updated user response DTO
     * @throws IllegalArgumentException if user not found
     */
    UserResponseDto updateLastLogin(Long userId);

    /**
     * Check if email exists.
     *
     * @param email the email address
     * @return true if email exists
     */
    boolean emailExists(String email);

    /**
     * Check if username exists.
     *
     * @param username the username
     * @return true if username exists
     */
    boolean usernameExists(String username);

    /**
     * Count users by role.
     *
     * @param role the user role
     * @return count of users with specified role
     */
    long countUsersByRole(UserRole role);

    /**
     * Count active users.
     *
     * @return count of active users
     */
    long countActiveUsers();

    /**
     * Get users created within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return list of user response DTOs created within date range
     */
    List<UserResponseDto> getUsersCreatedBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Get top photographers by sales.
     *
     * @param pageable pagination information
     * @return page of user response DTOs for top photographers
     */
    Page<UserResponseDto> getTopPhotographers(Pageable pageable);

    /**
     * Get verified photographers.
     *
     * @param pageable pagination information
     * @return page of user response DTOs for verified photographers
     */
    Page<UserResponseDto> getVerifiedPhotographers(Pageable pageable);

    /**
     * Assign role to user.
     *
     * @param userId the user ID
     * @param role the new role
     * @return the updated user response DTO
     * @throws IllegalArgumentException if user not found
     */
    UserResponseDto assignRole(Long userId, UserRole role);
}
