package org.bashtan.MyApps.mappers;

import org.bashtan.MyApps.data.dto.user.CreateUserDTO;
import org.bashtan.MyApps.data.dto.user.ResponseUserDTO;
import org.bashtan.MyApps.data.dto.user.UserDTO;
import org.bashtan.MyApps.data.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static ResponseUserDTO toResponseUserDTO(UserEntity userEntity) {
        return ResponseUserDTO.builder()
                .id(userEntity.getId())
                .login(userEntity.getLogin())
                .email(userEntity.getEmail().toLowerCase())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .roles(userEntity.getRoles())
                .enabled(userEntity.isEnabled())
                .superUser(userEntity.isSuperUser())
                .testUser(userEntity.isTestUser())
                .emailVerified(userEntity.isEmailVerified())
                .build();
    }

    public static UserDTO toUserDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .login(userEntity.getLogin())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail().toLowerCase())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .roles(userEntity.getRoles())
                .enabled(userEntity.isEnabled())
                .superUser(userEntity.isSuperUser())
                .testUser(userEntity.isTestUser())
                .emailVerified(userEntity.isEmailVerified())
                .build();
    }

    public static UserEntity toUserEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .id(userDTO.getId())
                .login(userDTO.getLogin())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail().toLowerCase())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .roles(userDTO.getRoles())
                .enabled(userDTO.isEnabled())
                .testUser(userDTO.isTestUser())
                .superUser(userDTO.isSuperUser())
                .emailVerified(userDTO.isEmailVerified())
                .build();
    }

    public static UserEntity toUserEntity(CreateUserDTO createUserDTO) {
        return UserEntity.builder()
                .login(createUserDTO.getLogin())
                .password(createUserDTO.getPassword())
                .email(createUserDTO.getEmail().toLowerCase())
                .firstName(createUserDTO.getFirstName())
                .lastName(createUserDTO.getLastName())
                .enabled(true)
                .build();
    }
}
