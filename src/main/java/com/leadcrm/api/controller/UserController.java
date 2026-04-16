package com.leadcrm.api.controller;

import com.leadcrm.application.command.CreateUserCommand;
import com.leadcrm.application.command.UpdateUserCommand;
import com.leadcrm.application.service.UserApplicationService;
import com.leadcrm.api.dto.ApiResponse;
import com.leadcrm.api.dto.LoginRequestDTO;
import com.leadcrm.api.dto.LoginResponseDTO;
import com.leadcrm.api.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {
    
    private final UserApplicationService userApplicationService;
    
    public UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }
    
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        String token = userApplicationService.login(request.getUsername(), request.getPassword());
        com.leadcrm.domain.user.aggregate.User user = userApplicationService.getUserByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        response.setUser(toDTO(user));
        
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody CreateUserCommand command) {
        com.leadcrm.domain.user.aggregate.User user = userApplicationService.createUser(command);
        return ResponseEntity.ok(ApiResponse.success(toDTO(user)));
    }
    
    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        List<com.leadcrm.domain.user.aggregate.User> users = userApplicationService.getAllUsers();
        List<UserDTO> userDTOs = users.stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(userDTOs));
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userApplicationService.getUserById(id)
                .map(user -> ResponseEntity.ok(ApiResponse.success(toDTO(user))))
                .orElse(ResponseEntity.ok(ApiResponse.error("User not found")));
    }
    
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody CreateUserCommand command) {
        com.leadcrm.domain.user.aggregate.User user = userApplicationService.createUser(command);
        return ResponseEntity.ok(ApiResponse.success(toDTO(user)));
    }
    
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserCommand command) {
        command.setId(id);
        com.leadcrm.domain.user.aggregate.User user = userApplicationService.updateUser(command);
        return ResponseEntity.ok(ApiResponse.success(toDTO(user)));
    }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userApplicationService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
    
    @GetMapping("/auth/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if (!userApplicationService.validateToken(token)) {
            return ResponseEntity.ok(ApiResponse.error("Invalid token"));
        }
        String username = userApplicationService.getUsernameFromToken(token);
        return userApplicationService.getUserByUsername(username)
                .map(user -> ResponseEntity.ok(ApiResponse.success(toDTO(user))))
                .orElse(ResponseEntity.ok(ApiResponse.error("User not found")));
    }
    
    private UserDTO toDTO(com.leadcrm.domain.user.aggregate.User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUserInfo().getUsername());
        dto.setEmail(user.getUserInfo().getEmail());
        dto.setRole(user.getRole().name());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setLastLoginAt(user.getLastLoginAt());
        return dto;
    }
}