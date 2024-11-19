package com.example.runawaytravel.repository;

import com.example.runawaytravel.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
    @Query("select distinct r from Reservation r" +
            " join fetch r.accom a" +
            " join fetch r.user u1" +
            " join fetch a.user u2" +
            " where u2.username = :username and r. chkin_Date <= :end and chkout_Date >= :start")
    public List<Reservation> resMonth(String username, LocalDate start, LocalDate end);
    @Query("select distinct r from Reservation r" +
            " join fetch r.accom a" +
            " join fetch r.user u1" +
            " join fetch a.user u2" +
            " where a.accomNum = :accomNum and r. chkin_Date <= :end and chkout_Date >= :start")
    public List<Reservation> resMonthbyAccomNum(int accomNum, LocalDate start, LocalDate end);

}
