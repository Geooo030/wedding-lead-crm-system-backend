package com.leadcrm.domain.user.service;

import com.leadcrm.domain.user.aggregate.User;

public interface AuthService {
    User authenticate(String username, String password);
    
    String generateToken(User user);
    
    boolean validateToken(String token);
    
    String getUsernameFromToken(String token);
    
    boolean checkPermission(User user, String resource, String action);
}