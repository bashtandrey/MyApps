package org.bashtan.MyApps.data.dto.church.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private long id;
    private String title;
    private String description;
    private String imageUrl;
    @Builder.Default
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate createdAt = LocalDate.now();
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate eventDate;

    @Override
    public String toString() {
        return "PostDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", createdAt=" + createdAt +
                ", eventDate=" + eventDate +
                '}';
    }
}
