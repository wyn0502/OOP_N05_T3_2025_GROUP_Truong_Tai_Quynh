package com.example.zoo.repository;

import com.example.zoo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);
    
    User findByUsername(String username);

    boolean existsByPhone(String phone);
    
    boolean existsByUsername(String username);
}