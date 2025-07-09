package org.bashtan.MyApps.data.dto;

import lombok.*;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class DeleteRequestDTO {

    private long id;
    private boolean checkDelete;
}
