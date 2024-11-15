package com.example.runawaytravel.controller;

import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.Pay;
import com.example.runawaytravel.entity.Reservation;
import com.example.runawaytravel.repository.PayRepository;
import com.example.runawaytravel.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PayRepository payResp;

    @Autowired
    ReservationRepository resResp;

/*
    @GetMapping
    public ResponseEntity<String> payment() {

        if(id == null){
            redirectAttributes.addFlashAttribute("errorMessage", "로그인 한 회원만 접근가능한 페이지 입니다.");
        }
        List<Pay> list = dao.payList(id);

        return "payment";
    }
*/


    @PutMapping
    public ResponseEntity<?> insertPay(@RequestBody Pay pay){
        
        //결제번호: 자동생성이 아니라서 persist() 필요
        String impuid= "imp_497727200392";
        pay.setImpUid(impuid);

        //예약번호 가져오기
        Reservation res= pay.getResNum();

        if(res== null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 예약번호에 대한 정보가 없습니다.");
        }

        //예약과 숙소정보 가져오기
        Integer resNum= res.getResNum();
        Accom accom= res.getAccom();

        if(resNum==null | accom==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("예약정보 또는 숙소정보가 존재하지 않습니다.");
        }

        Optional<Reservation> resOpt= resResp.findById(resNum);

        if(resOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 예약번호에 대한 정보가 없습니다.");
        }

        pay.setResNum(resOpt.get());
        pay.setAccomNum(accom);

        Pay payment= payResp.save(pay);

        return ResponseEntity.ok(payment);
    }


    //결제취소
    @PutMapping("/cancel")
    @Transactional
    public ResponseEntity<String> cancelPayment(@RequestBody Pay pay) {

        String impUid = pay.getImpUid();

        // 각 impUid에 대해 결제 취소 처리 (DB에서 결제 상태 변경 또는 삭제)
        int updateStatus= payResp.cancelPay(impUid);

         if(updateStatus > 0){
            return ResponseEntity.ok("결제취소가 완료되었습니다.");
         }else{
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("결제정보가 없습니다:"+impUid);
         }
    }

}
