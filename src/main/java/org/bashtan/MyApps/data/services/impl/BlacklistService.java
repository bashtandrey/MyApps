package org.bashtan.MyApps.data.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bashtan.MyApps.data.entities.BlacklistedTokenEntity;
import org.bashtan.MyApps.data.repositories.BlacklistedTokenRepository;
import org.bashtan.MyApps.data.services.interfaces.BlacklistServiceInterface;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BlacklistService implements BlacklistServiceInterface {

    private final BlacklistedTokenRepository blacklistedTokenRepository;

    public void addTokenToBlacklist(String token) {;
        blacklistedTokenRepository.save(BlacklistedTokenEntity
                .builder()
                .token(token)
                .build());
    }

    public boolean isTokenBlacklisted(String token) {
//        return true;
        return blacklistedTokenRepository.existsByToken(token);
    }

}
