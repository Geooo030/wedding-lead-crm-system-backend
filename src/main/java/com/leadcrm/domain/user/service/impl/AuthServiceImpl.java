package com.leadcrm.domain.user.service.impl;

import com.leadcrm.domain.user.aggregate.User;
import com.leadcrm.domain.user.service.AuthService;
import com.leadcrm.domain.user.vo.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expirationTime = 86400000; // 24 hours
    
    @Override
    public User authenticate(String username, String password) {
        // 实际认证逻辑会在仓储层实现
        return null;
    }
    
    @Override
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUserInfo().getUsername());
        claims.put("role", user.getRole().name());
        
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUserInfo().getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }
    
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    
    @Override
    public boolean checkPermission(User user, String resource, String action) {
        Role role = user.getRole();
        
        if (role == Role.ADMIN) {
            return true;
        }
        
        if (role == Role.BOSS) {
            // Boss可以访问大部分资源，除了系统配置
            return !"system_config".equals(resource);
        }
        
        if (role == Role.AGENT) {
            // Agent只能访问分配给自己的资源
            return "lead".equals(resource) || "task".equals(resource);
        }
        
        return false;
    }
}