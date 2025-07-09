package org.bashtan.MyApps.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bashtan.MyApps.data.dto.android.AndroidDTO;
import org.bashtan.MyApps.data.repositories.AndroidRepository;
import org.bashtan.MyApps.data.services.interfaces.AndroidServiceInterface;
import org.bashtan.MyApps.data.services.interfaces.EmailServiceInterface;
import org.bashtan.MyApps.data.services.interfaces.ExpoPushServiceInterface;
import org.bashtan.MyApps.mappers.AndroidMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class AndroidService implements AndroidServiceInterface {

    private final AndroidRepository androidRepository;
    private final EmailServiceInterface emailService;
    private final ExpoPushServiceInterface expoPushService;

    @Override
    public void testRequest(AndroidDTO androidDTO) {
        androidRepository.save(AndroidMapper.createUserEntity(androidDTO));
        log.info("Save email: {}", androidDTO.getEmail());
        expoPushService.sendAdminRegisterNewEmail(androidDTO.getEmail());
    }

    @Override
    public List<AndroidDTO> fetchAll() {
        return androidRepository.findAll()
                .stream()
                .map(AndroidMapper::toAndroidDTO)
                .filter(androidDTO -> !androidDTO.isAdded())
                .collect(Collectors.toList());
    }

    @Override
    public void sendRequest(AndroidDTO androidDTO) {
        if (androidDTO.getId() != null && androidDTO.getId() != 0) {
            androidRepository
                    .findById(androidDTO.getId()).ifPresent(entity -> {
                        entity.setAdded(true);
                        androidRepository.save(entity);
                    });
        }
        emailService.sendResponseAndroid(androidDTO.getEmail());
    }
}