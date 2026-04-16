package com.leadcrm.domain.user.service;

import com.leadcrm.domain.user.aggregate.User;
import com.leadcrm.domain.user.vo.UserInfo;
import com.leadcrm.domain.user.vo.Role;

public interface UserDomainService {
    User createUser(UserInfo userInfo, Role role);
    
    void updateUser(User user, UserInfo userInfo);
    
    void updateUserRole(User user, Role role);
    
    void deleteUser(User user);
    
    User getUserByUsername(String username);
}