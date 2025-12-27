package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.dto.*;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller for authentication operations.
 * Provides endpoints for login, registration, and token management.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.service.UserService userService;

    /**
     * Authenticate user and generate JWT tokens.
     *
     * @param loginRequest login credentials
     * @return authentication response with tokens
     */
    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT tokens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody UserLoginDto loginRequest) {
        log.info("Login attempt for user: {}", loginRequest.getUsernameOrEmail());

        AuthResponseDto authResponse = authenticationService.authenticate(loginRequest);

        log.info("User logged in successfully: {}", loginRequest.getUsernameOrEmail());
        return ResponseEntity.ok(authResponse);
    }

    /**
     * Refresh access token using refresh token.
     *
     * @param refreshRequest refresh token request
     * @return new access token
     */
    @PostMapping("/refresh")
    @Operation(summary = "Refresh token", description = "Generate new access token using refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid refresh token"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<Map<String, Object>> refreshToken(@Valid @RequestBody RefreshTokenRequestDto refreshRequest) {
        log.debug("Token refresh request received");

        Map<String, Object> tokenResult = authenticationService.refreshToken(refreshRequest.getRefreshToken());
        log.debug("Token refreshed successfully");
        return ResponseEntity.ok(tokenResult);
    }

    /**
     * Validate user credentials without generating tokens.
     *
     * @param loginRequest login credentials
     * @return validation result
     */
    @PostMapping("/validate")
    @Operation(summary = "Validate credentials", description = "Validate user credentials without generating tokens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Credentials valid"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<Map<String, Boolean>> validateCredentials(@Valid @RequestBody UserLoginDto loginRequest) {
        log.debug("Validating credentials for user: {}", loginRequest.getUsernameOrEmail());

        boolean isValid = authenticationService.validateCredentials(
                loginRequest.getUsernameOrEmail(),
                loginRequest.getPassword());

        Map<String, Boolean> response = Map.of("valid", isValid);

        if (isValid) {
            log.debug("Credentials validated successfully for user: {}", loginRequest.getUsernameOrEmail());
            return ResponseEntity.ok(response);
        } else {
            log.debug("Invalid credentials for user: {}", loginRequest.getUsernameOrEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    /**
     * Verify email address using verification token.
     *
     * @param token the verification token from email link
     * @return success message
     */
    @GetMapping("/verify-email")
    @Operation(summary = "Verify email", description = "Verify user email address using token from email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email verified successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or expired token")
    })
    public ResponseEntity<Map<String, Object>> verifyEmail(@RequestParam String token) {
        log.info("Email verification request received");

        try {
            UserResponseDto user = userService.verifyEmailByToken(token);
            
            Map<String, Object> response = Map.of(
                "success", true,
                "message", "Email verified successfully! You can now log in.",
                "user", user
            );
            
            log.info("Email verified successfully for user: {}", user.getEmail());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.warn("Email verification failed: {}", e.getMessage());
            Map<String, Object> response = Map.of(
                "success", false,
                "message", e.getMessage()
            );
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Resend verification email.
     *
     * @param email the user email
     * @return success message
     */
    @PostMapping("/resend-verification")
    @Operation(summary = "Resend verification email", description = "Resend email verification link to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verification email sent"),
            @ApiResponse(responseCode = "400", description = "Email already verified or not found")
    })
    public ResponseEntity<Map<String, String>> resendVerification(@RequestParam String email) {
        log.info("Resend verification request for email: {}", email);

        try {
            userService.resendVerificationEmail(email);
            
            Map<String, String> response = Map.of(
                "success", "true",
                "message", "Verification email sent successfully. Please check your inbox."
            );
            
            log.info("Verification email resent to: {}", email);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.warn("Resend verification failed: {}", e.getMessage());
            Map<String, String> response = Map.of(
                "success", "false",
                "message", e.getMessage()
            );
            return ResponseEntity.badRequest().body(response);
        }
    }
}
