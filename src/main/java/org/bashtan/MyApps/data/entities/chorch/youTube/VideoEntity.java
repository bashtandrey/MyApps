package org.bashtan.MyApps.data.entities.chorch.youTube;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "youTube")
public class VideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    @Builder.Default
    private long id = 0L;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date", nullable = false)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate date;

    @Column(name = "url", nullable = false)
    private String url;


}
