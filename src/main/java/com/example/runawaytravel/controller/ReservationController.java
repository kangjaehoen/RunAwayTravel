
package com.example.runawaytravel.controller;

import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.Reservation;
import com.example.runawaytravel.repository.AccomRepository;
import com.example.runawaytravel.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    ReservationRepository resRep;

    @Autowired
    AccomRepository accomRep;


    @GetMapping
    public ResponseEntity<Map<String, Object>> oneReserv(@RequestBody Reservation reservation) {
        Accom accom = reservation.getAccomNum();
        int accomnum= accom.getAccomNum();

        long revCnt=resRep.countReview(accomnum);
        String revRate=resRep.reviewRating(accomnum);
        Integer price=resRep.accomPrice(accomnum);

        Map<String, Object> response= new HashMap<>();
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

        Accom accom = reservation.getAccomNum();
        int accomnum= accom.getAccomNum();
        Accom accomInfo= accomRep.findById(accomnum).get();

        //체크인 시간 형식변환
        LocalTime chkinTime = accomInfo.getChkin_Time();
        DateTimeFormatter format= DateTimeFormatter.ofPattern("h:mm");
        String fmChkTime= chkinTime.format(format);

        long revCnt=resRep.countReview(accomnum);
        String revRate=resRep.reviewRating(accomnum);

        reservation.setChkinTime(fmChkTime);
        reservation.setReviewcnt(revCnt);
        reservation.setReviewRate(revRate);


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
    public ResponseEntity<?> insertRes(@RequestBody Reservation reservation) {

            int accomnum = reservation.getAccomNum().getAccomNum();
            Optional<Accom> accom = accomRep.findById(accomnum);

            if(accom.isPresent()){
                reservation.setAccom(accom.get());
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 숙소가 존재하지 않습니다.");
            }

            Reservation res= resRep.save(reservation);
            return ResponseEntity.ok(res);
        }

    }



