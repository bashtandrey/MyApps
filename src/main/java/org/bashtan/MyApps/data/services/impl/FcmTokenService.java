package org.bashtan.MyApps.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bashtan.MyApps.configs.MyUserDetails;
import org.bashtan.MyApps.data.entities.FcmTokenEntity;
import org.bashtan.MyApps.data.repositories.FcmTokenRepository;
import org.bashtan.MyApps.data.services.interfaces.FcmTokenServiceInterface;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FcmTokenService implements FcmTokenServiceInterface {
    private final FcmTokenRepository fcmTokenRepository;

    @Override
    public void saveToken(Map<String, String> body, Authentication authentication) {
        String token = body.get("token");

        Long userId = null;
        if (authentication != null && authentication.getPrincipal() instanceof MyUserDetails) {
            userId = ((MyUserDetails) authentication.getPrincipal()).getId();
            log.info("User is authenticated, ID: {}", userId);
        }
        Optional<FcmTokenEntity> existing = fcmTokenRepository.findByToken(token);

        if (existing.isPresent()) {
            FcmTokenEntity t = existing.get();
            t.setCreatedAt(LocalDateTime.now());

            // Привязываем токен, если он был анонимным
            if (userId != null && t.getUserId() == null) {
                t.setUserId(userId);
                log.info("Token {} now associated with user {}", token, userId);

                // Удаляем все другие токены пользователя (оставляя текущий)
                fcmTokenRepository.deleteAllByUserIdAndTokenNot(userId, token);
            }
            fcmTokenRepository.save(t);
        } else {
            // Если авторизован — удаляем все предыдущие токены этого пользователя
            if (userId != null) {
                fcmTokenRepository.deleteAllByUserId(userId);
            }
            // Сохраняем новый токен (анонимный или привязанный)
            fcmTokenRepository.save(FcmTokenEntity
                    .builder()
                    .token(token)
                    .userId(userId)
                    .createdAt(LocalDateTime.now())
                    .build());

            log.info("Saved new token: {}, userId: {}", token, userId);
        }
    }

    @Override
    public Optional<String> getTokenByUserId(Long userId) {
        return fcmTokenRepository.findFirstByUserIdOrderByCreatedAtDesc(userId)
                .map(FcmTokenEntity::getToken);
    }

    @Override
    public List<String> getAllTokens() {
        return fcmTokenRepository.findAllByUserIdIsNotNull().stream()
                .map(FcmTokenEntity::getToken)
                .toList();
    }
}