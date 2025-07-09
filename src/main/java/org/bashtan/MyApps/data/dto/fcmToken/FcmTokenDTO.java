package org.bashtan.MyApps.data.dto.fcmToken;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FcmTokenDTO {
    private Long id;
    private Long userId;
    private String token;
    private LocalDateTime createdAt;
}
