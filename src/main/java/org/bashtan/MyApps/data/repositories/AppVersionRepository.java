package org.bashtan.MyApps.data.repositories;

import org.bashtan.MyApps.data.entities.chorch.AppVersionEntity;
import org.bashtan.MyApps.enums.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppVersionRepository extends JpaRepository<AppVersionEntity, Long> {
    List<AppVersionEntity> findByPlatform(Platform platform);

}