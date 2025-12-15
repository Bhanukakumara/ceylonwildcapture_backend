package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.UserRole;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.UserManagementDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.UserManagementService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * REST controller for user management operations.
 */
@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserManagementController {

    private final UserManagementService userManagementService;

    @PostMapping("/manage")
    public ResponseEntity<User> manageUser(
            @Valid @RequestBody UserManagementDto managementDto,
            @RequestAttribute("userId") Long adminId) {
        User user = userManagementService.manageUser(managementDto, adminId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{userId}/ban")
    public ResponseEntity<User> banUser(
            @PathVariable Long userId,
            @RequestParam String reason,
            @RequestAttribute("userId") Long adminId) {
        User bannedUser = userManagementService.banUser(userId, reason, adminId);
        return ResponseEntity.ok(bannedUser);
    }

    @PostMapping("/{userId}/unban")
    public ResponseEntity<User> unbanUser(
            @PathVariable Long userId,
            @RequestAttribute("userId") Long adminId) {
        User unbannedUser = userManagementService.unbanUser(userId, adminId);
        return ResponseEntity.ok(unbannedUser);
    }

    @PostMapping("/{userId}/activate")
    public ResponseEntity<User> activateUser(
            @PathVariable Long userId,
            @RequestAttribute("userId") Long adminId) {
        User activatedUser = userManagementService.activateUser(userId, adminId);
        return ResponseEntity.ok(activatedUser);
    }

    @PostMapping("/{userId}/deactivate")
    public ResponseEntity<User> deactivateUser(
            @PathVariable Long userId,
            @RequestAttribute("userId") Long adminId) {
        User deactivatedUser = userManagementService.deactivateUser(userId, adminId);
        return ResponseEntity.ok(deactivatedUser);
    }

    @PostMapping("/{userId}/verify-photographer")
    public ResponseEntity<User> verifyPhotographer(
            @PathVariable Long userId,
            @RequestAttribute("userId") Long adminId) {
        User verifiedUser = userManagementService.verifyPhotographer(userId, adminId);
        return ResponseEntity.ok(verifiedUser);
    }

    @PostMapping("/{userId}/unverify-photographer")
    public ResponseEntity<User> unverifyPhotographer(
            @PathVariable Long userId,
            @RequestAttribute("userId") Long adminId) {
        User unverifiedUser = userManagementService.unverifyPhotographer(userId, adminId);
        return ResponseEntity.ok(unverifiedUser);
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<User> changeUserRole(
            @PathVariable Long userId,
            @RequestParam UserRole newRole,
            @RequestAttribute("userId") Long adminId) {
        User user = userManagementService.assignRole(userId, newRole, adminId);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(required = false) UserRole role,
            @RequestParam(required = false) Boolean isActive,
            Pageable pageable) {
        Page<User> users = userManagementService.getAllUsers(role, isActive, pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/banned")
    public ResponseEntity<Page<User>> getBannedUsers(Pageable pageable) {
        Page<User> bannedUsers = userManagementService.getBannedUsers(pageable);
        return ResponseEntity.ok(bannedUsers);
    }

    @GetMapping("/verified-photographers")
    public ResponseEntity<Page<User>> getVerifiedPhotographers(Pageable pageable) {
        Page<User> verifiedPhotographers = userManagementService.getVerifiedPhotographers(pageable);
        return ResponseEntity.ok(verifiedPhotographers);
    }

    @GetMapping("/unverified-photographers")
    public ResponseEntity<Page<User>> getUnverifiedPhotographers(Pageable pageable) {
        Page<User> unverifiedPhotographers = userManagementService.getUnverifiedPhotographers(pageable);
        return ResponseEntity.ok(unverifiedPhotographers);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserDetails(@PathVariable Long userId) {
        User user = userManagementService.getUserDetails(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/inactive")
    public ResponseEntity<Page<User>> getInactiveUsers(Pageable pageable) {
        Page<User> inactiveUsers = userManagementService.getInactiveUsers(pageable);
        return ResponseEntity.ok(inactiveUsers);
    }
}
