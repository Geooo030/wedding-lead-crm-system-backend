package com.leadcrm.domain.user.aggregate;

import com.leadcrm.domain.user.vo.UserInfo;
import com.leadcrm.domain.user.vo.Role;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private UserInfo userInfo;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
    
    public User() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void updateUserInfo(UserInfo newUserInfo) {
        this.userInfo = newUserInfo;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void updateRole(Role newRole) {
        this.role = newRole;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void recordLogin() {
        this.lastLoginAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}