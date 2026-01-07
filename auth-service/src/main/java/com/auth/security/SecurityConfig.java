package com.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final GatewayAuthenticationFilter gatewayAuthenticationFilter;
	public SecurityConfig(GatewayAuthenticationFilter gatewayAuthenticationFilter) {
	    this.gatewayAuthenticationFilter = gatewayAuthenticationFilter;
	}

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	// Core security rules:
	/*
	defines how every HTTP request to auth-service is secured,
	who is allowed in, and how identity is attached to the request
	*/
    @Bean
    public SecurityFilterChain securityFilterChain(
    		HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.addFilterBefore(
                    gatewayAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
                )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login", "/auth/register").permitAll()
                .requestMatchers("/auth/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            );

        return http.build();
    }
    /*
     AuthenticationManager for login
    	provided by Spring Security
    	Orchestrates authentication
    	Calls UserDetailsService automatically
    	This is where CustomUserDetailsService comes into play.
    */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
