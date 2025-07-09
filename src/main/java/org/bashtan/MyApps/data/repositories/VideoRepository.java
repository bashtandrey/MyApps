package org.bashtan.MyApps.data.repositories;


import org.bashtan.MyApps.data.entities.chorch.youTube.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VideoRepository extends JpaRepository<VideoEntity,Long> {

    Optional<VideoEntity> findByTitleAndUrl(String title, String url);
}
