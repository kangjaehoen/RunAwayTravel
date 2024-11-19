package com.example.runawaytravel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
public class Accom {
    @Column(name="accname")
    private String accName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="accomnum")
    private int accomNum;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="username")
    private User user;
    private String postcode;
    private String address;
    @Column(name = "detailaddress")
    private String detailAddress;
    @Column(name="acccall")
    private String accCall;
    private int price;
    @Column(name="adultprice")
    private int adultPrice;
    @Column(name="kidprice")
    private int kidPrice;
    private int occ;
    private int maxocc;
    private String dayoff;
    private String category;
    @Column(name="acctype")
    private String accType;
    @Column(name="onsale")
    private int onSale;
    @Column(name="accomrule", length = 5000)
    private String accomRule;
    @Column(length = 5000)
    private String informtext;
    @Column(name="chkin_time")
    private LocalTime chkin_Time;
    @Column(name="chkout_time")
    private LocalTime chkout_Time;
    private int room;
    private int bed;
    private int bathroom;
    @Column(name="regdate")
    private LocalDate regDate;

    public void setUserUsername(String username){
        if (this.user == null) {
            this.user = new User(); // User 객체 초기화
        }
        this.user.setUsername(username);
    }
}
