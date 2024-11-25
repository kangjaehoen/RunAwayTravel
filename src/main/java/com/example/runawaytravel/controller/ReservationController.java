
package com.example.runawaytravel.controller;

import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.AccomImage;
import com.example.runawaytravel.entity.Reservation;
import com.example.runawaytravel.repository.AccomImageRepository;
import com.example.runawaytravel.repository.AccomRepository;
import com.example.runawaytravel.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    @Autowired
    ReservationRepository resRep;

    @Autowired
    AccomRepository accomRep;

    @Autowired
    AccomImageRepository ai;

    //숙소 정보 조회
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> oneReserv(@RequestParam("accomNum") int accomnum) {
        Accom accom =accomRep.findById(accomnum).orElseThrow(() ->
                new IllegalArgumentException("Invalid accomNum:"+ accomnum));

        long revCnt=resRep.countReview(accomnum);
        String revRate=resRep.reviewRating(accomnum);
        Integer price=resRep.accomPrice(accomnum);

        List<Reservation> reservation= resRep.findByAccomnum(accomnum);
        List<AccomImage> images = ai.oneacc(accomnum);
        Map<String, Object> response= new HashMap<>();
        response.put("accom", accom);
        response.put("revCnt", revCnt);
        response.put("revRate", revRate );
        response.put("price", price);
        response.put("reservation", reservation);
        response.put("images", images);

        ResponseEntity entity=new ResponseEntity<>(response, HttpStatus.OK);

        return entity;
    }


    //예약하기 버튼-> 예약페이지(뷰에서 처리가능(?))
    @PostMapping
    public ResponseEntity<Map<String, Object>> reservBtn(@RequestBody Reservation reservation) {

        // 숙소 정보 가져오기
        Accom accom = reservation.getAccom();
        int accomnum = accom.getAccomNum();
        Accom accomInfo = accomRep.findById(accomnum).orElse(null);

        // 숙소 이미지 가져오기
        List<AccomImage> images = ai.oneacc(accomnum);

        // 체크인 시간 형식변환
        LocalTime chkinTime = accomInfo != null ? accomInfo.getChkin_Time() : null;
        String fmChkTime = chkinTime != null ? chkinTime.format(DateTimeFormatter.ofPattern("h:mm")) : null;

        // 리뷰 정보
        long revCnt = resRep.countReview(accomnum);
        String revRate = resRep.reviewRating(accomnum);

        // 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("reservation", reservation); // 예약 정보
        response.put("images", images);           // 이미지 리스트
        response.put("accom", accomInfo );
        response.put("revCnt", revCnt);           // 리뷰 개수
        response.put("revRate", revRate);         // 리뷰 평점

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    //예약날짜 중복체크
    @GetMapping("/dateList")
    public ResponseEntity<List<LocalDate>> checkDuplicate(@RequestParam Integer accomnum){

       List<Reservation> res= resRep.findByAccomnum(accomnum);

       //예약된 날짜들 저장 리스트
       List<LocalDate> bookdate= new ArrayList<>();

       //각 Reservation 객체의 체크인~체크아웃 날짜들 추출
       for(Reservation reservation: res){
           LocalDate checkIn= reservation.getChkin_Date();
           LocalDate checkOut= reservation.getChkout_Date();

           //체크인~체크아웃 전날까지 반복해서 리스트에 추가
           LocalDate currentDate= checkIn;

           //체크아웃 전날까지의 날짜를 계산
           while(!currentDate.isAfter((checkOut.minusDays(1)))){
               bookdate.add(currentDate);
               currentDate= currentDate.plusDays(1);
           }

       }
        return ResponseEntity.ok(bookdate);
    }


    @PutMapping("/insertRes")
    @Transactional
    public ResponseEntity<?> insertRes(@RequestBody Map<String,Object> reservinfo, Principal principal) {
        if (principal == null || principal.getName() == null || principal.getName().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = principal.getName();
        System.out.println("Logged in user: " + username);
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




