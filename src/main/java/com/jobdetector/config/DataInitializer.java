package com.jobdetector.config;

import com.jobdetector.entity.User;
import com.jobdetector.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        String defaultEmail = "admin@example.com";
        if (!userRepository.existsByEmail(defaultEmail)) {
            User defaultUser = new User();
            defaultUser.setName("Admin User");
            defaultUser.setEmail(defaultEmail);
            defaultUser.setPassword("password");
            userRepository.save(defaultUser);
        }
    }
}
