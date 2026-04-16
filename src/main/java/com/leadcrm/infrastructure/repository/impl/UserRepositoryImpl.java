package com.leadcrm.infrastructure.repository.impl;

import com.leadcrm.domain.user.aggregate.User;
import com.leadcrm.domain.user.vo.UserInfo;
import com.leadcrm.domain.user.vo.Role;
import com.leadcrm.infrastructure.persistence.entity.UserJpaEntity;
import com.leadcrm.infrastructure.persistence.repository.UserJpaRepository;
import com.leadcrm.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {
    
    private final UserJpaRepository userJpaRepository;
    
    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }
    
    @Override
    public User save(User user) {
        UserJpaEntity jpaEntity = toJpaEntity(user);
        UserJpaEntity saved = userJpaRepository.save(jpaEntity);
        return toDomainModel(saved);
    }
    
    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(this::toDomainModel);
    }
    
    @Override
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }
    
    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll().stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(this::toDomainModel);
    }
    
    @Override
    public List<User> findByRole(Role role) {
        return userJpaRepository.findByRole(role.name()).stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }
    
    private UserJpaEntity toJpaEntity(User user) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUserInfo().getUsername());
        entity.setPassword(user.getUserInfo().getPassword());
        entity.setEmail(user.getUserInfo().getEmail());
        entity.setRole(user.getRole().name());
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());
        entity.setLastLoginAt(user.getLastLoginAt());
        return entity;
    }
    
    private User toDomainModel(UserJpaEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        
        UserInfo userInfo = new UserInfo(
            entity.getUsername(),
            entity.getPassword(),
            entity.getEmail()
        );
        user.setUserInfo(userInfo);
        
        user.setRole(Role.valueOf(entity.getRole()));
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(entity.getUpdatedAt());
        user.setLastLoginAt(entity.getLastLoginAt());
        
        return user;
    }
}