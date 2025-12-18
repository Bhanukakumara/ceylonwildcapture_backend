package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.security;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * Custom UserDetailsService implementation for JWT authentication.
 * Loads user-specific data from the database for authentication.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Load user by username or email for authentication.
     *
     * @param usernameOrEmail username or email address
     * @return UserDetails object containing user information
     * @throws UsernameNotFoundException if user not found
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        log.debug("Loading user by username or email: {}", usernameOrEmail);

        lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User user = userRepository
                .findByEmailOrUsername(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username or email: " + usernameOrEmail));

        if (!user.getIsActive()) {
            log.warn("User account is inactive: {}", usernameOrEmail);
            throw new UsernameNotFoundException("User account is inactive: " + usernameOrEmail);
        }

        log.debug("User found and active: {}", user.getUsername());
        return createUserDetails(user);
    }

    /**
     * Create UserDetails object from User entity.
     *
     * @param user User entity
     * @return UserDetails object
     */
    private UserDetails createUserDetails(
            lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User user) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());

        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(authority));
    }

    /**
     * Load user by ID.
     *
     * @param userId user ID
     * @return UserDetails object
     * @throws UsernameNotFoundException if user not found
     */
    @Transactional(readOnly = true)
    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
        log.debug("Loading user by ID: {}", userId);

        lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

        if (!user.getIsActive()) {
            log.warn("User account is inactive: {}", userId);
            throw new UsernameNotFoundException("User account is inactive: " + userId);
        }

        return createUserDetails(user);
    }
}
