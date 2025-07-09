package org.bashtan.MyApps.data.repositories;

import org.bashtan.MyApps.data.entities.chorch.AndroidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AndroidRepository extends JpaRepository<AndroidEntity, Long> {
}
