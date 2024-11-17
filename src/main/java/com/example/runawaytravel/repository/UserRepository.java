package com.example.runawaytravel.repository;

import com.example.runawaytravel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByUsername(String username);
    User findByUsername(String username); //조회 메서드
}