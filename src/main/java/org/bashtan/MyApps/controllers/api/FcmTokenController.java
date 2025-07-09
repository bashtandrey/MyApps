package org.bashtan.MyApps.controllers.api;

import lombok.RequiredArgsConstructor;
import org.bashtan.MyApps.data.services.interfaces.FcmTokenServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class FcmTokenController {
    private static final String REGISTER_TOKEN = "/register-token";

    private final FcmTokenServiceInterface fcmTokenServiceInterface;

    @PostMapping(REGISTER_TOKEN)
    public ResponseEntity<Void> saveToken(@RequestBody Map<String, String> body, Authentication authentication) {
        fcmTokenServiceInterface.saveToken(body, authentication);
        return ResponseEntity.ok().build();
    }
}