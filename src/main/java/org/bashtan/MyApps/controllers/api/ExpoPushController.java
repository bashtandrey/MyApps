package org.bashtan.MyApps.controllers.api;

import lombok.RequiredArgsConstructor;
import org.bashtan.MyApps.data.repositories.FcmTokenRepository;
import org.bashtan.MyApps.data.services.interfaces.ExpoPushServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/api/v1/expoPush")
@RequiredArgsConstructor
public class ExpoPushController {

    private final ExpoPushServiceInterface expoPushService;
    private final FcmTokenRepository fcmTokenRepository;
    private static final String PUSH = "/pushAdmin";

    @GetMapping(PUSH)
    public ResponseEntity<Void> pushRegisterAdmin(
            @RequestParam String email) {
        expoPushService.sendAdminRegisterNewEmail(email);
        return ResponseEntity.ok().build();
    }

}
