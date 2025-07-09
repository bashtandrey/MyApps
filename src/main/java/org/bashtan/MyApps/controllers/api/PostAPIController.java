package org.bashtan.MyApps.controllers.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.bashtan.MyApps.data.dto.DeleteRequestDTO;
import org.bashtan.MyApps.data.dto.church.post.PostDTO;
import org.bashtan.MyApps.data.services.interfaces.PostServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
@Transactional
public class PostAPIController {
    private final PostServiceInterface postService;

    private static final String SAVE = "/save";
    private static final String DELETE = "/delete";
    private static final String FETCH_ALL = "/fetchAll";

    @PostMapping(value = SAVE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('POST_EDITOR')")
    public ResponseEntity<Void> savePost(
            @RequestPart(value = "id", required = false) String id,
            @RequestPart(value = "title", required = false) String title,
            @RequestPart(value = "description", required = false) String description,
            @RequestPart(value = "eventDate", required = false) String eventDate,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        postService.savePost(
                (id != null && !id.isBlank()) ? Long.valueOf(id) : null,
                title,
                description,
                eventDate,
                image);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(DELETE)
    @PreAuthorize("hasRole('POST_EDITOR')")
    public ResponseEntity<Void> deleteStream(@RequestBody DeleteRequestDTO deleteRequestDTO) {
        postService.deletePost(deleteRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @PermitAll
    @GetMapping(FETCH_ALL)
    public ResponseEntity<List<PostDTO>> fetchAll() {
        List<PostDTO> posts = postService.fetchAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}