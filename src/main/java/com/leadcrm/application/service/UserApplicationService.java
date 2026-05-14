package com.leadcrm.application.service;

import com.leadcrm.application.command.CreateUserCommand;
import com.leadcrm.application.command.UpdateUserCommand;
import com.leadcrm.domain.user.aggregate.User;
import com.leadcrm.domain.user.service.UserDomainService;
import com.leadcrm.domain.user.service.AuthService;
import com.leadcrm.domain.user.vo.Role;
import com.leadcrm.domain.user.vo.UserInfo;
import com.leadcrm.infrastructure.repository.UserRepository;
import com.leadcrm.infrastructure.security.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserApplicationService {
    
    private final UserRepository userRepository;
    private final UserDomainService userDomainService;
    private final AuthService authService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    
    public UserApplicationService(UserRepository userRepository, UserDomainService userDomainService, 
                                AuthService authService, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDomainService = userDomainService;
        this.authService = authService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    public User createUser(CreateUserCommand command) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(command.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        // 加密密码
        String encryptedPassword = passwordEncoder.encode(command.getPassword());
        
        UserInfo userInfo = new UserInfo(
            command.getUsername(),
            encryptedPassword,
            command.getEmail()
        );
        
        Role role = Role.valueOf(command.getRole());
        
        User user = userDomainService.createUser(userInfo, role);
        
        return userRepository.save(user);
    }
    
    @Transactional
    public User updateUser(UpdateUserCommand command) {
        Optional<User> optionalUser = userRepository.findById(command.getId());
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }
        
        User user = optionalUser.get();
        
        if (command.getPassword() != null) {
            String encryptedPassword = passwordEncoder.encode(command.getPassword());
            UserInfo userInfo = new UserInfo(
                command.getUsername() != null ? command.getUsername() : user.getUserInfo().getUsername(),
                encryptedPassword,
                command.getEmail() != null ? command.getEmail() : user.getUserInfo().getEmail()
            );
            userDomainService.updateUser(user, userInfo);
        } else if (command.getUsername() != null || command.getEmail() != null) {
            UserInfo userInfo = new UserInfo(
                command.getUsername() != null ? command.getUsername() : user.getUserInfo().getUsername(),
                user.getUserInfo().getPassword(),
                command.getEmail() != null ? command.getEmail() : user.getUserInfo().getEmail()
            );
            userDomainService.updateUser(user, userInfo);
        }
        
        if (command.getRole() != null) {
            userDomainService.updateUserRole(user, Role.valueOf(command.getRole()));
        }
        
        return userRepository.save(user);
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public String login(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("Invalid username or password");
        }
        
        User user = optionalUser.get();
        if (!StringUtils.equals(password, user.getUserInfo().getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        
        // 记录登录时间
        user.recordLogin();
        userRepository.save(user);
        
        // 生成JWT令牌
        return jwtUtils.generateToken(user.getUserInfo().getUsername(), user.getRole().name());
    }
    
    public boolean validateToken(String token) {
        return jwtUtils.validateToken(token);
    }
    
    public String getUsernameFromToken(String token) {
        return jwtUtils.getUsernameFromToken(token);
    }
    
    public String getRoleFromToken(String token) {
        return jwtUtils.getRoleFromToken(token);
    }
}