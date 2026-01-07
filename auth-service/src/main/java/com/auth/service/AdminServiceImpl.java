package com.auth.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.client.NotificationFeignClient;
import com.auth.dto.AdminUserResponse;
import com.auth.dto.CreateUserRequest;
import com.auth.dto.NotificationRequest;
import com.auth.exception.UserAlreadyExistsException;
import com.auth.model.NotificationEvent;
import com.auth.model.Role;
import com.auth.model.User;
import com.auth.model.UserStatus;
import com.auth.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService{

	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationFeignClient notificationFeignClient;
    private static final Logger log =
			Logger.getLogger(AuthService.class.getName());

    public AdminServiceImpl(UserRepository userRepository,
                            PasswordEncoder passwordEncoder,
                            NotificationFeignClient notificationFeignClient) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.notificationFeignClient = notificationFeignClient;
    }

    @Override
    public AdminUserResponse createUser(CreateUserRequest request) {
        // Prevent illegal roles
        if (request.getRole() == Role.ADMIN ||
            request.getRole() == Role.CUSTOMER) {
            throw new IllegalArgumentException(
                "Admin can create only OFFICER or MANAGER");
        }
        // Duplicate checks
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        // Create user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setStatus(UserStatus.ACTIVE);

        User saved = userRepository.save(user);

        return new AdminUserResponse(
            saved.getId(),
            saved.getUsername(),
            saved.getEmail(),
            saved.getRole(),
            saved.getStatus()
        );
    }

    @Override
    public List<AdminUserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new AdminUserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole(),
                        user.getStatus()))
                .toList();
    }

    @Override
    public void disableUser(Long userId, String currentAdminUsername) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
        // Prevent admin disabling self
        if (user.getUsername().equals(currentAdminUsername)) {
            throw new IllegalArgumentException(
                "Admin cannot disable self");
        }
        user.setStatus(UserStatus.DISABLED);
        userRepository.save(user);

        try {
		    NotificationRequest notificationRequest = new NotificationRequest();
		    notificationRequest.setEvent(NotificationEvent.ACCOUNT_DISABLED);
		    notificationRequest.setRecipient(user.getEmail());
		    notificationRequest.setUsername(user.getUsername());

		    notificationFeignClient.sendNotification(notificationRequest);

		} catch (Exception e) {
		    log.warning("Account disable function failed: " + e.getMessage());
		}
    }

    @Override
    public void enableUser(Long userId, String currentAdminUsername) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
        // Prevent admin enable self
//        if (user.getUsername().equals(currentAdminUsername)) {
//            throw new IllegalArgumentException(
//                "Admin cannot disable self");
//        }
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        try {
		    NotificationRequest notificationRequest = new NotificationRequest();
		    notificationRequest.setEvent(NotificationEvent.ACCOUNT_ENABLED);
		    notificationRequest.setRecipient(user.getEmail());
		    notificationRequest.setUsername(user.getUsername());

		    notificationFeignClient.sendNotification(notificationRequest);

		} catch (Exception e) {
		    log.warning("Account enable function failed: " + e.getMessage());
		}
    }

}
