package org.bashtan.MyApps.data.entities.chorch;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "about_service")
public class AboutServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Builder.Default
    private Long id = 0L;
    @Column(name = "name_service")
    private String nameService;
    @Column(name = "time_service")
    private String timeService;
    
}
