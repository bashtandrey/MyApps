package org.bashtan.MyApps.data.services.interfaces;

import org.bashtan.MyApps.data.dto.DeleteRequestDTO;
import org.bashtan.MyApps.data.dto.user.CreateUserDTO;
import org.bashtan.MyApps.data.dto.user.ResponseUserDTO;
import org.bashtan.MyApps.data.dto.user.UserDTO;
import org.bashtan.MyApps.enums.UserAction;
import org.bashtan.MyApps.enums.UserRole;

import java.util.List;

public interface UserServiceInterface {
    void createUser(CreateUserDTO userDTO);

    void editUser(UserDTO userDTO, UserAction action);

    void deleteUser(DeleteRequestDTO deleteRequestDTO);

    List<UserRole> fetchAllUserRole();

    List<ResponseUserDTO> fetchAllUser();

    List<ResponseUserDTO> fetchUserBySymbol(String targetSymbol);

    void createDefaultUser();

    void checkTestUser();
}