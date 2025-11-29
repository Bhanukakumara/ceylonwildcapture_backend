package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.util;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;

import java.util.List;

/**
 * Interface for user data validation operations.
 * Provides methods for validating user fields, credentials, and business rules.
 */
public interface UserValidator {

    /**
     * Validate user entity before creation or update.
     *
     * @param user the user entity to validate
     * @return list of validation error messages (empty if valid)
     */
    List<String> validateUser(User user);

    /**
     * Validate email address format and availability.
     *
     * @param email the email address to validate
     * @return validation error message, or null if valid
     */
    String validateEmail(String email);

    /**
     * Validate username format and availability.
     *
     * @param username the username to validate
     * @return validation error message, or null if valid
     */
    String validateUsername(String username);

    /**
     * Validate password strength and format.
     *
     * @param password the password to validate
     * @return validation error message, or null if valid
     */
    String validatePassword(String password);

    /**
     * Validate phone number format.
     *
     * @param phoneNumber the phone number to validate
     * @return validation error message, or null if valid
     */
    String validatePhoneNumber(String phoneNumber);

    /**
     * Validate first name.
     *
     * @param firstName the first name to validate
     * @return validation error message, or null if valid
     */
    String validateFirstName(String firstName);

    /**
     * Validate last name.
     *
     * @param lastName the last name to validate
     * @return validation error message, or null if valid
     */
    String validateLastName(String lastName);

    /**
     * Check if email is already in use.
     *
     * @param email the email address to check
     * @return true if email is already in use
     */
    boolean isEmailTaken(String email);

    /**
     * Check if username is already in use.
     *
     * @param username the username to check
     * @return true if username is already in use
     */
    boolean isUsernameTaken(String username);

    /**
     * Check if email is already in use by another user.
     *
     * @param email the email address to check
     * @param userId the user ID to exclude from check
     * @return true if email is taken by another user
     */
    boolean isEmailTakenByOtherUser(String email, Long userId);

    /**
     * Check if username is already in use by another user.
     *
     * @param username the username to check
     * @param userId the user ID to exclude from check
     * @return true if username is taken by another user
     */
    boolean isUsernameTakenByOtherUser(String username, Long userId);

    /**
     * Validate password confirmation match.
     *
     * @param password the password
     * @param confirmPassword the password confirmation
     * @return validation error message, or null if passwords match
     */
    String validatePasswordConfirmation(String password, String confirmPassword);

    /**
     * Validate profile image URL format.
     *
     * @param imageUrl the image URL to validate
     * @return validation error message, or null if valid
     */
    String validateImageUrl(String imageUrl);

    /**
     * Check if user can be deleted.
     *
     * @param userId the user ID
     * @return validation error message, or null if user can be deleted
     */
    String validateUserDeletion(Long userId);

    /**
     * Check if user can change role.
     *
     * @param userId the user ID
     * @param newRole the new role
     * @return validation error message, or null if role change is allowed
     */
    String validateRoleChange(Long userId, String newRole);

    /**
     * Sanitize user input to prevent XSS attacks.
     *
     * @param input the user input to sanitize
     * @return sanitized input
     */
    String sanitizeInput(String input);

    /**
     * Validate registration request data.
     *
     * @param registerRequest the registration request DTO (placeholder)
     * @return list of validation error messages (empty if valid)
     */
    List<String> validateRegistrationRequest(Object registerRequest);

    /**
     * Validate login request data.
     *
     * @param loginRequest the login request DTO (placeholder)
     * @return list of validation error messages (empty if valid)
     */
    List<String> validateLoginRequest(Object loginRequest);

    /**
     * Validate update profile request data.
     *
     * @param updateRequest the update profile request DTO (placeholder)
     * @return list of validation error messages (empty if valid)
     */
    List<String> validateUpdateProfileRequest(Object updateRequest);

    /**
     * Check if email domain is allowed.
     *
     * @param email the email address
     * @return true if email domain is allowed
     */
    boolean isEmailDomainAllowed(String email);

    /**
     * Check if password is commonly used or weak.
     *
     * @param password the password to check
     * @return true if password is commonly used
     */
    boolean isPasswordCommonlyUsed(String password);

    /**
     * Validate age requirement for user registration.
     *
     * @param dateOfBirth the date of birth
     * @return validation error message, or null if age is valid
     */
    String validateAge(String dateOfBirth);
}
