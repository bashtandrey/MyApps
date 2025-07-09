package org.bashtan.MyApps.data.dto.church.youTube;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class VideoDTO {

    private long id;
    private String title;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate date;
    private String url;
}
