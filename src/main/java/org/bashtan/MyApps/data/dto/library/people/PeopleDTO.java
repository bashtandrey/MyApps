package org.bashtan.MyApps.data.dto.library.people;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@ToString
public class PeopleDTO {

    private long id;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate birthday;
    private Gender gender;
    private String phone;
    private boolean remotePeople;
}
