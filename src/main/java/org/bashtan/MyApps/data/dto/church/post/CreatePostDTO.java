package org.bashtan.MyApps.data.dto.church.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CreatePostDTO {

    private String title;
    private String description;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate eventDate;
    private String imageUrl;

    @Override
    public String toString() {
        return "CreatePostDTO{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", eventDate=" + eventDate +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
