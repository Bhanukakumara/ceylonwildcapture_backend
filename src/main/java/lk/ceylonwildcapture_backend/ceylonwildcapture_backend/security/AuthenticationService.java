package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.security;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.repository.UserRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.dto.UserLoginDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.dto.AuthResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for handling user authentication operations.
 * Provides login functionality and JWT token generation.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    /**
     * Authenticate user and generate JWT tokens.
     *
     * @param loginRequest login credentials
     * @return authentication response with tokens
     * @throws BadCredentialsException if authentication fails
     */
    @Transactional
    public AuthResponseDto authenticate(UserLoginDto loginRequest) {
        String usernameOrEmail = loginRequest.getUsernameOrEmail();

        log.debug("Attempting authentication for user: {}", usernameOrEmail);

        try {
            // Validate credentials (this will check password and status like enabled/disabled)
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usernameOrEmail, loginRequest.getPassword())
            );

            log.debug("Authentication successful for user: {}", usernameOrEmail);

            // Get user details
            User user = userRepository.findByEmailOrUsername(usernameOrEmail, usernameOrEmail)
                    .orElseThrow(() -> new BadCredentialsException("User not found"));

            // Generate tokens
            String accessToken = jwtTokenUtil.generateAccessToken(user.getUsername(), user.getRole());
            String refreshToken = jwtTokenUtil.generateRefreshToken(user.getUsername());

            // Update last login
            user.setLastLogin(java.time.LocalDateTime.now());
            userRepository.save(user);

            // Build response
            return AuthResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .expiresIn(jwtTokenUtil.getJwtExpiration() / 1000)
                    .user(createUserResponse(user))
                    .build();

        } catch (DisabledException e) {
            log.warn("Login attempted for unverified user: {}", usernameOrEmail);
            throw new DisabledException("Your email address is not verified. Please verify your email before logging in.");
        } catch (AuthenticationException e) {
            log.warn("Authentication failed for user: {}: {}", usernameOrEmail, e.getMessage());
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    /**
     * Refresh access token using refresh token.
     *
     * @param refreshToken refresh token
     * @return new access token
     * @throws BadCredentialsException if refresh token is invalid
     */
    @Transactional(readOnly = true)
    public Map<String, Object> refreshToken(String refreshToken) {
        log.debug("Attempting to refresh token");

        if (!jwtTokenUtil.validateTokenFormat(refreshToken)) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        String username = jwtTokenUtil.extractUsername(refreshToken);

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(refreshToken, userDetails)) {
                User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new BadCredentialsException("User not found"));

                String newAccessToken = jwtTokenUtil.generateAccessToken(username, user.getRole());

                Map<String, Object> response = new HashMap<>();
                response.put("accessToken", newAccessToken);
                response.put("tokenType", "Bearer");
                response.put("expiresIn", jwtTokenUtil.getJwtExpiration() / 1000);

                log.debug("Token refreshed successfully for user: {}", username);
                return response;
            } else {
                throw new BadCredentialsException("Invalid refresh token");
            }
        } catch (Exception e) {
            log.warn("Token refresh failed: {}", e.getMessage());
            throw new BadCredentialsException("Failed to refresh token", e);
        }
    }

    /**
     * Create user response object for authentication.
     *
     * @param user User entity
     * @return UserInfoDto
     */
    private AuthResponseDto.UserInfoDto createUserResponse(User user) {
        return AuthResponseDto.UserInfoDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .emailVerified(user.getEmailVerified())
                .build();
    }

    /**
     * Validate user credentials without generating tokens.
     *
     * @param usernameOrEmail username or email
     * @param password        password
     * @return true if credentials are valid
     */
    @Transactional(readOnly = true)
    public boolean validateCredentials(String usernameOrEmail, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usernameOrEmail, password));
            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }
}
