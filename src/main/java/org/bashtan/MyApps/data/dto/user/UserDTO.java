package org.bashtan.MyApps.data.dto.user;

import lombok.*;
import org.bashtan.MyApps.enums.UserRole;

import java.util.Set;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Set<UserRole> roles;
    private boolean testUser;
    private boolean enabled;
    private boolean superUser;
    private boolean emailVerified;

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", enabled=" + enabled +
                ", superUser=" + superUser +
                ", testUser=" + testUser +
                ", emailVerified=" + emailVerified +
                '}';
    }
}
