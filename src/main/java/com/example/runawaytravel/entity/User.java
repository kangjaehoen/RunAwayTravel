package com.example.runawaytravel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
public class User {
    @Id
    private String username;
    private String name;
    private String email;
    private String pw;
    private String gender;
    private String birth;
    private String accountNum;
    private String role;
}
