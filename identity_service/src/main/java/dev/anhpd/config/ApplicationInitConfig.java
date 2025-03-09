package dev.anhpd.config;

import dev.anhpd.entity.model.Role;
import dev.anhpd.entity.model.User;
import dev.anhpd.repository.RoleRepository;
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
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User user = new User();
                user.setFullname("Phan Duc Anh");
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("admin"));
                //set role admin cho user
                user.setRoles(new HashSet<>(roleRepository.findById("ADMIN").map(role -> {
                    var roles = new HashSet<Role>();
                    roles.add(role);
                    return roles;
                }).orElseThrow()));
                userRepository.save(user);
                log.warn("admin user has been created with default password : admin, please change password");
            }
        };
    }
}
