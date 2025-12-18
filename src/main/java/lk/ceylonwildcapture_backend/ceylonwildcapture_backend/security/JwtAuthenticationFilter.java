package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT authentication filter for processing JWT tokens in HTTP requests.
 * Extracts JWT token from Authorization header and validates it.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Filter method to process JWT authentication.
     *
     * @param request     HTTP request
     * @param response    HTTP response
     * @param filterChain filter chain
     * @throws ServletException if servlet error occurs
     * @throws IOException      if I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = getJwtFromRequest(request);
            log.debug("JWT extracted from request: {}", jwt != null ? "present" : "null");

            if (StringUtils.hasText(jwt) && jwtTokenUtil.validateTokenFormat(jwt)) {
                String username = jwtTokenUtil.extractUsername(jwt);
                log.debug("Username extracted from JWT: {}", username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    log.debug("UserDetails loaded for username: {}, authorities: {}", username,
                            userDetails.getAuthorities());

                    if (jwtTokenUtil.validateToken(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        // Set userId as request attribute for controllers
                        if (userDetails instanceof CustomUserDetails) {
                            Long userId = ((CustomUserDetails) userDetails).getId();
                            request.setAttribute("userId", userId);
                            log.debug("Set userId attribute: {}", userId);
                        }

                        log.info("User authenticated successfully: {} with authorities: {}", username,
                                userDetails.getAuthorities());
                    } else {
                        log.warn("JWT token validation failed for user: {}", username);
                    }
                } else {
                    if (username == null) {
                        log.warn("Username is null from JWT");
                    }
                    if (SecurityContextHolder.getContext().getAuthentication() != null) {
                        log.debug("Authentication already set in SecurityContext");
                    }
                }
            } else {
                if (!StringUtils.hasText(jwt)) {
                    log.debug("No JWT token found in request");
                } else {
                    log.warn("JWT token format validation failed");
                }
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extract JWT token from Authorization header.
     *
     * @param request HTTP request
     * @return JWT token or null if not found
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }

        return null;
    }

    /**
     * Determine if the filter should be applied to the request.
     * Skip authentication for public endpoints.
     *
     * @param request HTTP request
     * @return true if filter should be applied
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        // Skip authentication for public endpoints
        return path.startsWith("/api/auth/login") ||
                path.startsWith("/api/auth/register") ||
                path.startsWith("/api/auth/refresh") ||
                path.startsWith("/api/public/") ||
                path.startsWith("/swagger-ui/") ||
                path.startsWith("/v3/api-docs/") ||
                path.startsWith("/actuator/") ||
                path.equals("/error");
    }
}
