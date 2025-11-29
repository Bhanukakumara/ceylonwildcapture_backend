package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.security;

/**
 * Interface for password encoding and verification operations.
 * Provides methods for hashing passwords and verifying password matches.
 */
public interface PasswordEncoderService {

    /**
     * Encode (hash) a raw password.
     *
     * @param rawPassword the raw password to encode
     * @return the encoded (hashed) password
     */
    String encodePassword(String rawPassword);

    /**
     * Verify if a raw password matches an encoded password.
     *
     * @param rawPassword the raw password
     * @param encodedPassword the encoded (hashed) password
     * @return true if passwords match
     */
    boolean matches(String rawPassword, String encodedPassword);

    /**
     * Check if a password is strong enough.
     *
     * @param password the password to check
     * @return true if password meets strength requirements
     */
    boolean isPasswordStrong(String password);

    /**
     * Validate password strength and return errors.
     *
     * @param password the password to validate
     * @return validation error message, or null if valid
     */
    String validatePasswordStrength(String password);

    /**
     * Generate a random secure password.
     *
     * @param length the desired password length
     * @return the generated password
     */
    String generateRandomPassword(int length);

    /**
     * Generate a secure password reset token.
     *
     * @return the password reset token
     */
    String generatePasswordResetToken();

    /**
     * Hash a token for secure storage.
     *
     * @param token the token to hash
     * @return the hashed token
     */
    String hashToken(String token);

    /**
     * Check if password needs rehashing (e.g., after algorithm upgrade).
     *
     * @param encodedPassword the encoded password
     * @return true if password should be rehashed
     */
    boolean needsRehashing(String encodedPassword);
}
