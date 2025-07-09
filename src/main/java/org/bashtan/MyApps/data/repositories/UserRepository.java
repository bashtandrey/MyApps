package org.bashtan.MyApps.data.repositories;

import org.bashtan.MyApps.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findBySuperUser(boolean bol);

    List<UserEntity> findByLoginContainingIgnoreCase(String targetSymbol);

}
