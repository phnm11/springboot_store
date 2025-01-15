package com.nam.storespring.configuration;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nam.storespring.entity.User;
import com.nam.storespring.enums.Role;
import com.nam.storespring.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUserName("admin").isEmpty()) {
                User user = User.builder()
                        .userName("admin")
                        .password(passwordEncoder.encode("admin@123"))
                        .role(Role.ADMIN.name())
                        .build();

                userRepository.save(user);
                log.warn("Admin user has been created with default password: admin@123, please change the pass!");
            }
        };
    }
}
