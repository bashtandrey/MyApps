package org.bashtan.MyApps.data.entities;

import jakarta.persistence.*;
import lombok.*;
import org.bashtan.MyApps.enums.UserRole;

import java.util.HashSet;
import java.util.Set;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    @Builder.Default
    private Long id = 0L;

    @Column(name = "login", length = 50, nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<UserRole> roles = new HashSet<>(Set.of(UserRole.GUEST));


    @Builder.Default
    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

    @Builder.Default
    @Column(name = "superUser", nullable = false, updatable = false)
    private boolean superUser = false;

    @Builder.Default
    @Column(name = "test_user")
    private boolean testUser = false;

    @Builder.Default
    @Column(name = "emailVerified", nullable = false)
    private boolean emailVerified = false;
}
