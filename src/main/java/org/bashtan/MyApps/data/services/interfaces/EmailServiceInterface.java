package org.bashtan.MyApps.data.services.interfaces;

import org.bashtan.MyApps.data.dto.user.UserDTO;

public interface EmailServiceInterface {
    void sendEmail(UserDTO userDTO);

    String confirmEmail(String token);

    void resendEmail(UserDTO userDTO);

    void deleteToken(UserDTO userDTO);

    void sendResponseAndroid(String email);
}
