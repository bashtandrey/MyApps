package org.bashtan.MyApps.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fcm_token")
public class FcmTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "token", unique = true)
    private String token;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
