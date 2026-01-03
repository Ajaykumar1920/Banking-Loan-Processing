package com.auth.service;

import java.util.List;

import com.auth.dto.AdminUserResponse;
import com.auth.dto.CreateUserRequest;

public interface AdminService {
	AdminUserResponse createUser(CreateUserRequest request);

    List<AdminUserResponse> getAllUsers();

    void disableUser(Long userId, String currentAdminUsername);
}
