package com.example.runawaytravel.controller;

import com.example.runawaytravel.DTO.PageDTO;
import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.repository.AccomImageRepository;
import com.example.runawaytravel.repository.AccomRepository;
import com.example.runawaytravel.repository.DayoffRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class AccMenuController {
    @Autowired
    AccomRepository acr;
    @Autowired
    AccomImageRepository air;
    @Autowired
    DayoffRepository dor;

    @PostMapping("/myaccomtable")
    public ResponseEntity<Map<String, Object>> myaccomtable(@RequestBody PageDTO page, HttpSession session) {
        String username= (String) session.getAttribute("username");
        if(username == null){username = "testID";}//로그인 실패시

        System.out.println("현재 아이디 : "+username);

        PageRequest pr = PageRequest.of(page.getPage(),10);
        Page<Accom> mylist =acr.searchmine(username, page.getKey(),pr);

        // 필요한 데이터를 Map 형태로 변환
        Map<String, Object> response = new HashMap<>();
        response.put("content", mylist.getContent()); // Accom 리스트
        response.put("currentPage", mylist.getNumber());
        response.put("totalPages", mylist.getTotalPages());
        response.put("totalElements", mylist.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping(value = "/changeonsale")
    public ResponseEntity<String>changeonsale(@RequestBody List<Integer>accomNumList){
        List<Accom>list= acr.manyacc(accomNumList);
        for(Accom accom : list){
            if(accom.getOnSale()==1){
                accom.setOnSale(0);
            } else if (accom.getOnSale()==0) {
                accom.setOnSale((1));
            }
        }
        acr.saveAll(list);
        return new ResponseEntity<String>("성공",HttpStatus.OK);
    }
}
