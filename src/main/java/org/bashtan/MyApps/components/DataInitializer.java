package org.bashtan.MyApps.components;

import lombok.RequiredArgsConstructor;
import org.bashtan.MyApps.data.services.interfaces.UserServiceInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {
    private final UserServiceInterface userService;

    @Override
    public void run(String... args) throws Exception {
        userService.createDefaultUser();
    }
}
