package com.leadcrm.domain.user.service.impl;

import com.leadcrm.domain.user.aggregate.User;
import com.leadcrm.domain.user.service.UserDomainService;
import com.leadcrm.domain.user.vo.UserInfo;
import com.leadcrm.domain.user.vo.Role;
import org.springframework.stereotype.Service;

@Service
public class UserDomainServiceImpl implements UserDomainService {
    
    @Override
    public User createUser(UserInfo userInfo, Role role) {
        User user = new User();
        user.setUserInfo(userInfo);
        user.setRole(role);
        return user;
    }
    
    @Override
    public void updateUser(User user, UserInfo userInfo) {
        user.updateUserInfo(userInfo);
    }
    
    @Override
    public void updateUserRole(User user, Role role) {
        user.updateRole(role);
    }
    
    @Override
    public void deleteUser(User user) {
        // 实际删除逻辑会在仓储层实现
    }
    
    @Override
    public User getUserByUsername(String username) {
        // 实际查询逻辑会在仓储层实现
        return null;
    }
}