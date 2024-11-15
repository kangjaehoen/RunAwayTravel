package com.example.runawaytravel.repository;

import com.example.runawaytravel.entity.Pay;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface PayRepository extends JpaRepository<Pay, String> {

    //결제확인
//    @Query("SELECT p.username.username, p.payDate, p.name FROM Pay p  WHERE p.username.username = :id ORDER BY p.payDate DESC")
//    public List<Pay> findByById(@Param("id") String id);

    //예약테이블에 생성된 resNum 가져오기
    @Query("select r.resNum from Reservation r where r.accom.accomNum= :accomnum ORDER BY r.resNum DESC ")
    public Optional<Integer> selectresNum(@Param("accomNum") int accomnum);

    //결제내역
//    @Select("select * from pay where accomNum= #{accomNum}")
//    public PayVO payInfo(@Param("accomNum") int accomNum);

    //impUid로 결제정보 찾기
    Optional<Pay> findByImpUid(String impUid);

    //결제취소
    @Modifying
    @Query("update Pay p set p.pay_Staus='N' where p.impUid= :impuid")
    public int cancelPay(@Param("impUid") String impuid);

    /*
    //resNum에 해당하는 모든 결제정보
    @Select("select * from pay where resNum=#{resNum}")
    public List<PaymentDTO> listAll(@Param("resNum") int resNum);

    //세션에 따른 리스트출력
    @Select("select payDate,impUid,name,amount,pay_Status from pay where id=#{id}")
    public List<PaymentDTO> payList(@Param("id") String id);

    //결제상태에 따른 조회
    @Select("select * from pay where pay_Status=#{pay_Status}")
    public List<PaymentDTO> statusByList(@Param("pay_Status") String payStatus,@Param("resNum") int resNum);
    */

}




