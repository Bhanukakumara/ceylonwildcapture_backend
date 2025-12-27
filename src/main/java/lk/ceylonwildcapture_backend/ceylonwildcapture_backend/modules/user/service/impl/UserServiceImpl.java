package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.UserRole;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.dto.*;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.exception.DuplicateUserException;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.exception.InvalidPasswordException;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.exception.UserAlreadyExistsException;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.exception.UserNotFoundException;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.repository.UserRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of UserService interface.
 * Provides business logic for user management operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.util.EmailService emailService;
    private final lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.repository.EmailVerificationTokenRepository tokenRepository;

    @Override
    public UserResponseDto createUser(UserCreateDto userCreateDto) {
        log.info("Creating new user with email: {}", userCreateDto.getEmail());

        // Validate email and username uniqueness
        if (userRepository.existsByEmail(userCreateDto.getEmail())) {
            throw new DuplicateUserException("Email", userCreateDto.getEmail());
        }

        if (userRepository.existsByUsername(userCreateDto.getUsername())) {
            throw new DuplicateUserException("Username", userCreateDto.getUsername());
        }

        // Convert DTO to entity
        User user = User.builder()
                .username(userCreateDto.getUsername())
                .email(userCreateDto.getEmail())
                .password(passwordEncoder.encode(userCreateDto.getPassword()))
                .firstName(userCreateDto.getFirstName())
                .lastName(userCreateDto.getLastName())
                .phoneNumber(userCreateDto.getPhoneNumber())
                .role(userCreateDto.getRole())
                .isActive(userCreateDto.getIsActive() != null && userCreateDto.getIsActive())
                .emailVerified(userCreateDto.getEmailVerified() != null && userCreateDto.getEmailVerified())
                .profileImageUrl(userCreateDto.getProfileImageUrl())
                .build();

        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId());
        
        // Send verification email for non-admin users who aren't pre-verified
        if (!savedUser.getEmailVerified() && savedUser.getRole() != UserRole.ADMIN) {
            try {
                String token = createVerificationToken(savedUser.getId());
                emailService.sendEmailVerification(savedUser, token);
                log.info("Verification email sent to: {}", savedUser.getEmail());
            } catch (Exception e) {
                log.error("Failed to send verification email to: {}", savedUser.getEmail(), e);
                // Don't fail user creation if email fails
            }
        }
        
        return UserResponseDto.fromEntity(savedUser);
    }

    @Override
    public UserResponseDto createFirstAdmin(UserCreateDto userCreateDto) {
        log.info("Creating first admin user with email: {}", userCreateDto.getEmail());

        // Check if any admin users already exist
        List<User> existingAdmins = userRepository.findByRole(UserRole.ADMIN);
        if (!existingAdmins.isEmpty()) {
            throw new UserAlreadyExistsException("Admin user already exists. First admin registration is only allowed when no admin users exist.");
        }

        // Validate email and username uniqueness
        if (userRepository.existsByEmail(userCreateDto.getEmail())) {
            throw new DuplicateUserException("Email", userCreateDto.getEmail());
        }

        if (userRepository.existsByUsername(userCreateDto.getUsername())) {
            throw new DuplicateUserException("Username", userCreateDto.getUsername());
        }

        // Force admin role and verification status
        userCreateDto.setRole(UserRole.ADMIN);
        userCreateDto.setIsActive(true);
        userCreateDto.setEmailVerified(true);

        // Convert DTO to entity
        User user = User.builder()
                .username(userCreateDto.getUsername())
                .email(userCreateDto.getEmail())
                .password(passwordEncoder.encode(userCreateDto.getPassword()))
                .firstName(userCreateDto.getFirstName())
                .lastName(userCreateDto.getLastName())
                .phoneNumber(userCreateDto.getPhoneNumber())
                .role(UserRole.ADMIN)
                .isActive(true)
                .emailVerified(true)
                .profileImageUrl(userCreateDto.getProfileImageUrl())
                .build();

        User savedUser = userRepository.save(user);
        log.info("First admin user created successfully with ID: {}", savedUser.getId());
        return UserResponseDto.fromEntity(savedUser);
    }

    @Override
    public UserResponseDto updateUser(Long userId, UserUpdateDto userUpdateDto) {
        log.info("Updating user with ID: {}", userId);

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Update fields if they are not null in the DTO
        if (userUpdateDto.getUsername() != null && !userUpdateDto.getUsername().equals(existingUser.getUsername())) {
            if (userRepository.existsByUsername(userUpdateDto.getUsername())) {
                throw new DuplicateUserException("Username", userUpdateDto.getUsername());
            }
            existingUser.setUsername(userUpdateDto.getUsername());
        }

        if (userUpdateDto.getEmail() != null && !userUpdateDto.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.existsByEmail(userUpdateDto.getEmail())) {
                throw new DuplicateUserException("Email", userUpdateDto.getEmail());
            }
            existingUser.setEmail(userUpdateDto.getEmail());
            existingUser.setEmailVerified(false); // Reset verification when email changes
        }

        if (userUpdateDto.getFirstName() != null) {
            existingUser.setFirstName(userUpdateDto.getFirstName());
        }

        if (userUpdateDto.getLastName() != null) {
            existingUser.setLastName(userUpdateDto.getLastName());
        }

        if (userUpdateDto.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(userUpdateDto.getPhoneNumber());
        }

        if (userUpdateDto.getRole() != null) {
            existingUser.setRole(userUpdateDto.getRole());
        }

        if (userUpdateDto.getIsActive() != null) {
            existingUser.setIsActive(userUpdateDto.getIsActive());
        }

        if (userUpdateDto.getEmailVerified() != null) {
            existingUser.setEmailVerified(userUpdateDto.getEmailVerified());
        }

        if (userUpdateDto.getProfileImageUrl() != null) {
            existingUser.setProfileImageUrl(userUpdateDto.getProfileImageUrl());
        }

        User updatedUser = userRepository.save(existingUser);
        log.info("User updated successfully with ID: {}", updatedUser.getId());
        return UserResponseDto.fromEntity(updatedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long userId) {
        log.debug("Fetching user by ID: {}", userId);
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? UserResponseDto.fromEntity(user) : null;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(String email) {
        log.debug("Fetching user by email: {}", email);
        User user = userRepository.findByEmail(email).orElse(null);
        return user != null ? UserResponseDto.fromEntity(user) : null;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserByUsername(String username) {
        log.debug("Fetching user by username: {}", username);
        User user = userRepository.findByUsername(username).orElse(null);
        return user != null ? UserResponseDto.fromEntity(user) : null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDto> getAllUsers(Pageable pageable) {
        log.debug("Fetching all users with pagination");
        Page<User> userPage = userRepository.findAll(pageable);
        return new PageImpl<>(
                userPage.getContent().stream()
                        .map(UserResponseDto::fromEntity)
                        .collect(Collectors.toList()),
                userPage.getPageable(),
                userPage.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDto> getUsersByRole(UserRole role, Pageable pageable) {
        log.debug("Fetching users by role: {}", role);
        Page<User> userPage = userRepository.findByRole(role, pageable);
        return new PageImpl<>(
                userPage.getContent().stream()
                        .map(UserResponseDto::fromEntity)
                        .collect(Collectors.toList()),
                userPage.getPageable(),
                userPage.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDto> getActiveUsers(Boolean isActive, Pageable pageable) {
        log.debug("Fetching users by active status: {}", isActive);
        Page<User> userPage = userRepository.findByIsActive(isActive, pageable);
        return new PageImpl<>(
                userPage.getContent().stream()
                        .map(UserResponseDto::fromEntity)
                        .collect(Collectors.toList()),
                userPage.getPageable(),
                userPage.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDto> searchUsers(String searchTerm, Pageable pageable) {
        log.debug("Searching users with term: {}", searchTerm);
        Page<User> userPage = userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                searchTerm, searchTerm, pageable);
        return new PageImpl<>(
                userPage.getContent().stream()
                        .map(UserResponseDto::fromEntity)
                        .collect(Collectors.toList()),
                userPage.getPageable(),
                userPage.getTotalElements()
        );
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("Soft deleting user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setIsActive(false);
        userRepository.save(user);
        log.info("User soft deleted successfully with ID: {}", userId);
    }

    @Override
    public void permanentlyDeleteUser(Long userId) {
        log.warn("Permanently deleting user with ID: {}", userId);

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        userRepository.deleteById(userId);
        log.info("User permanently deleted with ID: {}", userId);
    }

    @Override
    public UserResponseDto activateUser(Long userId) {
        log.info("Activating user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setIsActive(true);
        User activatedUser = userRepository.save(user);
        log.info("User activated successfully with ID: {}", userId);
        return UserResponseDto.fromEntity(activatedUser);
    }

    @Override
    public UserResponseDto deactivateUser(Long userId) {
        log.info("Deactivating user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setIsActive(false);
        User deactivatedUser = userRepository.save(user);
        log.info("User deactivated successfully with ID: {}", userId);
        return UserResponseDto.fromEntity(deactivatedUser);
    }

    @Override
    public UserResponseDto verifyEmail(Long userId) {
        log.info("Verifying email for user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setEmailVerified(true);
        User verifiedUser = userRepository.save(user);
        log.info("Email verified successfully for user with ID: {}", userId);
        return UserResponseDto.fromEntity(verifiedUser);
    }

    @Override
    public UserResponseDto updatePassword(Long userId, PasswordChangeDto passwordChangeDto) {
        log.info("Updating password for user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Validate password confirmation
        if (!passwordChangeDto.getNewPassword().equals(passwordChangeDto.getConfirmPassword())) {
            throw new InvalidPasswordException("New password and confirmation do not match");
        }

        // Verify old password
        if (!passwordEncoder.matches(passwordChangeDto.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Current password is incorrect");
        }

        // Validate new password
        if (passwordChangeDto.getNewPassword() == null || passwordChangeDto.getNewPassword().length() < 8) {
            throw new InvalidPasswordException("New password must be at least 8 characters long");
        }

        user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        User updatedUser = userRepository.save(user);
        log.info("Password updated successfully for user with ID: {}", userId);
        return UserResponseDto.fromEntity(updatedUser);
    }

    @Override
    public UserResponseDto resetPassword(PasswordResetDto passwordResetDto) {
        log.info("Resetting password for user with email: {}", passwordResetDto.getEmail());

        User user = userRepository.findByEmail(passwordResetDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("email", passwordResetDto.getEmail()));

        // Validate password confirmation
        if (!passwordResetDto.getNewPassword().equals(passwordResetDto.getConfirmPassword())) {
            throw new InvalidPasswordException("New password and confirmation do not match");
        }

        // Validate new password
        if (passwordResetDto.getNewPassword() == null || passwordResetDto.getNewPassword().length() < 8) {
            throw new InvalidPasswordException("New password must be at least 8 characters long");
        }

        user.setPassword(passwordEncoder.encode(passwordResetDto.getNewPassword()));
        User updatedUser = userRepository.save(user);
        log.info("Password reset successfully for user with email: {}", passwordResetDto.getEmail());
        return UserResponseDto.fromEntity(updatedUser);
    }

    @Override
    public UserResponseDto updateProfile(Long userId, UserProfileDto userProfileDto) {
        log.info("Updating profile for user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (userProfileDto.getFirstName() != null && !userProfileDto.getFirstName().trim().isEmpty()) {
            user.setFirstName(userProfileDto.getFirstName());
        }

        if (userProfileDto.getLastName() != null && !userProfileDto.getLastName().trim().isEmpty()) {
            user.setLastName(userProfileDto.getLastName());
        }

        if (userProfileDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userProfileDto.getPhoneNumber());
        }

        if (userProfileDto.getEmail() != null && !userProfileDto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userProfileDto.getEmail())) {
                throw new DuplicateUserException("Email", userProfileDto.getEmail());
            }
            user.setEmail(userProfileDto.getEmail());
            user.setEmailVerified(false); // Reset verification when email changes
        }

        if (userProfileDto.getProfileImageUrl() != null) {
            user.setProfileImageUrl(userProfileDto.getProfileImageUrl());
        }

        User updatedUser = userRepository.save(user);
        log.info("Profile updated successfully for user with ID: {}", userId);
        return UserResponseDto.fromEntity(updatedUser);
    }

    @Override
    public UserResponseDto updateProfileImage(Long userId, String profileImageUrl) {
        log.info("Updating profile image for user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setProfileImageUrl(profileImageUrl);
        User updatedUser = userRepository.save(user);
        log.info("Profile image updated successfully for user with ID: {}", userId);
        return UserResponseDto.fromEntity(updatedUser);
    }

    @Override
    public UserResponseDto updateLastLogin(Long userId) {
        log.debug("Updating last login for user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setLastLogin(LocalDateTime.now());
        User updatedUser = userRepository.save(user);
        return UserResponseDto.fromEntity(updatedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public long countUsersByRole(UserRole role) {
        return userRepository.countByRole(role);
    }

    @Override
    @Transactional(readOnly = true)
    public long countActiveUsers() {
        return userRepository.countByIsActive(true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsersCreatedBetween(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Fetching users created between {} and {}", startDate, endDate);
        List<User> users = userRepository.findByCreatedAtBetween(startDate, endDate);
        return users.stream()
                .map(UserResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDto> getTopPhotographers(Pageable pageable) {
        log.debug("Fetching top photographers");
        Page<User> userPage = userRepository.findTopPhotographers(UserRole.PHOTOGRAPHER, pageable);
        return new PageImpl<>(
                userPage.getContent().stream()
                        .map(UserResponseDto::fromEntity)
                        .collect(Collectors.toList()),
                userPage.getPageable(),
                userPage.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDto> getVerifiedPhotographers(Pageable pageable) {
        log.debug("Fetching verified photographers");
        Page<User> userPage = userRepository.findVerifiedPhotographers(UserRole.PHOTOGRAPHER, pageable);
        return new PageImpl<>(
                userPage.getContent().stream()
                        .map(UserResponseDto::fromEntity)
                        .collect(Collectors.toList()),
                userPage.getPageable(),
                userPage.getTotalElements()
        );
    }

    @Override
    public UserResponseDto assignRole(Long userId, UserRole role) {
        log.info("Assigning role {} to user with ID: {}", role, userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setRole(role);
        User updatedUser = userRepository.save(user);
        log.info("Role assigned successfully to user with ID: {}", userId);
        return UserResponseDto.fromEntity(updatedUser);
    }

    @Override
    public String createVerificationToken(Long userId) {
        log.info("Creating verification token for user ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        
        // Delete any existing tokens for this user
        tokenRepository.deleteByUser(user);
        
        // Create new token
        lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.EmailVerificationToken token = 
            lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.EmailVerificationToken.builder()
                .token(lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.EmailVerificationToken.generateTokenString())
                .user(user)
                .expiryDate(lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.EmailVerificationToken.calculateExpiryDate())
                .build();
        
        lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.EmailVerificationToken savedToken = tokenRepository.save(token);
        log.info("Verification token created for user ID: {}", userId);
        return savedToken.getToken();
    }

    @Override
    public UserResponseDto verifyEmailByToken(String token) {
        log.info("Verifying email with token");
        
        lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.EmailVerificationToken verificationToken = 
            tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid verification token"));
        
        if (verificationToken.isExpired()) {
            log.warn("Verification token has expired");
            throw new IllegalArgumentException("Verification token has expired");
        }
        
        if (verificationToken.getUsed()) {
            log.warn("Verification token has already been used");
            throw new IllegalArgumentException("Verification token has already been used");
        }
        
        User user = verificationToken.getUser();
        user.setEmailVerified(true);
        User verifiedUser = userRepository.save(user);
        
        // Mark token as used
        verificationToken.setUsed(true);
        tokenRepository.save(verificationToken);
        
        log.info("Email verified successfully for user ID: {}", user.getId());
        return UserResponseDto.fromEntity(verifiedUser);
    }

    @Override
    public void resendVerificationEmail(String email) {
        log.info("Resending verification email to: {}", email);
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("email", email));
        
        if (user.getEmailVerified()) {
            throw new IllegalArgumentException("Email is already verified");
        }
        
        // Create new token and send email
        String token = createVerificationToken(user.getId());
        emailService.sendEmailVerification(user, token);
        
        log.info("Verification email resent to: {}", email);
    }
}
