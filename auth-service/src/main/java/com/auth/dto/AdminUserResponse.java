package com.auth.dto;

import com.auth.model.Role;
import com.auth.model.UserStatus;

public class AdminUserResponse {
	private Long id;
    private String username;
    private String email;
    private Role role;
    private UserStatus status;

    public AdminUserResponse(Long id, String username, String email,
                             Role role, UserStatus status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.status = status;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}
    
    
}
