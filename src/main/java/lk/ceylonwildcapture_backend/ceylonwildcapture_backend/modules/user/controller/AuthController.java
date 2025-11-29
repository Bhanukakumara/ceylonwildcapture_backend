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

        Map<String, Object> authResult = authenticationService.authenticate(loginRequest);

        AuthResponseDto response = AuthResponseDto.builder()
                .accessToken((String) authResult.get("accessToken"))
                .refreshToken((String) authResult.get("refreshToken"))
                .tokenType((String) authResult.get("tokenType"))
                .expiresIn((Long) authResult.get("expiresIn"))
                .user(convertToUserInfoDto((Map<String, Object>) authResult.get("user")))
                .build();

        log.info("User logged in successfully: {}", loginRequest.getUsernameOrEmail());
        return ResponseEntity.ok(response);
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
                loginRequest.getPassword()
        );

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
     * Convert user map to UserInfoDto.
     *
     * @param userMap user information map
     * @return UserInfoDto
     */
    private AuthResponseDto.UserInfoDto convertToUserInfoDto(Map<String, Object> userMap) {
        return AuthResponseDto.UserInfoDto.builder()
                .id((Long) userMap.get("id"))
                .username((String) userMap.get("username"))
                .email((String) userMap.get("email"))
                .firstName((String) userMap.get("firstName"))
                .lastName((String) userMap.get("lastName"))
                .role((lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.UserRole) userMap.get("role"))
                .isActive((Boolean) userMap.get("isActive"))
                .emailVerified((Boolean) userMap.get("emailVerified"))
                .build();
    }
}
