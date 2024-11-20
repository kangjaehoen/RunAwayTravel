package com.example.runawaytravel.controller;

import com.example.runawaytravel.dto.PaymentDTO;
import com.example.runawaytravel.entity.Pay;
import com.example.runawaytravel.entity.Reservation;
import com.example.runawaytravel.entity.User;
import com.example.runawaytravel.repository.PayRepository;
import com.example.runawaytravel.repository.ReservationRepository;
import com.example.runawaytravel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;


@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class PaymentController {
    @Autowired
    PayRepository payResp;

    @Autowired
    ReservationRepository resResp;

    @Autowired
    UserRepository userResp;

    //결제내역
    @GetMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> payment(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                     @RequestParam(value = "status", required = false) Character status,
                                     @RequestParam(value = "year", required = false) Integer year,
                                     @RequestParam(value = "month", required = false) Integer month ) {

        String id= "sumin0901";


        Optional<User> userOpt= userResp.findById(id);

        if(userOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 한 회원만 접근가능 한 페이지 입니다.");
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Pay> paginatedList = payResp.findAllPayments(id, status, year, month, pageRequest);

        User user = userOpt.get();
        Page<Pay> list = payResp.findAllPayments(id, status, year, month, pageRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("content", paginatedList.getContent()); // 데이터 리스트
        response.put("totalPages", paginatedList.getTotalPages()); // 전체 페이지 수
        response.put("currentPage", paginatedList.getNumber()); // 현재 페이지 번호
        response.put("totalElements", paginatedList.getTotalElements()); // 전체 요소 개수

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> insertPay(@RequestBody PaymentDTO payinfo){
        Pay pay = new Pay();

        // impUid 설정
        pay.setImpUid(payinfo.getImpUid());

        // apply_num 필드는 선택적으로 처리
        if (payinfo.getApply_num() != null) {
            pay.setApply_num(payinfo.getApply_num());
        }

        // 예약번호 가져오기
        Integer resnum = payinfo.getResNum();
        if (resnum == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("예약번호가 필요합니다.");
        }

        Optional<Reservation> resOpt = resResp.findById(resnum);
        if (resOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 예약번호에 대한 정보가 없습니다.");
        }

        Reservation reservation = resOpt.get();
        pay.setResNum(reservation);
        pay.setAccomNum(reservation.getAccom());

        // 추가적으로 필요한 필드 설정
        pay.setPay_Status(payinfo.getPay_status());
        pay.setMerchantUid(payinfo.getMerchantUid());
        pay.setAmount(payinfo.getAmount());
        pay.setName(payinfo.getName());
        pay.setPayDate(LocalDate.now());
        pay.setPay_Status('Y');

        payResp.save(pay);

        return ResponseEntity.ok(pay);
    }

    //결제취소
    @PostMapping("/cancel")
    @Transactional
    public ResponseEntity<String> cancelPayment(@RequestBody List<String> impUids) {


        if (impUids == null || impUids.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("취소할 결제를 선택하세요.");
        }

        for (String impUid : impUids) {
            int updateStatus = payResp.cancelPay(impUid);

            if (updateStatus > 0) {
                return ResponseEntity.ok("결제취소가 완료되었습니다.");
             }else{
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("결제정보가 없습니다:"+impUid);
             }
        }

        return ResponseEntity.ok("결제 취소가 완료되었습니다.");
    }


}
