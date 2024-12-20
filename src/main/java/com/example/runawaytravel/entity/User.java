package com.example.runawaytravel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@NoArgsConstructor
public class User {
    @Id
    @Column(length = 30)
    private String username;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String birth;
    private String accountnum;
    private String role;

    public User(String username){
        this.username = username;
    }
}
