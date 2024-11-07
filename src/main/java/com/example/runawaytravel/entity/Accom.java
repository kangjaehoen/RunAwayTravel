package com.example.runawaytravel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@Entity
public class Accom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accomnum")
    private int accomNum;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="username" , columnDefinition = "VARCHAR(30)")
    private User username;
    private String postcode;
    private String address;
    @Column(name = "detailaddress")
    private String detailAddress;
    @Column(name = "acccall")
    private String accCall;
    private int price;
    @Column(name = "adultprice")
    private int adultPrice;
    @Column(name = "kidprice")
    private int kidPrice;
    private int occ;
    private int maxocc;
    private String dayoff;
    private String category;
    @Column(name = "acctype")
    private String accType;
    @Column(name = "onsale")
    private int onSale;
    @Column(name = "accomrule")
    private String accomRule;
    @Column(length = 5000)
    private String informtext;
    private LocalTime chkin_Time;
    private LocalTime chkout_Time;
    private int room;
    private int bed;
    private int bathroom;
    @Column(name = "regdate")
    private LocalDate regDate;
    @Column(name = "accname")
    private String accName;
}
