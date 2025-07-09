package org.bashtan.MyApps.configs;

import lombok.AllArgsConstructor;
import org.bashtan.MyApps.data.entities.UserEntity;
import org.bashtan.MyApps.data.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity findUser = userRepository
                .findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return MyUserDetails.builder()
                .id(findUser.getId())
                .username(findUser.getLogin())
                .password(findUser.getPassword())
                .firstName(findUser.getFirstName())
                .lastName(findUser.getLastName())
                .email(findUser.getEmail())
                .emailVerified(findUser.isEmailVerified())
                .authorities(getAuthorities(findUser))
                .enabled(findUser.isEnabled())
                .superUser(findUser.isSuperUser())
                .build();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserEntity userEntity) {
        return userEntity
                .getRoles()
                .stream()
                .map(userRole ->
                        new SimpleGrantedAuthority("ROLE_" + userRole.name()))
                .collect(Collectors.toList());
    }
}