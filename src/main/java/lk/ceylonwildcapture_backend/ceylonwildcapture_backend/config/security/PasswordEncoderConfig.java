package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for password encoding.
 * Provides a BCryptPasswordEncoder bean for password hashing.
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * Creates a BCryptPasswordEncoder bean.
     * BCrypt is a strong hashing algorithm suitable for password storage.
     *
     * @return PasswordEncoder instance using BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
