package com.auth.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.auth.dto.AdminUserResponse;
import com.auth.dto.CreateUserRequest;
import com.auth.service.AdminService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

	private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Create Loan Officer or Manager
    @PostMapping("/users")
    public ResponseEntity<AdminUserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        AdminUserResponse response = adminService.createUser(request);
        return ResponseEntity.ok(response);
    }

    // Fetch all users
    @GetMapping("/users")
    public ResponseEntity<List<AdminUserResponse>> getAllUsers() {

        return ResponseEntity.ok(adminService.getAllUsers());
    }

    // Disable a user
    @PutMapping("/users/{id}/disable")
    public ResponseEntity<String> disableUser(
            @PathVariable Long id,
            Principal principal) {

        adminService.disableUser(id, principal.getName());
        return ResponseEntity.ok("User disabled successfully");
    }
}
