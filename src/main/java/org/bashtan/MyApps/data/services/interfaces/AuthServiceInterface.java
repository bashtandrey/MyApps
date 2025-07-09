package org.bashtan.MyApps.data.services.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bashtan.MyApps.data.dto.authorisation.AuthorizationUserDTO;
import org.bashtan.MyApps.data.dto.authorisation.SignInDTO;
import org.bashtan.MyApps.data.dto.user.CreateUserDTO;


public interface AuthServiceInterface {
    AuthorizationUserDTO signIn(SignInDTO signInDTO, HttpServletResponse response, boolean forMobileClient);

    void logOut(HttpServletRequest request, HttpServletResponse response);

    void signUp(CreateUserDTO createUserDTO);
}
