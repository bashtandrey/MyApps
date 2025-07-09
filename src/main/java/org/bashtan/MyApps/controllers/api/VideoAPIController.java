package org.bashtan.MyApps.controllers.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.bashtan.MyApps.data.dto.DeleteRequestDTO;
import org.bashtan.MyApps.data.dto.church.youTube.CreateVideoDTO;
import org.bashtan.MyApps.data.dto.church.youTube.VideoDTO;
import org.bashtan.MyApps.data.services.interfaces.VideoServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/video")
@Transactional
public class VideoAPIController {
    private final VideoServiceInterface videoService;

    private static final String CREATE = "/create";
    private static final String EDITE = "/edit";
    private static final String DELETE = "/delete";
    private static final String FETCH_ALL = "/fetchAll";
    private static final String SEARCH_BY_SYMBOL = "/searchBySymbol";

    @PostMapping(CREATE)
    @PreAuthorize("hasRole('ROLE_VIDEO_ADMIN')")
    public ResponseEntity<Void> createVideo(@RequestBody CreateVideoDTO createVideoDTO) {
        videoService.createVideo(createVideoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(EDITE)
    @PreAuthorize("hasRole('ROLE_VIDEO_ADMIN')")
    public ResponseEntity<Void> editVideo(@RequestBody VideoDTO videoDTO) {
        videoService.editVideo(videoDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(DELETE)
    @PreAuthorize("hasRole('ROLE_VIDEO_ADMIN')")
    public ResponseEntity<Void> deleteStream(@RequestBody DeleteRequestDTO deleteRequestDTO) {
        videoService.deleteStream(deleteRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(FETCH_ALL)
    @PermitAll
    public ResponseEntity<List<VideoDTO>> fetchAllVideo() {
        List<VideoDTO> videoDTOS = videoService.fetchAllVideo();
        return new ResponseEntity<>(videoDTOS, HttpStatus.OK);
    }

    @GetMapping(SEARCH_BY_SYMBOL)
    @PreAuthorize("hasAnyRole('ROLE_VIDEO_ADMIN','ROLE_VIDEO_USER')")
    public ResponseEntity<List<VideoDTO>> fetchStreamBySymbol(
            @RequestParam(value = "target_symbol") String targetSymbol) {
        List<VideoDTO> stream = videoService.fetchStreamBySymbol(targetSymbol);
        return new ResponseEntity<>(stream, HttpStatus.OK);
    }
}
