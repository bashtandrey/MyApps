package org.bashtan.MyApps.data.services.interfaces;

import org.bashtan.MyApps.data.dto.DeleteRequestDTO;
import org.bashtan.MyApps.data.dto.church.post.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostServiceInterface {
    void savePost(Long id, String title, String description, String eventDate, MultipartFile image);
    void deletePost(DeleteRequestDTO deleteRequestDTO);
    List<PostDTO> fetchAll();

}
