package com.example.runawaytravel.repository;

import com.example.runawaytravel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User,String > {
}
