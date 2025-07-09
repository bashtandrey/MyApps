package org.bashtan.MyApps.data.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blacklisted_tokens")
public class BlacklistedTokenEntity {

    @Id
    private String token;

    @Column(name = "date_added",nullable = false)
    @Builder.Default
    private LocalDateTime dateAdded = LocalDateTime.now();

}

