package org.bashtan.MyApps.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bashtan.MyApps.data.dto.appVersion.AppVersionDTO;
import org.bashtan.MyApps.data.entities.chorch.AppVersionEntity;
import org.bashtan.MyApps.data.repositories.AppVersionRepository;
import org.bashtan.MyApps.data.services.interfaces.AppVersionServiceInterface;
import org.bashtan.MyApps.enums.Platform;
import org.bashtan.MyApps.exceptions.DatabaseException;
import org.bashtan.MyApps.exceptions.NotFoundException;
import org.bashtan.MyApps.mappers.AppVersionMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@RequiredArgsConstructor
@Service
@Log4j2
public class AppVersionService implements AppVersionServiceInterface {

    private final AppVersionRepository repository;


    @Override
    public void createAppVersion(AppVersionDTO appVersionDTO) {
        try {
            repository.save(AppVersionMapper.toAppVersionEntity(appVersionDTO));

        } catch (Exception e) {
            log.error("Error while create appVersion", e);
            throw new DatabaseException("Error create appVersion", e);
        }
    }

    @Override
    public AppVersionDTO getLastVersionForPlatform(Platform platform) {
        System.out.println(platform.name());
        return AppVersionMapper.toAppVersionDTO(
                repository.findByPlatform(platform).stream()
                        .max(Comparator.comparing(AppVersionEntity::getLatestVersion, this::compareVersions))
                        .orElseThrow(() -> new NotFoundException("Версия для платформы " + platform + " не найдена"))
        );
    }


    private int compareVersions(String v1, String v2) {
        String[] parts1 = v1.split("\\.");
        String[] parts2 = v2.split("\\.");

        int length = Math.max(parts1.length, parts2.length);
        for (int i = 0; i < length; i++) {
            int num1 = i < parts1.length ? Integer.parseInt(parts1[i]) : 0;
            int num2 = i < parts2.length ? Integer.parseInt(parts2[i]) : 0;
            if (num1 != num2) {
                return Integer.compare(num1, num2);
            }
        }
        return 0; // equal
    }
}
