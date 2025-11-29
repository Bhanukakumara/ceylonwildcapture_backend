package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.security;

import io.jsonwebtoken.Claims;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;

/**
 * Interface for JWT token generation, validation, and claims extraction.
 * Provides methods for managing JWT access and refresh tokens.
 */
public interface JwtTokenProvider {

    /**
     * Generate JWT access token for user.
     *
     * @param user the user entity
     * @return the generated JWT access token
     */
    String generateAccessToken(User user);

    /**
     * Generate JWT refresh token for user.
     *
     * @param user the user entity
     * @return the generated JWT refresh token
     */
    String generateRefreshToken(User user);

    /**
     * Generate JWT token with custom claims.
     *
     * @param claims custom claims to include in token
     * @param user the user entity
     * @return the generated JWT token
     */
    String generateToken(Map<String, Object> claims, User user);

    /**
     * Extract username (email) from JWT token.
     *
     * @param token the JWT token
     * @return the username (email)
     */
    String extractUsername(String token);

    /**
     * Extract user ID from JWT token.
     *
     * @param token the JWT token
     * @return the user ID
     */
    Long extractUserId(String token);

    /**
     * Extract user role from JWT token.
     *
     * @param token the JWT token
     * @return the user role
     */
    String extractUserRole(String token);

    /**
     * Extract token expiration date.
     *
     * @param token the JWT token
     * @return the expiration date
     */
    Date extractExpiration(String token);

    /**
     * Extract all claims from JWT token.
     *
     * @param token the JWT token
     * @return the claims
     */
    Claims extractAllClaims(String token);

    /**
     * Extract a specific claim from JWT token.
     *
     * @param token the JWT token
     * @param claimKey the claim key
     * @param <T> the claim type
     * @return the claim value
     */
    <T> T extractClaim(String token, String claimKey);

    /**
     * Validate JWT token against user details.
     *
     * @param token the JWT token
     * @param userDetails the user details
     * @return true if token is valid
     */
    boolean validateToken(String token, UserDetails userDetails);

    /**
     * Validate JWT token.
     *
     * @param token the JWT token
     * @return true if token is valid
     */
    boolean validateToken(String token);

    /**
     * Check if JWT token is expired.
     *
     * @param token the JWT token
     * @return true if token is expired
     */
    boolean isTokenExpired(String token);

    /**
     * Check if JWT token is valid for user.
     *
     * @param token the JWT token
     * @param user the user entity
     * @return true if token is valid for the user
     */
    boolean isTokenValidForUser(String token, User user);

    /**
     * Get token expiration time in milliseconds.
     *
     * @return the access token expiration time
     */
    long getAccessTokenExpiration();

    /**
     * Get refresh token expiration time in milliseconds.
     *
     * @return the refresh token expiration time
     */
    long getRefreshTokenExpiration();

    /**
     * Invalidate JWT token (add to blacklist).
     *
     * @param token the JWT token to invalidate
     */
    void invalidateToken(String token);

    /**
     * Check if JWT token is blacklisted.
     *
     * @param token the JWT token
     * @return true if token is blacklisted
     */
    boolean isTokenBlacklisted(String token);

    /**
     * Extract token from Authorization header.
     *
     * @param authorizationHeader the Authorization header value
     * @return the JWT token without "Bearer " prefix
     */
    String extractTokenFromHeader(String authorizationHeader);

    /**
     * Refresh access token using refresh token.
     *
     * @param refreshToken the refresh token
     * @return new access token
     * @throws IllegalArgumentException if refresh token is invalid
     */
    String refreshAccessToken(String refreshToken);
}
