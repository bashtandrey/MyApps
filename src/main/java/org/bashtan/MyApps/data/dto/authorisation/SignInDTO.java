package org.bashtan.MyApps.data.dto.authorisation;

import lombok.*;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class SignInDTO {

    private String login;
    private String password;
}
