package com.example.runawaytravel.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

public class AbleDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;
    @ManyToOne
    @JoinColumn(name="accomnum")
    private int accomNum;
    private LocalDate date;
    @Column(name = "ablestatus")
    private String ableStatus;
    @ManyToOne
    @JoinColumn
    private User username;
}
