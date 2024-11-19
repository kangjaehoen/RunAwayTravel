package com.example.runawaytravel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter@Setter@ToString@Entity@Table(name="dayoff")
public class Dayoff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;
    @ManyToOne
    @JoinColumn(name="accomnum")
    private Accom accom;
    private LocalDate date;

    public void setAccomNum(int accomNum) {
        if(this.accom == null){
            this.accom = new Accom();
        }
        this.accom.setAccomNum(accomNum);
    }
}
