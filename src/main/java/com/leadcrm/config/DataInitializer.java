package com.leadcrm.config;

import com.leadcrm.entity.User;
import com.leadcrm.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) {
        // 初始化管理员用户
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();

            admin.setUsername("admin");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setCreatedAt(LocalDateTime.now());
            admin.setUpdatedAt(LocalDateTime.now());
            userRepository.save(admin);
            System.out.println("已创建默认管理员用户: admin / admin123");
        } else {
            // 更新密码确保正确
            User admin = userRepository.findByUsername("admin").orElse(null);
            if (admin != null && !passwordEncoder.matches("admin123", admin.getPasswordHash())) {
                admin.setPasswordHash(passwordEncoder.encode("admin123"));
                admin.setUpdatedAt(LocalDateTime.now());
                userRepository.save(admin);
                System.out.println("已更新管理员密码: admin / admin123");
            }
        }
    }
}