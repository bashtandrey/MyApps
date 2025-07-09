package org.bashtan.MyApps.data.repositories;

import org.bashtan.MyApps.data.entities.library.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,Long> {
    Optional<Object> findBySerial(String serial);
}
