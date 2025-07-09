package org.bashtan.MyApps.data.services.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bashtan.MyApps.configs.MyUserDetails;
import org.bashtan.MyApps.configs.jwt.JwtUtil;
import org.bashtan.MyApps.data.dto.authorisation.AuthorizationUserDTO;
import org.bashtan.MyApps.data.dto.authorisation.SignInDTO;
import org.bashtan.MyApps.data.dto.user.CreateUserDTO;
import org.bashtan.MyApps.data.repositories.UserRepository;
import org.bashtan.MyApps.data.services.interfaces.AuthServiceInterface;
import org.bashtan.MyApps.data.services.interfaces.UserServiceInterface;
import org.bashtan.MyApps.enums.UserRole;
import org.bashtan.MyApps.exceptions.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Log4j2
public class AuthorizationService implements AuthServiceInterface {

    private final AuthenticationManager authenticationManager;
    private final UserServiceInterface userService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Override
    public AuthorizationUserDTO signIn(SignInDTO signInDTO, HttpServletResponse response, boolean forMobileClient) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInDTO.getLogin(),
                            signInDTO.getPassword())
            );

            final MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();

            // Создаем токен
            String tokenJwt = jwtUtil.generateToken(myUserDetails);

            if (!forMobileClient) {
                // Вариант для браузера — ставим куку
                Cookie jwtCookie = new Cookie("jwt", tokenJwt);
                jwtCookie.setHttpOnly(true);
                jwtCookie.setSecure(true);
                jwtCookie.setPath("/");
                jwtCookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
                response.addCookie(jwtCookie);
            }

            return AuthorizationUserDTO.builder()
                    .id(myUserDetails.getId())
                    .login(myUserDetails.getUsername())
                    .firstName(myUserDetails.getFirstName())
                    .lastName(myUserDetails.getLastName())
                    .email(myUserDetails.getEmail())
                    .emailVerified(myUserDetails.isEmailVerified())
                    .roles(myUserDetails.getAuthorities()
                            .stream()
                            .map(grantedAuthority ->
                                    UserRole.valueOf(grantedAuthority.getAuthority().replaceFirst("ROLE_", "")))
                            .collect(Collectors.toSet()))
                    .accessToken(forMobileClient ? tokenJwt : null) // токен только для мобильного клиента
                    .superUser(myUserDetails.isSuperUser())
                    .build();
        } catch (BadCredentialsException e) {
            throw new BadRequestException(
                    "Неверный логин или пароль",
                    "login"
            );
        }
    }

    @Override
    public void logOut(HttpServletRequest request, HttpServletResponse response) {
        try {
            String token = getJwtFromRequest(request);
            if (token != null) {
                jwtUtil.logOut(token);
            }

            // Удаляем cookie
            Cookie jwtCookie = new Cookie("jwt", "");
            jwtCookie.setMaxAge(0);
            jwtCookie.setPath("/");
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(request.isSecure());
            response.addCookie(jwtCookie);
            log.info("Successful Log out");
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during logout", e);
        }
    }

    @Override
    public void signUp(CreateUserDTO createUserDTO) {
        userService.createUser(createUserDTO);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) { // Ищем cookie с именем "jwt"
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
