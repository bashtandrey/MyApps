package org.bashtan.MyApps.data.dto.authorisation;

import lombok.*;
import org.bashtan.MyApps.enums.UserRole;

import java.util.Set;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthorizationUserDTO {

    private long id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private boolean emailVerified;
    private Set<UserRole> roles;
    private String accessToken;
    private boolean superUser;
}
