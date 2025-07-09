package org.bashtan.MyApps.data.repositories;

import org.bashtan.MyApps.data.entities.EmailVerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailVerificationTokenRepository extends
        JpaRepository<EmailVerificationTokenEntity, UUID> {
    Optional<EmailVerificationTokenEntity> findByToken(String token);

    Optional<EmailVerificationTokenEntity> findByUserId(long id);
}
