package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.controller;

import jakarta.validation.Valid;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.UserRole;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.dto.*;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for User-related operations.
 * Handles user management, profile updates, and queries.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    // ------------------------------
    // Create User
    // ------------------------------
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        return ResponseEntity.ok(userService.createUser(userCreateDto));
    }

    // ------------------------------
    // Update User
    // ------------------------------
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(userService.updateUser(userId, userUpdateDto));
    }

    // ------------------------------
    // Get User by ID
    // ------------------------------
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        UserResponseDto user = userService.getUserById(userId);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // ------------------------------
    // Get User by Email
    // ------------------------------
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        UserResponseDto user = userService.getUserByEmail(email);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // ------------------------------
    // Get All Users (Paginated)
    // ------------------------------
    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    // ------------------------------
    // Get Users by Role
    // ------------------------------
    @GetMapping("/role/{role}")
    public ResponseEntity<Page<UserResponseDto>> getUsersByRole(
            @PathVariable UserRole role,
            Pageable pageable) {
        return ResponseEntity.ok(userService.getUsersByRole(role, pageable));
    }

    // ------------------------------
    // Search Users
    // ------------------------------
    @GetMapping("/search")
    public ResponseEntity<Page<UserResponseDto>> searchUsers(
            @RequestParam String term,
            Pageable pageable) {
        return ResponseEntity.ok(userService.searchUsers(term, pageable));
    }

    // ------------------------------
    // Soft Delete User
    // ------------------------------
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    // ------------------------------
    // Permanently Delete User
    // ------------------------------
    @DeleteMapping("/{userId}/hard")
    public ResponseEntity<Void> permanentlyDeleteUser(@PathVariable Long userId) {
        userService.permanentlyDeleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    // ------------------------------
    // Activate User
    // ------------------------------
    @PutMapping("/{userId}/activate")
    public ResponseEntity<UserResponseDto> activateUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.activateUser(userId));
    }

    // ------------------------------
    // Deactivate User
    // ------------------------------
    @PutMapping("/{userId}/deactivate")
    public ResponseEntity<UserResponseDto> deactivateUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.deactivateUser(userId));
    }

    // ------------------------------
    // Verify Email
    // ------------------------------
    @PutMapping("/{userId}/verify-email")
    public ResponseEntity<UserResponseDto> verifyEmail(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.verifyEmail(userId));
    }

    // ------------------------------
    // Update Password
    // ------------------------------
    @PutMapping("/{userId}/password")
    public ResponseEntity<UserResponseDto> updatePassword(
            @PathVariable Long userId,
            @Valid @RequestBody PasswordChangeDto passwordChangeDto) {
        return ResponseEntity.ok(userService.updatePassword(userId, passwordChangeDto));
    }

    // ------------------------------
    // Reset Password
    // ------------------------------
    @PutMapping("/reset-password")
    public ResponseEntity<UserResponseDto> resetPassword(
            @Valid @RequestBody PasswordResetDto passwordResetDto) {
        return ResponseEntity.ok(userService.resetPassword(passwordResetDto));
    }

    // ------------------------------
    // Update Profile Info
    // ------------------------------
    @PutMapping("/{userId}/profile")
    public ResponseEntity<UserResponseDto> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UserProfileDto userProfileDto) {
        return ResponseEntity.ok(userService.updateProfile(userId, userProfileDto));
    }

    // ------------------------------
    // Update Profile Image
    // ------------------------------
    @PutMapping("/{userId}/profile-image")
    public ResponseEntity<UserResponseDto> updateProfileImage(
            @PathVariable Long userId,
            @RequestParam String imageUrl) {
        return ResponseEntity.ok(userService.updateProfileImage(userId, imageUrl));
    }

    // ------------------------------
    // Check Email Exists
    // ------------------------------
    @GetMapping("/exists/email")
    public ResponseEntity<Boolean> emailExists(@RequestParam String email) {
        return ResponseEntity.ok(userService.emailExists(email));
    }

    // ------------------------------
    // Check Username Exists
    // ------------------------------
    @GetMapping("/exists/username")
    public ResponseEntity<Boolean> usernameExists(@RequestParam String username) {
        return ResponseEntity.ok(userService.usernameExists(username));
    }

    // ------------------------------
    // Get Users Created Between Dates
    // ------------------------------
    @GetMapping("/created-between")
    public ResponseEntity<List<UserResponseDto>> getUsersCreatedBetween(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(userService.getUsersCreatedBetween(startDate, endDate));
    }

    // ------------------------------
    // Get Verified Photographers
    // ------------------------------
    @GetMapping("/photographers/verified")
    public ResponseEntity<Page<UserResponseDto>> getVerifiedPhotographers(Pageable pageable) {
        return ResponseEntity.ok(userService.getVerifiedPhotographers(pageable));
    }

    // ------------------------------
    // Assign Role
    // ------------------------------
    @PutMapping("/{userId}/role")
    public ResponseEntity<UserResponseDto> assignRole(
            @PathVariable Long userId,
            @RequestParam UserRole role) {
        return ResponseEntity.ok(userService.assignRole(userId, role));
    }

    // ------------------------------
    // Get User by Username
    // ------------------------------
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable String username) {
        UserResponseDto user = userService.getUserByUsername(username);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // ------------------------------
    // Get Active Users
    // ------------------------------
    @GetMapping("/active")
    public ResponseEntity<Page<UserResponseDto>> getActiveUsers(
            @RequestParam(defaultValue = "true") Boolean isActive,
            Pageable pageable) {
        return ResponseEntity.ok(userService.getActiveUsers(isActive, pageable));
    }

    // ------------------------------
    // Update Last Login
    // ------------------------------
    @PutMapping("/{userId}/last-login")
    public ResponseEntity<UserResponseDto> updateLastLogin(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.updateLastLogin(userId));
    }

    // ------------------------------
    // Count Users by Role
    // ------------------------------
    @GetMapping("/count/role/{role}")
    public ResponseEntity<Long> countUsersByRole(@PathVariable UserRole role) {
        return ResponseEntity.ok(userService.countUsersByRole(role));
    }

    // ------------------------------
    // Count Active Users
    // ------------------------------
    @GetMapping("/count/active")
    public ResponseEntity<Long> countActiveUsers() {
        return ResponseEntity.ok(userService.countActiveUsers());
    }

    // ------------------------------
    // Get Top Photographers
    // ------------------------------
    @GetMapping("/photographers/top")
    public ResponseEntity<Page<UserResponseDto>> getTopPhotographers(Pageable pageable) {
        return ResponseEntity.ok(userService.getTopPhotographers(pageable));
    }

    // ------------------------------
    // Register First Admin (No Auth Required)
    // ------------------------------
    @PostMapping("/register-first-admin")
    public ResponseEntity<UserResponseDto> registerFirstAdmin(@Valid @RequestBody UserCreateDto userCreateDto) {
        // Force role to ADMIN and ensure email verification
        userCreateDto.setRole(UserRole.ADMIN);
        userCreateDto.setEmailVerified(true);
        userCreateDto.setIsActive(true);
        
        return ResponseEntity.ok(userService.createFirstAdmin(userCreateDto));
    }
}
