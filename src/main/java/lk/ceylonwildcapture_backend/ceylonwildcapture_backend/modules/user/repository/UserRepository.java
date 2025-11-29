package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.UserRole;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity.
 * Provides database operations for user management including authentication and authorization.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by email address.
     *
     * @param email the email address
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Find a user by username.
     *
     * @param username the username
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Find a user by email or username.
     *
     * @param email the email address
     * @param username the username
     * @return Optional containing the user if found
     */
    Optional<User> findByEmailOrUsername(String email, String username);

    /**
     * Check if a user exists by email.
     *
     * @param email the email address
     * @return true if user exists
     */
    boolean existsByEmail(String email);

    /**
     * Check if a user exists by username.
     *
     * @param username the username
     * @return true if user exists
     */
    boolean existsByUsername(String username);

    /**
     * Find all users by role.
     *
     * @param role the user role
     * @return list of users with the specified role
     */
    List<User> findByRole(UserRole role);

    /**
     * Find all users by role with pagination.
     *
     * @param role the user role
     * @param pageable pagination information
     * @return page of users with the specified role
     */
    Page<User> findByRole(UserRole role, Pageable pageable);

    /**
     * Find all active users.
     *
     * @param isActive the active status
     * @return list of active users
     */
    List<User> findByIsActive(Boolean isActive);

    /**
     * Find all active users with pagination.
     *
     * @param isActive the active status
     * @param pageable pagination information
     * @return page of active users
     */
    Page<User> findByIsActive(Boolean isActive, Pageable pageable);

    /**
     * Find users by role and active status.
     *
     * @param role the user role
     * @param isActive the active status
     * @param pageable pagination information
     * @return page of users matching criteria
     */
    Page<User> findByRoleAndIsActive(UserRole role, Boolean isActive, Pageable pageable);

    /**
     * Find users by email verification status.
     *
     * @param emailVerified the email verification status
     * @return list of users with specified verification status
     */
    List<User> findByEmailVerified(Boolean emailVerified);

    /**
     * Search users by username or email containing search term.
     *
     * @param username the username search term
     * @param email the email search term
     * @param pageable pagination information
     * @return page of users matching search criteria
     */
    Page<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String username, String email, Pageable pageable);

    /**
     * Find users created within a date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return list of users created within the date range
     */
    List<User> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find users who have logged in since a specific date.
     *
     * @param lastLogin the last login date
     * @return list of users who logged in after the specified date
     */
    List<User> findByLastLoginAfter(LocalDateTime lastLogin);

    /**
     * Count users by role.
     *
     * @param role the user role
     * @return count of users with the specified role
     */
    long countByRole(UserRole role);

    /**
     * Count active users.
     *
     * @param isActive the active status
     * @return count of active users
     */
    long countByIsActive(Boolean isActive);

    /**
     * Find photographers with verified profiles.
     *
     * @param role the user role (PHOTOGRAPHER)
     * @param pageable pagination information
     * @return page of verified photographers
     */
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.photographerProfile.verifiedPhotographer = true AND u.isActive = true")
    Page<User> findVerifiedPhotographers(@Param("role") UserRole role, Pageable pageable);

    /**
     * Find top photographers by total sales.
     *
     * @param role the user role (PHOTOGRAPHER)
     * @param pageable pagination information
     * @return page of top photographers
     */
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.isActive = true ORDER BY u.photographerProfile.totalSales DESC")
    Page<User> findTopPhotographers(@Param("role") UserRole role, Pageable pageable);

    /**
     * Find photographers with pending payouts.
     *
     * @param role the user role (PHOTOGRAPHER)
     * @return list of photographers with pending earnings
     */
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.photographerProfile.pendingEarnings > 0")
    List<User> findPhotographersWithPendingPayouts(@Param("role") UserRole role);
}
