package org.bashtan.MyApps.mappers;

import org.bashtan.MyApps.data.dto.fcmToken.FcmTokenDTO;
import org.bashtan.MyApps.data.entities.FcmTokenEntity;
import org.springframework.stereotype.Component;

@Component
public class FcmTokenMapper {
    public static FcmTokenEntity toFcmTokenEntity(FcmTokenDTO fcmTokenDTO) {
        return FcmTokenEntity
                .builder()
                .id(fcmTokenDTO.getId())
                .userId(fcmTokenDTO.getUserId())
                .token(fcmTokenDTO.getToken())
                .createdAt(fcmTokenDTO.getCreatedAt())
                .build();
    }

    public static FcmTokenDTO toFcmTokenDTO(FcmTokenEntity fcmTokenEntity) {
        return FcmTokenDTO
                .builder()
                .id(fcmTokenEntity.getId())
                .userId(fcmTokenEntity.getUserId())
                .token(fcmTokenEntity.getToken())
                .createdAt(fcmTokenEntity.getCreatedAt())
                .build();
    }
}
