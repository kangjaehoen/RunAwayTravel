package com.example.runawaytravel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rnum;

    private int satisfy;
    private int accuracy;
    private int clean;
    private int scp; // 가격대비 만족도

    @Column(name = "revcontent")
    private String revContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accomnum")
    private Accom accom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;
}
