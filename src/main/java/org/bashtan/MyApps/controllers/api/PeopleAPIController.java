package org.bashtan.MyApps.controllers.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.bashtan.MyApps.data.dto.library.people.CreatePeopleDTO;
import org.bashtan.MyApps.data.dto.library.people.DeletePeopleRequestDTO;
import org.bashtan.MyApps.data.dto.library.people.PeopleDTO;
import org.bashtan.MyApps.data.dto.library.people.ResponsePeopleDTO;
import org.bashtan.MyApps.data.services.interfaces.PeopleServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/peoples")
@Transactional
public class PeopleAPIController {
    private final PeopleServiceInterface peopleService;

    private static final String CREATE = "/create";
    private static final String EDITE = "/edit";
    private static final String DELETE = "/delete";
    private static final String FETCH_ALL = "/fetchAll";
    private static final String SEARCH_BY_SYMBOL = "/searchBySymbol";


    @PostMapping(CREATE)
    @PermitAll
    public ResponseEntity<Void> create(@RequestBody CreatePeopleDTO createPeopleDTO) {
        peopleService.createPeople(createPeopleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PatchMapping(EDITE)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> edit(@RequestBody PeopleDTO peopleDTO) {
        peopleService.editPeople(peopleDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(DELETE)
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@RequestBody DeletePeopleRequestDTO deletePeopleRequestDTO) {
        peopleService.deletePeople(deletePeopleRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(FETCH_ALL)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ResponsePeopleDTO>> fetchAllUser() {
        List<ResponsePeopleDTO> peoples = peopleService.fetchAllPeople();
        return new ResponseEntity<>(peoples, HttpStatus.OK);
    }
    @GetMapping(SEARCH_BY_SYMBOL)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ResponsePeopleDTO>> fetchUserBySymbol(
            @RequestParam(value = "target_symbol") String targetSymbol) {
        List<ResponsePeopleDTO> peoples = peopleService.fetchPeopleBySymbol(targetSymbol);
        return new ResponseEntity<>(peoples, HttpStatus.OK);
    }

}
