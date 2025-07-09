package org.bashtan.MyApps.controllers.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.bashtan.MyApps.data.dto.DeleteRequestDTO;
import org.bashtan.MyApps.data.dto.aboutService.AboutServiceDTO;
import org.bashtan.MyApps.data.services.interfaces.AboutServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/about")
@RestController
public class AboutController {

    private final AboutServiceInterface aboutService;
    private static final String CREATE = "/create";
    private static final String FETCH_ALL = "/fetchAll";
    private static final String DELETE = "/delete";


    @PostMapping(CREATE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> signUp(@RequestBody AboutServiceDTO aboutServiceDTO) {
        aboutService.createAboutService(aboutServiceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(FETCH_ALL)
    @PermitAll
    public ResponseEntity<List<AboutServiceDTO>> fetchAllUser() {
        return new ResponseEntity<>(aboutService.fetchAllAboutService(), HttpStatus.OK);
    }

    @DeleteMapping(DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@RequestBody DeleteRequestDTO deleteUserRequest) {
        aboutService.deleteAboutService(deleteUserRequest);
        return ResponseEntity.noContent().build();
    }
}
