package com.example.runawaytravel.repository;

import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.Reservation;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    //숙소정보
    @Query("select r from Reservation r inner join fetch r.accomNum a where r.accomNum.accomNum = :accomnum")
    public List<Reservation> findByAccomnum(@Param("accomnum") int accomnum);

    //예약정보 가져오기
    @Query("select r.resDate, r.chkin_Date, r.chkout_Date, r.adultCnt, r.kidCnt, a.chkin_Time, a.chkout_Time, a.accName from Reservation r inner join r.accomNum a where r.accomNum.accomNum= :accomnum")
    public List<Reservation> oneReservation(@Param("accomnum") int accomnum);

    //리뷰 개수
    @Query("select count(r) from Review r where r.accomNum.accomNum = :accomnum")
    public long countReview(@Param("accomnum") int accomnum);


    //리뷰 별점
    @Query("select round(avg(r.satisfy),1) from Review r inner join r.accomNum a where a.accomNum= :accomnum")
    public  String reviewRating(@Param("accomnum") int accomnum);

    //숙소 가격
    /*@Query("select r.accomNum.price from Reservation r where r.accomNum.accomNum = :accomnum")
    public int accomPrice(@Param("accomnum") int accomnum);
*/
    @Query("SELECT COALESCE(r.price, 0) FROM Accom r WHERE r.accomNum = :accomnum")
    public Integer accomPrice(@Param("accomnum") int accomnum);


    //숙소 정보
    @Query("select a from Accom a where a.accomNum= :accomnum")
    public Accom findAccom(@Param("accomnum") int accomnum);

    //예약 중복확인
  /*  @Query("select count(r) from Reservation r where r.chkin_Date= :chkin_Date and r.chkout_Date= :chkout_Date")
    public int checkResDuplicate(Reservation reservation);*/

    @Query("select count(*) from Reservation r where r.chkin_Date = :#{#reservation.chkin_Date} and r.chkout_Date = :#{#reservation.chkout_Date}")
    public int chkDateDuplicate(@Param("reservation") Reservation reservation);

    //accomNum을 기준으로 최근의 Reservation 찾기
    @Query("SELECT r FROM Reservation r WHERE r.accom.accomNum = :accomnum ORDER BY r.resNum DESC")
    Optional<Reservation> findTopByAccomNum(int accomnum);

}
