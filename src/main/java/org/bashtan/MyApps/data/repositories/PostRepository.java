package org.bashtan.MyApps.data.repositories;


import org.bashtan.MyApps.data.entities.chorch.post.PostEntity;
import org.bashtan.MyApps.data.entities.chorch.youTube.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {
}
