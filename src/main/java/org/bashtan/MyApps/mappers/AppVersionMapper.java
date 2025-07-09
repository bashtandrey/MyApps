package org.bashtan.MyApps.mappers;

import org.bashtan.MyApps.data.dto.appVersion.AppVersionDTO;
import org.bashtan.MyApps.data.entities.chorch.AppVersionEntity;
import org.springframework.stereotype.Component;

@Component
public class AppVersionMapper {
    public static AppVersionDTO toAppVersionDTO(AppVersionEntity appVersionEntity) {
        return AppVersionDTO
                .builder()
                .platform(appVersionEntity.getPlatform())
                .latestVersion(appVersionEntity.getLatestVersion())
                .storeUrl(appVersionEntity.getStoreUrl())
                .build();
    }

    public static AppVersionEntity toAppVersionEntity(AppVersionDTO appVersionDTO) {
        return AppVersionEntity
                .builder()
                .platform(appVersionDTO.getPlatform())
                .latestVersion(appVersionDTO.getLatestVersion())
                .storeUrl(appVersionDTO.getStoreUrl())
                .build();
    }
}
