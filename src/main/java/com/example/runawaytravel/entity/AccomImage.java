package com.example.runawaytravel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name="accomimage")
public class AccomImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imagenum;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accomnum")
    private Accom accomNum;
    @Column(name="filepath", length=5000)
    private String filePath;
}
