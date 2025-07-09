package org.bashtan.MyApps.data.dto.library.people;

import lombok.*;
import org.bashtan.MyApps.enums.Gender;
import org.bashtan.MyApps.enums.UserRole;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponsePeopleDTO {

    private long id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Gender gender;
    private String phone;
}
