package dev.anhpd.config;

import dev.anhpd.entity.model.User;
import dev.anhpd.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    //tao ra admin co san
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add("ADMIN");
                User user = new User();
                user.setFullname("Phan Duc Anh");
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("admin"));
                user.setRole(roles);
                userRepository.save(user);
                log.warn("admin user has been created with default password : admin, please change password");
            }
        };
    }
}
