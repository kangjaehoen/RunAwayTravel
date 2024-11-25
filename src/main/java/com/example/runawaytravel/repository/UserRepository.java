package com.example.runawaytravel.repository;

import com.example.runawaytravel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByUsername(String username);
    User findByUsername(String username);
    User findByNameAndBirthAndEmail(String name, String birth, String email);//조회 메서드
    User findByUsernameAndNameAndBirthAndEmail(String username, String name, String birth, String email);
}