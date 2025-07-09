package org.bashtan.MyApps.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bashtan.MyApps.configs.MyUserDetails;
import org.bashtan.MyApps.data.dto.DeleteRequestDTO;
import org.bashtan.MyApps.data.dto.user.CreateUserDTO;
import org.bashtan.MyApps.data.dto.user.ResponseUserDTO;
import org.bashtan.MyApps.data.dto.user.UserDTO;
import org.bashtan.MyApps.data.entities.UserEntity;
import org.bashtan.MyApps.data.repositories.UserRepository;
import org.bashtan.MyApps.data.services.interfaces.EmailServiceInterface;
import org.bashtan.MyApps.data.services.interfaces.SearchServiceInterface;
import org.bashtan.MyApps.data.services.interfaces.UserServiceInterface;
import org.bashtan.MyApps.enums.UserAction;
import org.bashtan.MyApps.enums.UserRole;
import org.bashtan.MyApps.exceptions.BadRequestException;
import org.bashtan.MyApps.exceptions.DatabaseException;
import org.bashtan.MyApps.exceptions.NotFoundException;
import org.bashtan.MyApps.mappers.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserService implements UserServiceInterface {
    private final SearchServiceInterface searchService;
    private final EmailServiceInterface emailService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(CreateUserDTO createUserDTO) {
        validateUserDTO(createUserDTO);
        createUserDTO.setPassword(setPasswordEncoder(createUserDTO.getPassword()));
        try {
            UserDTO userDTO = UserMapper.toUserDTO(
                    userRepository.save(UserMapper.toUserEntity(createUserDTO)));
            log.info("Save user!");
            emailService.sendEmail(userDTO);
            log.info("Successful Sign UP");

        } catch (Exception e) {
            log.error("Error while create user", e);
            throw new DatabaseException("Error create user", e);
        }

    }

    @Override
    public void editUser(UserDTO userDTO, UserAction action) {
        checkTestUser();
        UserDTO user = getUserByID(userDTO.getId());
        if (user.isSuperUser() && (
                action.equals(UserAction.EDIT_EMAIL) ||
                        action.equals(UserAction.EDIT_STATUS))) {
            throw new BadRequestException(
                    "Superuser cannot be edited",
                    "superUser"
            );
        }
        switch (action) {
            case EDIT_EMAIL -> {
                if (userDTO.getEmail() != null && !userDTO.getEmail().isBlank()) {
                    String newEmail = userDTO.getEmail().trim();
                    String currentEmail = user.getEmail();
                    if (!newEmail.equalsIgnoreCase(currentEmail)) {
                        userRepository.findByEmail(userDTO.getEmail()).ifPresent(existingUser -> {
                            if (!existingUser.getId().equals(user.getId())) {
                                throw new BadRequestException(
                                        String.format("Email %s already exists", userDTO.getEmail()),
                                        "email");
                            }
                        });
                        user.setEmail(userDTO.getEmail());
                        user.setEmailVerified(false);
                        emailService.resendEmail(userDTO);
                    }
                }
            }
            case EDIT_ROLES -> {
                Set<UserRole> rolesToSet = new HashSet<>(
                        (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty())
                                ? userDTO.getRoles()
                                : Collections.singleton(UserRole.GUEST)
                );

                if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
                    rolesToSet.remove(UserRole.GUEST);
                }

                if (user.isSuperUser()) {
                    rolesToSet.add(UserRole.ADMIN);
                    rolesToSet.remove(UserRole.GUEST);
                }
                user.setRoles(rolesToSet);
            }
            case EDIT_STATUS -> {
                user.setEnabled(!user.isEnabled());
            }
            case EDIT_PASSWORD -> {
                if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                    user.setPassword(setPasswordEncoder(userDTO.getPassword()));
                }
            }
            case EDIT_USER -> {
                if (userDTO.getFirstName() == null || userDTO.getFirstName().isEmpty()) {
                    throw new BadRequestException("The First Name cannot be empty");
                }
                if (userDTO.getLastName() == null || userDTO.getLastName().isEmpty()) {
                    throw new BadRequestException("The Last Name cannot be empty");
                }
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
            }
        }

        try {
            userRepository.save(UserMapper.toUserEntity(user));
        } catch (Exception e) {
            log.error("Error while edit user", e);
            throw new DatabaseException("Error edit user", e);
        }
    }

    @Override
    public void deleteUser(DeleteRequestDTO deleteRequestDTO) {
        checkTestUser();
        UserDTO user = getUserByID(deleteRequestDTO.getId());
        if (user.isSuperUser()) {
            throw new BadRequestException("Superuser cannot be delete");
        }
        if (!deleteRequestDTO.isCheckDelete()) {
            log.warn("Attempt to delete user without confirmation check");
            throw new BadRequestException("Confirmation check not installed: `checkDelete` must be true");
        }
        if (!user.isEmailVerified()) {
            emailService.deleteToken(user);
            log.info("Delete token is email:  " + user.getEmail());
        }
        try {
            userRepository.delete(UserMapper.toUserEntity(user));
        } catch (Exception e) {
            log.error("Error while deleting user", e);
            throw new DatabaseException("Error deleting user from database", e);
        }

    }

    @Override
    public List<UserRole> fetchAllUserRole() {

        return Arrays.stream(
                        UserRole.values())
                .filter(role -> role != UserRole.GUEST)
                .filter(role -> role != UserRole.LIBRARY_ADMIN)
                .filter(role -> role != UserRole.LIBRARY_USER)
                .filter(role -> role != UserRole.VIDEO_USER)
                .toList();
    }

    @Override
    public List<ResponseUserDTO> fetchAllUser() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseUserDTO> fetchUserBySymbol(String targetSymbol) {
        return searchService.searchUserBySymbol(targetSymbol);
    }

    @Override
    public void createDefaultUser() {
        if (userRepository.findBySuperUser(true).isEmpty()) {
            Set<UserRole> roles = EnumSet.allOf(UserRole.class);
            roles.remove(UserRole.GUEST);
            userRepository.save(UserEntity
                    .builder()
                    .login("Admin")
                    .password(setPasswordEncoder("Password"))
                    .email("admin@admin.us")
                    .firstName("Admin")
                    .lastName("Admin")
                    .roles(roles)
                    .emailVerified(true)
                    .enabled(true)
                    .superUser(true)
                    .build()
            );
        }
    }

    private String setPasswordEncoder(String password) {
        return passwordEncoder.encode(password);
    }

    private UserDTO getUserByID(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toUserDTO)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
    }

    private void validateUserDTO(CreateUserDTO userDTO) {
        if (userDTO.getLogin() == null || userDTO.getLogin().trim().isEmpty()) {
            log.info("User login cannot be null or empty.");
            throw new BadRequestException(
                    "User login cannot be null or empty.",
                    "login");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty()) {
            log.info("User password cannot be null or empty.");
            throw new BadRequestException(
                    "User password cannot be null or empty.",
                    "password");
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()) {
            log.info("User email cannot be null or empty.");
            throw new BadRequestException(
                    "User email cannot be null or empty.",
                    "email");
        }

        if (userRepository.findByLogin(userDTO.getLogin()).isPresent()) {
            log.info(String.format("Login '%s' already exists", userDTO.getLogin()));
            throw new BadRequestException(
                    String.format("Login '%s' already exists", userDTO.getLogin()),
                    "login");
        }
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            log.info(String.format("Email '%s' already exists", userDTO.getEmail()));
            throw new BadRequestException(
                    String.format("Email '%s' already exists", userDTO.getEmail()),
                    "EMAIL"
            );
        }
    }

    @Override
    public void checkTestUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            userRepository.
                    findById(((MyUserDetails) authentication
                            .getPrincipal())
                            .getId())
                    .ifPresent(user -> {
                        if (user.isTestUser()) {
                            throw new BadRequestException(
                                    "The test user cannot make any changes",
                                    "testUser");
                        }
                    });
        }
    }
}