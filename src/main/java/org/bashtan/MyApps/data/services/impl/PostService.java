package org.bashtan.MyApps.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bashtan.MyApps.data.dto.DeleteRequestDTO;
import org.bashtan.MyApps.data.dto.church.post.CreatePostDTO;
import org.bashtan.MyApps.data.dto.church.post.PostDTO;
import org.bashtan.MyApps.data.repositories.PostRepository;
import org.bashtan.MyApps.data.services.interfaces.PostServiceInterface;
import org.bashtan.MyApps.data.services.interfaces.UserServiceInterface;
import org.bashtan.MyApps.exceptions.BadRequestException;
import org.bashtan.MyApps.exceptions.DatabaseException;
import org.bashtan.MyApps.exceptions.NotFoundException;
import org.bashtan.MyApps.mappers.PostMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class PostService implements PostServiceInterface {
    private final PostRepository postRepository;
    private final UserServiceInterface userService;

    @Value("${upload.path}")
    private String uploadPath;
    @Value("${app.base-url}")
    private String baseUrl;

    private final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @Override
    public void savePost(Long id, String title, String description, String eventDate, MultipartFile image) {
        userService.checkTestUser();
        try {
            if (id != null) {
                PostDTO editPostDTO = getPostByID(id);
                editPostDTO.setTitle(title);
                editPostDTO.setDescription(description);
                editPostDTO.setEventDate(normalizeDate(eventDate));
                if (image != null && !image.isEmpty()) {
                    deleteImage(editPostDTO.getImageUrl());
                    editPostDTO.setImageUrl(uploadImage(image));
                }
                postRepository.save(PostMapper.toPostEntity(editPostDTO));
                log.info("Successful edit Post");
            } else {
                CreatePostDTO createPostDTO =
                        CreatePostDTO
                                .builder()
                                .title(title)
                                .description(description)
                                .eventDate(
                                        (eventDate != null && !eventDate.isBlank())
                                                ? LocalDate
                                                .parse(LocalDate
                                                        .parse(eventDate, inputFormatter)
                                                        .format(outputFormatter), outputFormatter)
                                                : null
                                )
                                .imageUrl(uploadImage(image))
                                .build();
                postRepository.save(PostMapper.toPostEntity(createPostDTO));
                log.info("Successful create Post");
            }
        } catch (Exception e) {
            log.error("Error while create post", e);
            throw new DatabaseException("Error create post", e);
        }
    }

    @Override
    public void deletePost(DeleteRequestDTO deleteRequestDTO) {
        userService.checkTestUser();
        if (!deleteRequestDTO.isCheckDelete()) {
            log.warn("Attempt to delete stream without confirmation check");
            throw new BadRequestException("Confirmation check not installed: `checkDelete` must be true");
        }
        try {
            PostDTO postDTO = getPostByID(deleteRequestDTO.getId());
            deleteImage(postDTO.getImageUrl());
            postRepository.delete(PostMapper.toPostEntity(postDTO));
        } catch (Exception e) {
            log.error("Error while deleting stream", e);
            throw new DatabaseException("Error deleting stream from database", e);
        }
    }

    @Override
    public List<PostDTO> fetchAll() {
        return postRepository
                .findAll()
                .stream()
                .map(post -> PostMapper.toPostDTO(post, String.format("%s/%s", baseUrl, uploadPath)))
                .collect(Collectors.toList());
    }

    private LocalDate normalizeDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(dateStr, outputFormatter);
        } catch (DateTimeParseException e) {
            return LocalDate.parse(LocalDate.parse(dateStr, inputFormatter).format(outputFormatter), outputFormatter);
        }
    }

    private void deleteImage(String imagePath) {
        try {
            Path filePath = Paths.get(uploadPath).resolve(imagePath);
            // Проверка существования файла
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        } catch (IOException e) {
            throw new BadRequestException("Error deleting file");
        }
    }

    private String uploadImage(MultipartFile image) {
        if (image.isEmpty()) {
            throw new BadRequestException("Fail Empty");
        }
        try {
            // Генерация уникального имени
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path uploadDir = Paths.get(uploadPath);

            // Создание директории если нет
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Сохранение файла
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Вернуть URL или просто путь
            return fileName;
        } catch (IOException e) {
            throw new BadRequestException("Error upload");
        }
    }

    private PostDTO getPostByID(long id) {

        return postRepository.findById(id)
                .map(PostMapper::toPostDTO)
                .orElseThrow(() -> new NotFoundException("Post not found with ID: " + id));
    }

}
