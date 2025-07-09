package org.bashtan.MyApps.mappers;

import org.bashtan.MyApps.data.dto.android.AndroidDTO;
import org.bashtan.MyApps.data.entities.chorch.AndroidEntity;
import org.springframework.stereotype.Component;

@Component
public class AndroidMapper {
    public static AndroidDTO toAndroidDTO(AndroidEntity androidEntity) {
        return AndroidDTO.builder()
                .id(androidEntity.getId())
                .email(androidEntity.getEmail().toLowerCase())
                .firstName(androidEntity.getFirstName())
                .lastName(androidEntity.getLastName())
                .added(androidEntity.isAdded())
                .build();
    }

    public static AndroidEntity toUserEntity(AndroidDTO androidDTO) {
        return AndroidEntity.builder()
                .id(androidDTO.getId())
                .email(androidDTO.getEmail().toLowerCase())
                .firstName(androidDTO.getFirstName())
                .lastName(androidDTO.getLastName())
                .added(androidDTO.isAdded())
                .build();
    }

    public static AndroidEntity createUserEntity(AndroidDTO androidDTO) {
        return AndroidEntity.builder()
                .email(androidDTO.getEmail().toLowerCase())
                .firstName(androidDTO.getFirstName())
                .lastName(androidDTO.getLastName())
                .build();
    }
}
