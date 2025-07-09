package org.bashtan.MyApps.controllers.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.bashtan.MyApps.data.dto.user.UserDTO;
import org.bashtan.MyApps.data.repositories.UserRepository;
import org.bashtan.MyApps.data.services.interfaces.EmailServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/email")
public class EmailAPIController {

    private static final String CONFIRM_EMAIL = "/confirmEmail";
    private static final String RESEND_EMAIL = "/resendEmail";

    private final UserRepository userRepository;
    private final EmailServiceInterface emailServiceInterface;

    @GetMapping(CONFIRM_EMAIL)
    @PermitAll
    public RedirectView confirmEmail(@RequestParam String token) {
        String result = emailServiceInterface.confirmEmail(token);

        String encodedResult = URLEncoder.encode(result, StandardCharsets.UTF_8);

        return new RedirectView("/email-confirmation.html?result=" + encodedResult);
    }


    @PostMapping(RESEND_EMAIL)
    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #userDTO.id")
    public ResponseEntity<String> resendEmail(@RequestBody UserDTO userDTO) {
        emailServiceInterface.resendEmail(userDTO);
        return ResponseEntity.ok().build();
    }
}
