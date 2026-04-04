package com.leadcrm.controller;

import com.leadcrm.dto.ApiResponse;
import com.leadcrm.dto.LoginRequest;
import com.leadcrm.entity.User;
import com.leadcrm.repository.UserRepository;
import com.leadcrm.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElse(null);
        
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.error("用户名或密码错误"));
        }
        
        String token = jwtUtils.generateToken(user.getUsername());
        
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("username", user.getUsername());
        data.put("userId", user.getId());
        
        return ResponseEntity.ok(ApiResponse.success(data));
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        // 从 SecurityContext 获取当前用户
        String username = (String) org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        
        User user = userRepository.findByUsername(username)
                .orElse(null);
        
        if (user == null) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.error("用户未登录"));
        }
        
        Map<String, Object> data = new HashMap<>();
        data.put("username", user.getUsername());
        data.put("userId", user.getId());
        
        return ResponseEntity.ok(ApiResponse.success(data));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(ApiResponse.success("登出成功"));
    }
}