package org.bashtan.MyApps.data.dto.library.people;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.*;
import org.bashtan.MyApps.enums.Gender;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class CreatePeopleDTO {

    private String lastName;
    private String firstName;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate birthday;
    private Gender gender;
    @Builder.Default
    private String phone ="";
}
