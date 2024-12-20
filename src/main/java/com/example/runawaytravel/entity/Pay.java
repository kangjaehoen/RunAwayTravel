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
public class Pay {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="username")
    private User user;

    @Column(name = "paydate")
    private LocalDate payDate;

    @Column(name="pay_status")
    private Character pay_Status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="accomnum")
    private Accom accomNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="resnum")
    private Reservation resNum;

    @Id
    @Column(name = "impuid")
    private String impUid;

    @Column(name = "merchantuid")
    private String merchantUid;

    private String amount;
    private String name;
    private String apply_num;

    public void setUserUsername(String username) {
        if(this.user == null) {
            this.user = new User();
        }
        this.user.setUsername(username);
    }
}

