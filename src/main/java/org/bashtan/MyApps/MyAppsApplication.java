package org.bashtan.MyApps;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "org.bashtan.MyApps.data.entities")
@EnableJpaRepositories(basePackages = "org.bashtan.MyApps.data.repositories")
public class MyAppsApplication {


    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
        SpringApplication.run(MyAppsApplication.class, args);
    }

//    @PostConstruct
//    public void testEnv() {
//        UserEntity userEntity = userRepository.findById(1553L).orElseThrow();
//        UserDTO userDTO = UserMapper.toUserDTO(userEntity);
//        emailService.sendEmail(userDTO);
//    }

}
