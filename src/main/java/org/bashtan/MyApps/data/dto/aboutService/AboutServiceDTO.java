package org.bashtan.MyApps.data.dto.aboutService;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AboutServiceDTO {
    private Long id;
    private String nameService;
    private String timeService;
}
