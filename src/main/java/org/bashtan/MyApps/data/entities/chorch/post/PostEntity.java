package org.bashtan.MyApps.data.entities.chorch.post;

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
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Builder.Default
    private long id = 0L;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Builder.Default
    @Column(name = "created_at")
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "event_date")
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate eventDate;
}