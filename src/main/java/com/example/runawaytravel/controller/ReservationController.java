
package com.example.runawaytravel.controller;

import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.Reservation;
import com.example.runawaytravel.repository.AccomRepository;
import com.example.runawaytravel.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "*")
public class ReservationController {
    @Autowired
    ReservationRepository resRep;

    @Autowired
    AccomRepository accomRep;


    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> oneReserv(@RequestParam("accomNum") int accomnum) {
    //숙소 정보 조회
        Accom accom =accomRep.findById(accomnum).orElseThrow(() ->
                new IllegalArgumentException("Invalid accomNum:"+ accomnum));

        long revCnt=resRep.countReview(accomnum);
        String revRate=resRep.reviewRating(accomnum);
        Integer price=resRep.accomPrice(accomnum);

        List<Reservation> reservation= resRep.findByAccomnum(accomnum);

        Map<String, Object> response= new HashMap<>();
        response.put("accom", accom);
        response.put("revCnt", revCnt);
        response.put("revRate", revRate );
        response.put("price", price);
        response.put("reservation", reservation);

        ResponseEntity entity=new ResponseEntity<>(response, HttpStatus.OK);

        return entity;
    }


    //예약하기 버튼-> 예약페이지(뷰에서 처리가능(?))
    @PostMapping
    public ResponseEntity<Reservation> reservBtn(@RequestBody Reservation reservation) {

        Accom accom = reservation.getAccom();
        int accomnum= accom.getAccomNum();
        Accom accomInfo= accomRep.findById(accomnum).get();

        //체크인 시간 형식변환
        LocalTime chkinTime = accomInfo.getChkin_Time();
        DateTimeFormatter format= DateTimeFormatter.ofPattern("h:mm");
        String fmChkTime= chkinTime.format(format);

        long revCnt=resRep.countReview(accomnum);
        String revRate=resRep.reviewRating(accomnum);

        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @PostMapping("/chkDuplicate")
    public ResponseEntity<String> checkDuplicate(@RequestBody Reservation reservation){

       int duplicate= resRep.chkDateDuplicate(reservation);

       if(duplicate > 0){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("해당 날짜에 이미 예약이 존재합니다.");
       }else{
            return ResponseEntity.ok("예약 가능일 입니다.");
       }
    }


    @PutMapping("/insertRes")
    @Transactional
    public ResponseEntity<?> insertRes(@RequestBody Map<String,Object> reservinfo ) {
        Reservation reservation= new Reservation();

        int accomNum = Integer.valueOf(reservinfo.get("accomNum").toString());
        Optional<Accom> accom= accomRep.findById(accomNum);

        if(accom.isPresent()){
            reservation.setAccom(accom.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 숙소가 존재하지 않습니다.");
        }
        // 필수 예약 정보 설정하기
        reservation.setChkin_Date(LocalDate.parse(reservinfo.get("checkIn").toString()));
        reservation.setChkout_Date(LocalDate.parse(reservinfo.get("checkOut").toString()));
        reservation.setAdultCnt(Integer.valueOf(reservinfo.get("adultCnt").toString()));
        reservation.setKidCnt(Integer.valueOf(reservinfo.get("kidCnt").toString()));
        reservation.setResDate(LocalDate.now());
        Reservation aa = resRep.save(reservation);
        reservation.setResNum(aa.getResNum());
        return ResponseEntity.ok(reservation);
    }
}




