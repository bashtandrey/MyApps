package org.bashtan.MyApps.controllers.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.bashtan.MyApps.data.dto.appVersion.AppVersionDTO;
import org.bashtan.MyApps.data.services.interfaces.AppVersionServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/appVersion")
@RestController
public class AppVersionController {

    private final AppVersionServiceInterface appVersionService;
    private static final String CREATE = "/create";
    private static final String VERSION = "/version";


    @PostMapping(CREATE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> CreateVersion(@RequestBody AppVersionDTO appVersionDTO) {
        appVersionService.createAppVersion(appVersionDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping(VERSION)
    @PermitAll
    public ResponseEntity<AppVersionDTO> getLatestVersion(@RequestBody AppVersionDTO appVersionDTO) {
        return new ResponseEntity<>(
                appVersionService
                        .getLastVersionForPlatform(appVersionDTO.getPlatform()), HttpStatus.OK);
    }
}
