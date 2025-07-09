package org.bashtan.MyApps.data.repositories;

import org.bashtan.MyApps.data.entities.library.PeopleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<PeopleEntity,Long> {
    Optional<PeopleEntity> findByFirstNameAndLastNameAndBirthday(String firstName, String lastName, LocalDate birthday);
}
