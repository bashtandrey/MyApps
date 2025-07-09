package org.bashtan.MyApps.data.dto.user;

import lombok.*;
import org.bashtan.MyApps.enums.UserRole;

import java.util.Set;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseUserDTO {

    private long id;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private Set<UserRole> roles;
    private boolean enabled;
    private boolean superUser;
    private boolean testUser;
    private boolean emailVerified;
}
