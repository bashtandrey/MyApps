package org.bashtan.MyApps.data.repositories;

import org.bashtan.MyApps.data.entities.library.BookHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookHistoryRepository extends JpaRepository<BookHistoryEntity, Long> {
}
