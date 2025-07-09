package org.bashtan.MyApps.controllers.api;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bashtan.MyApps.data.dto.authorisation.AuthorizationUserDTO;
import org.bashtan.MyApps.data.dto.authorisation.SignInDTO;
import org.bashtan.MyApps.data.dto.user.CreateUserDTO;
import org.bashtan.MyApps.data.services.interfaces.AuthServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthorizationAPIController {

    private final AuthServiceInterface authService;
    private static final String SIGN_IN = "/signIn";
    private static final String NATIVE_SIGN_IN = "/nativeSignIn";
    private static final String SIGN_UP = "/signUp";
    private static final String LOG_OUT = "/logOut";

    @PostMapping(SIGN_UP)
    @PermitAll
    public ResponseEntity<Void> signUp(@RequestBody CreateUserDTO createUserDTO) {
        authService.signUp(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(NATIVE_SIGN_IN)
    @PermitAll
    public ResponseEntity<AuthorizationUserDTO> nativeSignIn(@RequestBody SignInDTO signInDTO) {
        return ResponseEntity.ok(authService.signIn(signInDTO, null, true));
    }

    @PostMapping(SIGN_IN)
    @PermitAll
    public ResponseEntity<AuthorizationUserDTO> signIn(@RequestBody SignInDTO signInDTO, HttpServletResponse response) {
        return ResponseEntity.ok(authService.signIn(signInDTO, response, false));
    }

    @GetMapping(LOG_OUT)
    @PermitAll
    public ResponseEntity<Map<String, String>> logOut(HttpServletRequest request, HttpServletResponse response) {
        try {
            authService.logOut(request, response);
            return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Logout failed"));
        }
    }
}