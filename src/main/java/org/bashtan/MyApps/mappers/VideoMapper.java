package org.bashtan.MyApps.mappers;

import org.bashtan.MyApps.data.dto.church.youTube.VideoDTO;
import org.bashtan.MyApps.data.entities.chorch.youTube.VideoEntity;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

    public static VideoDTO toVideoDTO(VideoEntity videoEntity){
        return VideoDTO
                .builder()
                .id(videoEntity.getId())
                .title(videoEntity.getTitle())
                .url(videoEntity.getUrl())
                .date(videoEntity.getDate())
                .build();
    }
    public static VideoEntity toVideoEntity(VideoDTO videoDTO){
        return VideoEntity
                .builder()
                .id(videoDTO.getId())
                .title(videoDTO.getTitle())
                .url(videoDTO.getUrl())
                .date(videoDTO.getDate())
                .build();
    }
}