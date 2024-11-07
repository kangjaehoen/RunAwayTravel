package com.example.runawaytravel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "wishlist")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishnum")
    private int wishNum;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private User userName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accomnum")
    private Accom accomNum;
    @Column(name = "checkstatus")
    private int checkStatus;
}

