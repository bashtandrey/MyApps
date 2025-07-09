package org.bashtan.MyApps.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bashtan.MyApps.data.dto.PushMessage;
import org.bashtan.MyApps.data.repositories.FcmTokenRepository;
import org.bashtan.MyApps.data.repositories.UserRepository;
import org.bashtan.MyApps.data.services.interfaces.ExpoPushServiceInterface;
import org.bashtan.MyApps.enums.UserRole;
import org.bashtan.MyApps.mappers.FcmTokenMapper;
import org.bashtan.MyApps.mappers.UserMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Log4j2
public class ExpoPushService implements ExpoPushServiceInterface {

    private static final String EXPO_PUSH_URL = "https://exp.host/--/api/v2/push/send";
    private final FcmTokenRepository fcmTokenRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void sendPushNotificationAllToken(PushMessage pushMessage) {
        fcmTokenRepository
                .findAll()
                .stream()
                .map(FcmTokenMapper::toFcmTokenDTO)
                .forEach(token -> {
                    pushMessage.setToken(token.getToken());
                    sendPush(pushMessage);
                    log.info("Send massage for token: {}", token.getToken());
                });

    }

    @Override
    public void sendAdminRegisterNewEmail(String email) {
        userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains(UserRole.ADMIN))
                .map(UserMapper::toUserDTO)
                .forEach(user -> {
                    fcmTokenRepository
                            .findById(user.getId())
                            .map(FcmTokenMapper::toFcmTokenDTO)
                            .ifPresent(dto -> {
                                sendPush(PushMessage
                                        .builder()
                                        .token(dto.getToken())
                                        .title("Register new Tester")
                                        .body(String.format("Email:%s", email))
                                        .build());
                                log.info("Send massage admin: {} for token: {}",
                                        user.getLogin(),
                                        dto.getToken());
                            });
                });
    }

    @Override
    public void sendPush(PushMessage pushMessage) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("to", pushMessage.getToken());
        objectMap.put("sound", "default");
        objectMap.put("title", pushMessage.getTitle());
        objectMap.put("body", pushMessage.getBody());
        if (pushMessage.getData() != null) {
            objectMap.put("data", pushMessage.getData());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(objectMap, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(EXPO_PUSH_URL, request, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to send push notification: " + response.getBody());
        }
    }
}
