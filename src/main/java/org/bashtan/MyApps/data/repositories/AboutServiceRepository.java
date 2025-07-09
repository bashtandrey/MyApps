package org.bashtan.MyApps.data.repositories;

import org.bashtan.MyApps.data.entities.chorch.AboutServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutServiceRepository extends JpaRepository<AboutServiceEntity, Long> {
}