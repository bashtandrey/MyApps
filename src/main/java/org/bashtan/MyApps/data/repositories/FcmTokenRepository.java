package org.bashtan.MyApps.data.repositories;

import org.bashtan.MyApps.data.entities.FcmTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FcmTokenRepository extends JpaRepository<FcmTokenEntity, Long> {
    Optional<FcmTokenEntity> findByToken(String token);

    void deleteAllByUserId(Long userId);

    void deleteAllByUserIdAndTokenNot(Long userId, String token);

    Optional<FcmTokenEntity> findFirstByUserIdOrderByCreatedAtDesc(Long userId);

    List<FcmTokenEntity> findAllByUserIdIsNotNull();

}
