package com.auth.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.auth.model.Role;
import com.auth.model.User;
import com.auth.model.UserStatus;
import com.auth.repository.UserRepository;

@Component
public class AdminBootstrap implements CommandLineRunner{
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminBootstrap(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        boolean adminExists = userRepository
                .findAll()
                .stream()
                .anyMatch(user -> user.getRole() == Role.ADMIN);

        if (adminExists) {
            return;
        }

        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@bank.com");
        admin.setPassword(passwordEncoder.encode("admin@123"));
        admin.setRole(Role.ADMIN);
        admin.setStatus(UserStatus.ACTIVE);

        userRepository.save(admin);

        System.out.println("Initial ADMIN created");
    }
}
