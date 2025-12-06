package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for JWT token operations.
 * Provides methods for generating, validating, and extracting information from
 * JWT tokens.
 */
@Component
@Slf4j
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Extract username from JWT token.
     *
     * @param token JWT token
     * @return username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract expiration date from JWT token.
     *
     * @param token JWT token
     * @return expiration date
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract user role from JWT token.
     *
     * @param token JWT token
     * @return user role
     */
    public UserRole extractRole(String token) {
        String role = extractClaim(token, claims -> claims.get("role", String.class));
        return UserRole.valueOf(role);
    }

    /**
     * Extract specific claim from JWT token.
     *
     * @param token          JWT token
     * @param claimsResolver function to extract claim
     * @param <T>            claim type
     * @return claim value
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims from JWT token.
     *
     * @param token JWT token
     * @return all claims
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            log.error("JWT token is malformed: {}", e.getMessage());
            throw e;
        } catch (SecurityException e) {
            log.error("JWT signature validation failed: {}", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Check if JWT token is expired.
     *
     * @param token JWT token
     * @return true if expired, false otherwise
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generate JWT access token for user.
     *
     * @param username username
     * @param role     user role
     * @return JWT token
     */
    public String generateAccessToken(String username, UserRole role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role.name());
        return createToken(claims, username, jwtExpiration);
    }

    /**
     * Generate JWT refresh token for user.
     *
     * @param username username
     * @return refresh token
     */
    public String generateRefreshToken(String username) {
        return createToken(new HashMap<>(), username, refreshExpiration);
    }

    /**
     * Create JWT token with claims and expiration.
     *
     * @param claims     claims to include in token
     * @param subject    token subject (username)
     * @param expiration token expiration time
     * @return JWT token
     */
    private String createToken(Map<String, Object> claims, String subject, Long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Validate JWT token against user details.
     *
     * @param token       JWT token
     * @param userDetails user details
     * @return true if valid, false otherwise
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Validate JWT token format and signature.
     *
     * @param token JWT token
     * @return true if valid format, false otherwise
     */
    public Boolean validateTokenFormat(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Get token expiration time in milliseconds.
     *
     * @return expiration time
     */
    public Long getJwtExpiration() {
        return jwtExpiration;
    }

    /**
     * Get refresh token expiration time in milliseconds.
     *
     * @return refresh expiration time
     */
    public Long getRefreshExpiration() {
        return refreshExpiration;
    }
}
