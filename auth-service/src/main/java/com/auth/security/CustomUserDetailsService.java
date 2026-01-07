package com.auth.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.model.User;
import com.auth.model.UserStatus;
import com.auth.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // This method is called automatically by Spring Security during authentication.
    // if this fails --> RestAuthenticationEntryPoint
    	// Username not found
    	// Password incorrect
    	// User disabled
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // 1. Fetch user from DB
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        // 2️. Check if user is active, prevent blocked users from logging in
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new UsernameNotFoundException("User is disabled");
        }

        // 3️. Convert to Spring Security UserDetails
        	// converts your domain user into Spring Security’s user.
        	// At this point Spring:
        		// Compares password using PasswordEncoder
        		// Verifies credentials
        		// Authentication SUCCESS or FAILS here
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) // already encoded
                .authorities("ROLE_" + user.getRole().name())
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
