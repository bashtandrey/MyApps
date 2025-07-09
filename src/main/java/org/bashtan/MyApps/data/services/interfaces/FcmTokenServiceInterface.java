package org.bashtan.MyApps.data.services.interfaces;


import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FcmTokenServiceInterface {

    void saveToken(Map<String, String> body, Authentication authentication);

    Optional<String> getTokenByUserId(Long userId);

    List<String> getAllTokens();
}
