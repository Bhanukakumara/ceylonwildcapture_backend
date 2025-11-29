package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;

/**
 * Service interface for Authentication operations.
 * Defines business logic for user registration, login, token management, and password recovery.
 */
public interface AuthService {

    /**
     * Register a new user.
     *
     * @param registerRequest the registration request DTO (placeholder)
     * @return authentication response with user details and JWT token
     * @throws IllegalArgumentException if registration data is invalid or user already exists
     */
    Object register(Object registerRequest);

    /**
     * Authenticate user and generate JWT token.
     *
     * @param loginRequest the login request DTO (placeholder)
     * @return authentication response with user details and JWT token
     * @throws IllegalArgumentException if credentials are invalid
     */
    Object login(Object loginRequest);

    /**
     * Refresh JWT access token using refresh token.
     *
     * @param refreshToken the refresh token
     * @return authentication response with new access token
     * @throws IllegalArgumentException if refresh token is invalid or expired
     */
    Object refreshToken(String refreshToken);

    /**
     * Logout user and invalidate tokens.
     *
     * @param userId the user ID
     * @param accessToken the access token to invalidate
     * @throws IllegalArgumentException if user not found
     */
    void logout(Long userId, String accessToken);

    /**
     * Verify user email with verification token.
     *
     * @param verificationToken the email verification token
     * @return the verified user
     * @throws IllegalArgumentException if token is invalid or expired
     */
    User verifyEmail(String verificationToken);

    /**
     * Resend email verification token.
     *
     * @param email the user email
     * @throws IllegalArgumentException if user not found or already verified
     */
    void resendVerificationEmail(String email);

    /**
     * Initiate password reset process.
     *
     * @param email the user email
     * @throws IllegalArgumentException if user not found
     */
    void initiatePasswordReset(String email);

    /**
     * Reset password using reset token.
     *
     * @param resetToken the password reset token
     * @param newPassword the new password
     * @return the user with updated password
     * @throws IllegalArgumentException if token is invalid or expired
     */
    User resetPassword(String resetToken, String newPassword);

    /**
     * Validate password reset token.
     *
     * @param resetToken the password reset token
     * @return true if token is valid
     */
    boolean validatePasswordResetToken(String resetToken);

    /**
     * Change user password (requires current password).
     *
     * @param userId the user ID
     * @param currentPassword the current password
     * @param newPassword the new password
     * @return the user with updated password
     * @throws IllegalArgumentException if current password is incorrect
     */
    User changePassword(Long userId, String currentPassword, String newPassword);

    /**
     * Validate user credentials.
     *
     * @param email the email or username
     * @param password the password
     * @return the authenticated user
     * @throws IllegalArgumentException if credentials are invalid
     */
    User validateCredentials(String email, String password);

    /**
     * Generate email verification token for user.
     *
     * @param userId the user ID
     * @return the verification token
     * @throws IllegalArgumentException if user not found
     */
    String generateEmailVerificationToken(Long userId);

    /**
     * Generate password reset token for user.
     *
     * @param userId the user ID
     * @return the password reset token
     * @throws IllegalArgumentException if user not found
     */
    String generatePasswordResetToken(Long userId);

    /**
     * Check if user account is locked.
     *
     * @param email the user email
     * @return true if account is locked
     */
    boolean isAccountLocked(String email);

    /**
     * Lock user account.
     *
     * @param userId the user ID
     * @throws IllegalArgumentException if user not found
     */
    void lockAccount(Long userId);

    /**
     * Unlock user account.
     *
     * @param userId the user ID
     * @throws IllegalArgumentException if user not found
     */
    void unlockAccount(Long userId);
}
