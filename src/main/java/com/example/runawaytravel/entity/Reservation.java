package com.example.runawaytravel.entity;

import com.example.runawaytravel.repository.AccomRepository;
import groovyjarjarpicocli.CommandLine;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.time.LocalDate;
import java.util.Optional;

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
    private User username;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="accomnum")
    private Accom accom;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="review")
//    private Review review;

//    @ManyToOne
//    @JoinColumn(name = "accom")  // 숙소 정보와 연관
//    private Accom accom;

//    public void setAccom(Accom accom) {
//        this.accom = accom;
//    }

}
