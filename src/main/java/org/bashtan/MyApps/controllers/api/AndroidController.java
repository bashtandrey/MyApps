package org.bashtan.MyApps.controllers.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.bashtan.MyApps.data.dto.android.AndroidDTO;
import org.bashtan.MyApps.data.services.interfaces.AndroidServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/android")
@RestController
@Transactional
public class AndroidController {

    private final AndroidServiceInterface androidService;
    private static final String TEST_REQUEST = "/test-request";
    private static final String FETCH_ALL = "/fetchAll";
    private static final String SEND = "/send";


    @PostMapping(TEST_REQUEST)
    @PermitAll
    public ResponseEntity<Void> testRequest(@RequestBody AndroidDTO androidDTO) {
        androidService.testRequest(androidDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(FETCH_ALL)
    @PermitAll
    public ResponseEntity<List<AndroidDTO>> fetchAllAdmin() {
        List<AndroidDTO> users = androidService.fetchAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(SEND)
    @PermitAll
    public ResponseEntity<List<AndroidDTO>> sendRequest(@RequestBody AndroidDTO androidDTO) {
        androidService.sendRequest(androidDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
