package org.bashtan.MyApps.configs.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bashtan.MyApps.exceptions.jwt.JwtTokenExpiredException;
import org.bashtan.MyApps.exceptions.jwt.JwtTokenInvalidException;
import org.bashtan.MyApps.exceptions.jwt.JwtTokenMissingException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/v1/auth/.*",
            "/api/v1/email/confirmEmail",
            "/uploads/.*",
            "/api/v1/expoPush/.*",
            "/api/v1/posts/fetchAll",
            "/api/v1/video/fetchAll",
            "/api/v1/about/.*",
            "/api/v1/appVersion/version",
            "/api/v1/android/.*",
            "/",
            "/privacy-policy.html",
            "/email-confirmation.html",
            "/download.html",
            "/instructionAndroid.html",
            "/rol.jpg",
            "/favicon.ico"
    );

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::matches);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final String requestPath = request.getRequestURI();
        logger.info("Start doFilterInternal for path: " + requestPath);

        // Пропускаем публичные пути
        if (isPublicPath(requestPath)) {
            logger.info("Skipping JwtRequestFilter for public path: " + requestPath);
            chain.doFilter(request, response);
            return;
        }

        // Получаем токен из заголовка Authorization или из cookies
        String jwt = getJwtFromRequest(request);
        if (jwt == null) {
            logger.error("JWT token is missing");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT token is missing");
            return;
        }

        try {
            String username = jwtUtil.extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    logger.info("Authenticated user: " + userDetails.getUsername() + " with roles: " + userDetails.getAuthorities());
                } else {
                    logger.warn("Invalid token for user: " + username);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token is invalid or expired");
                    response.getWriter().flush();
                    return;
                }
            }
        } catch (JwtTokenExpiredException | JwtTokenInvalidException | JwtTokenMissingException e) {
            logger.error("JWT error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            String json = String.format(
                    "{\"error\": \"%s\", \"code\": \"%s\"}",
                    e.getMessage(),
                    "JWT"
            );
            response.getWriter().write(json);
            return;
        }

        chain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
