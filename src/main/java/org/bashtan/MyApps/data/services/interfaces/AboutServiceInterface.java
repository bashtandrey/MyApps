package org.bashtan.MyApps.data.services.interfaces;

import org.bashtan.MyApps.data.dto.DeleteRequestDTO;
import org.bashtan.MyApps.data.dto.aboutService.AboutServiceDTO;

import java.util.List;

public interface AboutServiceInterface {
    void createAboutService(AboutServiceDTO aboutServiceDTO);

    void deleteAboutService(DeleteRequestDTO deleteRequestDTO);

    List<AboutServiceDTO> fetchAllAboutService();
}
