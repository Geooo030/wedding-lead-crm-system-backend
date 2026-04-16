package com.leadcrm.infrastructure.repository;

import com.leadcrm.domain.user.aggregate.User;
import com.leadcrm.domain.user.vo.Role;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    void deleteById(Long id);
    List<User> findAll();
    Optional<User> findByUsername(String username);
    List<User> findByRole(Role role);
    boolean existsByUsername(String username);
}