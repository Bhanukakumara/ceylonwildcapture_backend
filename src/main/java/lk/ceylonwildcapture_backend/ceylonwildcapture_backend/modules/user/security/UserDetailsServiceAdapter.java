package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.security;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Interface for loading user details for Spring Security authentication.
 * Adapts User entity to Spring Security UserDetails.
 */
public interface UserDetailsServiceAdapter {

    /**
     * Load user by username (email) for Spring Security.
     *
     * @param username the username (email)
     * @return UserDetails for authentication
     * @throws UsernameNotFoundException if user not found
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Load user by email.
     *
     * @param email the email address
     * @return UserDetails for authentication
     * @throws UsernameNotFoundException if user not found
     */
    UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;

    /**
     * Load user by user ID.
     *
     * @param userId the user ID
     * @return UserDetails for authentication
     * @throws UsernameNotFoundException if user not found
     */
    UserDetails loadUserById(Long userId) throws UsernameNotFoundException;

    /**
     * Convert User entity to Spring Security UserDetails.
     *
     * @param user the user entity
     * @return UserDetails object
     */
    UserDetails convertToUserDetails(User user);

    /**
     * Get User entity from UserDetails.
     *
     * @param userDetails the UserDetails object
     * @return User entity
     * @throws IllegalArgumentException if UserDetails is not compatible
     */
    User getUserFromUserDetails(UserDetails userDetails);

    /**
     * Check if user account is enabled and not locked.
     *
     * @param user the user entity
     * @return true if account is active and usable
     */
    boolean isAccountActive(User user);

    /**
     * Check if user credentials are expired.
     *
     * @param user the user entity
     * @return true if credentials are not expired
     */
    boolean areCredentialsNonExpired(User user);

    /**
     * Check if user account is not expired.
     *
     * @param user the user entity
     * @return true if account is not expired
     */
    boolean isAccountNonExpired(User user);

    /**
     * Check if user account is not locked.
     *
     * @param user the user entity
     * @return true if account is not locked
     */
    boolean isAccountNonLocked(User user);
}
