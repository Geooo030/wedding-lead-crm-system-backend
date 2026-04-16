package com.leadcrm.infrastructure.persistence.repository;

import com.leadcrm.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    
    Optional<UserJpaEntity> findByUsername(String username);
    
    List<UserJpaEntity> findByRole(String role);
    
    boolean existsByUsername(String username);
}