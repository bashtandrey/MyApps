package org.bashtan.MyApps.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bashtan.MyApps.data.dto.DeleteRequestDTO;
import org.bashtan.MyApps.data.dto.aboutService.AboutServiceDTO;
import org.bashtan.MyApps.data.repositories.AboutServiceRepository;
import org.bashtan.MyApps.data.services.interfaces.AboutServiceInterface;
import org.bashtan.MyApps.exceptions.DatabaseException;
import org.bashtan.MyApps.exceptions.NotFoundException;
import org.bashtan.MyApps.mappers.AboutServiceMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class AboutService implements AboutServiceInterface {
    private final AboutServiceRepository aboutServiceRepository;

    @Override
    public void createAboutService(AboutServiceDTO aboutServiceDTO) {
        try {
            aboutServiceRepository.save(AboutServiceMapper.toAboutServiceEntity(aboutServiceDTO));
        } catch (Exception e) {
            log.error("Error while create About Service", e);
            throw new DatabaseException("Error create About Service", e);
        }
    }

    @Override
    public void deleteAboutService(DeleteRequestDTO deleteRequestDTO) {
        AboutServiceDTO aboutService = getAboutServiceByID(deleteRequestDTO.getId());
        try {
            aboutServiceRepository
                    .delete(AboutServiceMapper
                            .toAboutServiceEntity(aboutService));
        } catch (Exception e) {
            log.error("Error while deleting AboutService", e);
            throw new DatabaseException("Error deleting AboutService from database", e);
        }
    }

    @Override
    public List<AboutServiceDTO> fetchAllAboutService() {
        return aboutServiceRepository.findAll()
                .stream()
                .map(AboutServiceMapper::toAboutServiceDTO)
                .collect(Collectors.toList());
    }

    private AboutServiceDTO getAboutServiceByID(Long id) {
        return aboutServiceRepository.findById(id)
                .map(AboutServiceMapper::toAboutServiceDTO)
                .orElseThrow(() -> new NotFoundException("About Service not found with ID: " + id));
    }

}
