package org.bashtan.MyApps.data.dto.library.people;

import lombok.*;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class DeletePeopleRequestDTO {

    private long id;
    private boolean checkDelete;
}
