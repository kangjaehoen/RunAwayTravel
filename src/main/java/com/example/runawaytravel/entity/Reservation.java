package com.example.runawaytravel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resnum")
    private int resNum;

    @Column(name = "resdate")
    private LocalDate resDate;

    private LocalDate chkin_Date;
    private LocalDate chkout_Date;

    @Column(name = "adultcnt")
    private int adultCnt;

    @Column(name = "kidcnt")
    private int kidCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="username")
    private User user;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="accomnum")
    private Accom accom;
}
