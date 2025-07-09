package org.bashtan.MyApps.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "email_verification_token")
public class EmailVerificationTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = UserEntity.class)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;

    private Date expiryDate;

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public boolean isExpired() {
        return expiryDate.before(new Date());
    }
}
