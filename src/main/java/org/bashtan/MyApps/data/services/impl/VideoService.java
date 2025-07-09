package org.bashtan.MyApps.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bashtan.MyApps.data.dto.DeleteRequestDTO;
import org.bashtan.MyApps.data.dto.PushMessage;
import org.bashtan.MyApps.data.dto.church.youTube.CreateVideoDTO;
import org.bashtan.MyApps.data.dto.church.youTube.VideoDTO;
import org.bashtan.MyApps.data.entities.chorch.youTube.VideoEntity;
import org.bashtan.MyApps.data.repositories.VideoRepository;
import org.bashtan.MyApps.data.services.interfaces.ExpoPushServiceInterface;
import org.bashtan.MyApps.data.services.interfaces.SearchServiceInterface;
import org.bashtan.MyApps.data.services.interfaces.UserServiceInterface;
import org.bashtan.MyApps.data.services.interfaces.VideoServiceInterface;
import org.bashtan.MyApps.exceptions.BadRequestException;
import org.bashtan.MyApps.exceptions.DatabaseException;
import org.bashtan.MyApps.exceptions.NotFoundException;
import org.bashtan.MyApps.mappers.VideoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class VideoService implements VideoServiceInterface {
    private final VideoRepository videoRepository;
    private final SearchServiceInterface searchService;
    private final UserServiceInterface userService;
    private final ExpoPushServiceInterface expoPushService;

    @Override
    public void createVideo(CreateVideoDTO createVideoDTO) {
        userService.checkTestUser();
        validateStreamDTO(createVideoDTO);
        try {
            videoRepository.save(VideoEntity
                    .builder()
                    .title(createVideoDTO.getTitle())
                    .url(createVideoDTO.getUrl())
                    .date(createVideoDTO.getDate())
                    .build());
            PushMessage pushMessage = PushMessage.builder()
                    .title("Добавлено новое видео")
                    .body(createVideoDTO.getTitle())
                    .build();
            expoPushService.sendPushNotificationAllToken(pushMessage);
        } catch (Exception e) {
            log.error("Error while create stream", e);
            throw new DatabaseException("Error create stream", e);
        }
    }

    @Override
    public void editVideo(VideoDTO videoDTO) {
        userService.checkTestUser();
        VideoDTO findVideoDTO = getStreamByID(videoDTO.getId());
        if (videoDTO.getTitle() != null && !videoDTO.getTitle().trim().isBlank()) {
            findVideoDTO.setTitle(videoDTO.getTitle());
        }
        if (videoDTO.getUrl() != null && !videoDTO.getUrl().trim().isBlank()) {
            findVideoDTO.setUrl(videoDTO.getUrl());
        }
        if (videoDTO.getDate() != null) {
            findVideoDTO.setDate(videoDTO.getDate());
        }
        videoRepository.findByTitleAndUrl(
                        findVideoDTO.getTitle(),
                        findVideoDTO.getUrl()
                ).filter(stream -> stream.getId() != findVideoDTO.getId())
                .ifPresent(person -> {
                    throw new BadRequestException("Such a stream already exists..");
                });
        try {
            videoRepository.save(VideoMapper.toVideoEntity(findVideoDTO));
        } catch (Exception e) {
            log.error("Error while edit user", e);
            throw new DatabaseException("Error edit user", e);
        }
    }

    @Override
    public void deleteStream(DeleteRequestDTO deleteRequestDTO) {
        userService.checkTestUser();
        if (!deleteRequestDTO.isCheckDelete()) {
            log.warn("Attempt to delete stream without confirmation check");
            throw new BadRequestException("Confirmation check not installed: `checkDelete` must be true");
        }
        try {
            videoRepository.delete(VideoMapper
                    .toVideoEntity(getStreamByID(deleteRequestDTO.getId())));
        } catch (Exception e) {
            log.error("Error while deleting stream", e);
            throw new DatabaseException("Error deleting stream from database", e);
        }
    }

    @Override
    public List<VideoDTO> fetchAllVideo() {
        return videoRepository
                .findAll()
                .stream()
                .map(VideoMapper::toVideoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VideoDTO> fetchStreamBySymbol(String targetSymbol) {
        return searchService.searchStreamBySymbol(targetSymbol);
    }

    public VideoDTO getStreamByID(long id) {
        return videoRepository.findById(id).map(VideoMapper::toVideoDTO)
                .orElseThrow(() -> new NotFoundException("Stream not found with ID: " + id));
    }

    private void validateStreamDTO(CreateVideoDTO createVideoDTO) {
        if (createVideoDTO.getTitle() == null || createVideoDTO.getTitle().trim().isEmpty()) {
            throw new BadRequestException("Title cannot be null or empty.");
        }
        if (createVideoDTO.getUrl() == null || createVideoDTO.getUrl().trim().isEmpty()) {
            throw new BadRequestException("Url cannot be null or empty.");
        }
        if (createVideoDTO.getDate() == null) {
            throw new BadRequestException("Date cannot be null or empty.");
        }
    }

}
