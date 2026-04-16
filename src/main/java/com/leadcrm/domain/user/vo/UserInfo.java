package com.leadcrm.domain.user.vo;

import lombok.Value;

@Value
public class UserInfo {
    private String username;
    private String password;
    private String email;
    
    public UserInfo(String username, String password, String email) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        this.username = username;
        this.password = password;
        this.email = email;
    }
}