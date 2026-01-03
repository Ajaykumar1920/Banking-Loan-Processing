package com.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.auth.dto.LoginRequest;
import com.auth.dto.LoginResponse;
import com.auth.dto.RegisterRequest;
import com.auth.exception.UserAlreadyExistsException;
import com.auth.model.Role;
import com.auth.model.User;
import com.auth.model.UserStatus;
import com.auth.repository.UserRepository;
import com.auth.util.JwtUtil;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public AuthService(UserRepository userRepository, 
			PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	// Registers a new CUSTOMER user. Password is securely encoded using BCrypt
	public void register(RegisterRequest request) {
		// Validate uniqueness
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new UserAlreadyExistsException("Username already exists");
		}
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new UserAlreadyExistsException("Email already exists");
		}
		// Encode password
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		// Create User entity
		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(encodedPassword); // ENCODED password
		user.setRole(Role.CUSTOMER); // default role
		user.setStatus(UserStatus.ACTIVE);
		userRepository.save(user); // Persist user
	}

	public LoginResponse login(LoginRequest request, 
			AuthenticationManager authenticationManager) {
		// 1️. Authenticate credentials (Spring Security handles everything)
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(), 
						request.getPassword()));
		// 2️. Fetch user (safe now)
		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new RuntimeException("User not found"));
		// 3️. Generate JWT
		String token = jwtUtil.generateToken(
				user.getUsername(), 
				user.getRole().name());
		// 4️. Return response
		return new LoginResponse(token, user.getRole().name());
	}

}
