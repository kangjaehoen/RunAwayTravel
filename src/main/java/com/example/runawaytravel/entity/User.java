package com.example.runawaytravel.entity;

import jakarta.persistence.Column;
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
    @Column(length = 30)
    private String username;
    private String name;
    private String email;
    private String pw;
    private String gender;
    private String birth;
    private String accountnum;
}
