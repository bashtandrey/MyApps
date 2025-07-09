package org.bashtan.MyApps.data.dto.appVersion;

import lombok.*;
import org.bashtan.MyApps.enums.Platform;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class AppVersionDTO {

    private Platform platform;
    private String latestVersion;
    private String storeUrl;
}
