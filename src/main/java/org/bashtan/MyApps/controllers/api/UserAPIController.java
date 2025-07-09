package org.bashtan.MyApps.controllers.api;

import lombok.RequiredArgsConstructor;
import org.bashtan.MyApps.data.dto.DeleteRequestDTO;
import org.bashtan.MyApps.data.dto.user.ResponseUserDTO;
import org.bashtan.MyApps.data.dto.user.UserDTO;
import org.bashtan.MyApps.data.services.interfaces.UserServiceInterface;
import org.bashtan.MyApps.enums.UserAction;
import org.bashtan.MyApps.enums.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
@Transactional
public class UserAPIController {

    private final UserServiceInterface userService;

    private static final String EDITE_ROLE_USER = "/editRoleUser";
    private static final String EDITE_ROLE_ADMIN = "/editRoleAdmin";
    private static final String DELETE = "/delete";
    private static final String FETCH_ALL_ROLE = "/fetchAllRoles";
    private static final String FETCH_ALL = "/fetchAll";
    private static final String SEARCH_BY_SYMBOL = "/searchBySymbol";

    @PatchMapping(EDITE_ROLE_USER)
    @PreAuthorize("authentication.principal.id == #userDTO.id")
    public ResponseEntity<Void> editUserPasswordRoleUser(@RequestBody UserDTO userDTO, @RequestParam UserAction userAction) {
        switch (userAction) {
            case EDIT_PASSWORD -> userService.editUser(userDTO, UserAction.EDIT_PASSWORD);
            case EDIT_EMAIL -> userService.editUser(userDTO, UserAction.EDIT_EMAIL);
        }
        userService.editUser(userDTO, UserAction.EDIT_PASSWORD);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(EDITE_ROLE_ADMIN)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> editUserAdmin(@RequestBody UserDTO userDTO, @RequestParam UserAction userAction) {
        userService.editUser(userDTO, userAction);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@RequestBody DeleteRequestDTO deleteUserRequest) {
        userService.deleteUser(deleteUserRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(FETCH_ALL_ROLE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserRole>> fetchAllUserRoles() {
        List<UserRole> roles = userService.fetchAllUserRole();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping(FETCH_ALL)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ResponseUserDTO>> fetchAllUser() {
        List<ResponseUserDTO> users = userService.fetchAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(SEARCH_BY_SYMBOL)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ResponseUserDTO>> fetchUserBySymbol(
            @RequestParam(value = "target_symbol") String targetSymbol) {
        List<ResponseUserDTO> users = userService.fetchUserBySymbol(targetSymbol);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}