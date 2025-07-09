package org.bashtan.MyApps.mappers;

import org.bashtan.MyApps.data.dto.church.post.CreatePostDTO;
import org.bashtan.MyApps.data.dto.church.post.PostDTO;
import org.bashtan.MyApps.data.entities.chorch.post.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public static PostEntity toPostEntity(CreatePostDTO createPostDTO) {
        return PostEntity.builder()
                .title(createPostDTO.getTitle())
                .description(createPostDTO.getDescription())
                .eventDate(createPostDTO.getEventDate())
                .imageUrl(createPostDTO.getImageUrl())
                .build();
    }

    public static PostEntity toPostEntity(PostDTO postDTO) {
        return PostEntity.builder()
                .id(postDTO.getId())
                .title(postDTO.getTitle())
                .description(postDTO.getDescription())
                .imageUrl(postDTO.getImageUrl())
                .createdAt(postDTO.getCreatedAt())
                .eventDate(postDTO.getEventDate())
                .build();
    }

    public static PostDTO toPostDTO(PostEntity postEntity) {
        return PostDTO.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .description(postEntity.getDescription())
                .imageUrl(postEntity.getImageUrl())
                .createdAt(postEntity.getCreatedAt())
                .eventDate(postEntity.getEventDate())
                .build();
    }

    public static PostDTO toPostDTO(PostEntity postEntity, String baseUrl) {
        return PostDTO.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .description(postEntity.getDescription())
                .imageUrl(baseUrl + postEntity.getImageUrl())
                .createdAt(postEntity.getCreatedAt())
                .eventDate(postEntity.getEventDate())
                .build();
    }
}
