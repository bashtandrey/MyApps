package org.bashtan.MyApps.data.dto.church.youTube;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateVideoDTO {

    private String title;
    private String url;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate date;
}
