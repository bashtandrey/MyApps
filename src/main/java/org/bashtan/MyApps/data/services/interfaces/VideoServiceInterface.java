package org.bashtan.MyApps.data.services.interfaces;
import org.bashtan.MyApps.data.dto.DeleteRequestDTO;
import org.bashtan.MyApps.data.dto.church.youTube.CreateVideoDTO;
import org.bashtan.MyApps.data.dto.church.youTube.VideoDTO;

import java.util.List;

public interface VideoServiceInterface {
    void createVideo(CreateVideoDTO streamDTO);
    void editVideo(VideoDTO videoDTO);
    void deleteStream(DeleteRequestDTO deleteRequestDTO);
    List<VideoDTO> fetchAllVideo();
    List<VideoDTO> fetchStreamBySymbol(String targetSymbol);



}
