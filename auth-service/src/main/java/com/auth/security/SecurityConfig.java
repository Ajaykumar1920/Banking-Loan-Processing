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
//	private final CustomUserDetailsService userDetailsService;
//	
//	public SecurityConfig(CustomUserDetailsService userDetailsService) {
//		this.userDetailsService = userDetailsService;
//	}
//	private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	private final GatewayAuthenticationFilter gatewayAuthenticationFilter;
	public SecurityConfig(GatewayAuthenticationFilter gatewayAuthenticationFilter) {
	    this.gatewayAuthenticationFilter = gatewayAuthenticationFilter;
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	// Core security rules
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
//            .exceptionHandling(ex -> 
//            ex.authenticationEntryPoint(restAuthenticationEntryPoint)
//            )
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
    // AuthenticationManager for login
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    // Authentication provider (DB + BCrypt)
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(
//            PasswordEncoder passwordEncoder) {
//    	
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder);
//        return provider;
//    }
}
