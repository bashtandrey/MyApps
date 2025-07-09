package org.bashtan.MyApps.mappers;

import org.bashtan.MyApps.data.dto.aboutService.AboutServiceDTO;
import org.bashtan.MyApps.data.entities.chorch.AboutServiceEntity;
import org.springframework.stereotype.Component;

@Component
public class AboutServiceMapper {
    public static AboutServiceDTO toAboutServiceDTO(AboutServiceEntity aboutServiceEntity) {
        if (aboutServiceEntity == null) return null;
        return AboutServiceDTO
                .builder()
                .id(aboutServiceEntity.getId())
                .nameService(aboutServiceEntity.getNameService())
                .timeService(aboutServiceEntity.getTimeService())
                .build();
    }

    public static AboutServiceEntity toAboutServiceEntity(AboutServiceDTO aboutServiceDTO) {
        return AboutServiceEntity
                .builder()
                .timeService(aboutServiceDTO.getTimeService())
                .nameService(aboutServiceDTO.getNameService())
                .build();
    }
}
