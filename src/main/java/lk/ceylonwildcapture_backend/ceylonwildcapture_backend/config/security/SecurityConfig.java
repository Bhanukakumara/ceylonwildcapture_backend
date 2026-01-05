package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.config.security;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Security configuration for the application.
 * Configures JWT-based authentication and authorization.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        /**
         * Configures the security filter chain with JWT authentication.
         *
         * @param http HttpSecurity to configure
         * @return SecurityFilterChain
         * @throws Exception if configuration fails
         */
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                                // Public endpoints
                                                .requestMatchers("/api/auth/**").permitAll()
                                                .requestMatchers("/api/public/**").permitAll()
                                                .requestMatchers("/api/v1/public/**").permitAll()
                                                .requestMatchers("/api/v1/users/register-first-admin").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll() // Allow
                                                                                                               // public
                                                                                                               // registration
                                                .requestMatchers("/swagger-ui/**").permitAll()
                                                .requestMatchers("/v3/api-docs/**").permitAll()
                                                .requestMatchers("/actuator/**").permitAll()
                                                .requestMatchers("/error").permitAll()
                                                .requestMatchers("/api/v1/categories/**").permitAll()
                                                .requestMatchers("/api/v1/tags/**").permitAll()
                                                .requestMatchers("/api/v1/photos/**").permitAll()
                                                .requestMatchers("/api/v1/payments/webhook",
                                                                "/api/v1/payments/webhook/**")
                                                .permitAll() // Stripe webhook (Plural)
                                                .requestMatchers("/api/payment/webhook", "/api/payment/webhook/**")
                                                .permitAll() // Webhook alias (Singular)

                                                // Admin endpoints
                                                .requestMatchers("/api/admin/**", "/api/v1/admin/**").hasRole("ADMIN")

                                                // Photographer endpoints
                                                .requestMatchers("/api/photographer/**", "/api/v1/photographer/**")
                                                                .hasRole("PHOTOGRAPHER")

                                                // Buyer endpoints
                                                .requestMatchers("/api/buyer/**", "/api/v1/buyer/**").hasRole("BUYER")

                                                // All other endpoints require authentication
                                                .anyRequest().authenticated())
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        /**
         * Provides authentication manager bean.
         *
         * @param config AuthenticationConfiguration
         * @return AuthenticationManager
         * @throws Exception if configuration fails
         */
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        /**
         * Configures CORS to allow requests from the frontend.
         *
         * @return CorsConfigurationSource
         */
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();

                // Allow all origins
                configuration.setAllowedOriginPatterns(Arrays.asList("*"));

                // Allow all HTTP methods
                configuration.setAllowedMethods(Arrays.asList(
                                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

                // Allow all headers
                configuration.setAllowedHeaders(Arrays.asList("*"));

                // Allow credentials (cookies, authorization headers)
                configuration.setAllowCredentials(true);

                // Expose headers that the frontend can access
                configuration.setExposedHeaders(Arrays.asList(
                                "Authorization",
                                "Content-Type",
                                "X-Total-Count"));

                // Cache preflight response for 1 hour
                configuration.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);

                return source;
        }
}
