package org.bashtan.MyApps.data.dto.user;

import lombok.*;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {

    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}
