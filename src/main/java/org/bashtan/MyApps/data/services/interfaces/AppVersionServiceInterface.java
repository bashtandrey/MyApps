package org.bashtan.MyApps.data.services.interfaces;

import org.bashtan.MyApps.data.dto.appVersion.AppVersionDTO;
import org.bashtan.MyApps.enums.Platform;

public interface AppVersionServiceInterface {
    void createAppVersion(AppVersionDTO appVersionDTO);

    AppVersionDTO getLastVersionForPlatform(Platform platform);
}
