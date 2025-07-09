package org.bashtan.MyApps.data.dto.android;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AndroidDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean added;


}
