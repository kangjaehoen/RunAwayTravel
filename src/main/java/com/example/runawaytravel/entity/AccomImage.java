package com.example.runawaytravel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter@Getter@ToString@Entity
public class AccomImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;
    private String username;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accomnum")
    private Accom accomNum;
    private String filePath;
}
