package org.bashtan.MyApps.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bashtan.MyApps.configs.jwt.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity()
@Log4j2
public class SpringConfig {

    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize) ->
                                authorize
                                        .requestMatchers(
                                                "/",
                                                "/favicon.ico",
                                                "/rol.jpg",
                                                "/index.html",
                                                "/email-confirmation.html",
                                                "/privacy-policy.html",
                                                "/download.html",
//                                        "/pushAdmin",
                                                "/instructionAndroid.html"
                                        ).permitAll()
                                        .requestMatchers("/uploads/**").permitAll()
                                        .requestMatchers("/api/v1/auth/**").permitAll()
                                        .requestMatchers("/api/v1/posts/fetchAll").permitAll()
                                        .requestMatchers("/api/v1/video/fetchAll").permitAll()
                                        .requestMatchers("/api/v1/about/**").permitAll()
                                        .requestMatchers("/api/v1/appVersion/version").permitAll()
                                        .requestMatchers("/api/v1/email/confirmEmail").permitAll()
                                        .requestMatchers("/api/v1/expoPush/push").permitAll()
                                        .requestMatchers("/api/v1/android/**").permitAll()
                                        .anyRequest().authenticated()
                ).exceptionHandling(
                        except ->
                                except.accessDeniedHandler((request,
                                                            response,
                                                            accessDeniedException) -> {
                                    response.setStatus(HttpStatus.FORBIDDEN.value());
                                    response.setContentType("application/json");
                                    response.getWriter().write("{\"error\": \"Access Denied: Insufficient role permissions\"}");
                                }))
                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(List.of("http://localhost:5173"));
//        config.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
//        config.setAllowedHeaders(List.of("*"));
//        config.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder)
            throws Exception {
        try {
            AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
                    .getSharedObject(AuthenticationManagerBuilder.class);

            authenticationManagerBuilder
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder);
            log.info("AuthenticationManager has been successfully configured.");
            return authenticationManagerBuilder.build();
        } catch (Exception e) {
            log.error("Error occurred while configuring AuthenticationManager: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to configure AuthenticationManager", e);
        }
    }
}
