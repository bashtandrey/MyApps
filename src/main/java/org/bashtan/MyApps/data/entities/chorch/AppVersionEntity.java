package org.bashtan.MyApps.data.entities.chorch;

import jakarta.persistence.*;
import lombok.*;
import org.bashtan.MyApps.enums.Platform;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_versions")
public class AppVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Platform platform;
    private String latestVersion;
    private String storeUrl;
}
