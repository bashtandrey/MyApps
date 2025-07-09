package org.bashtan.MyApps.data.repositories;


import org.bashtan.MyApps.data.entities.BlacklistedTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedTokenEntity, String> {
    boolean existsByToken(String token);
}
