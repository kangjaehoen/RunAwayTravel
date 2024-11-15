package com.example.runawaytravel.controller;

import com.example.runawaytravel.DTO.PageDTO;
import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.repository.AccomRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class AccMenuController {
    @Autowired
    AccomRepository acr;

    @PostMapping("/myaccomtable")
    public ResponseEntity<Map<String, Object>> myaccomtable(@RequestBody PageDTO page, HttpSession session) {
        String username= (String) session.getAttribute("username");
        if(username == null){username = "testID";}//로그인 실패시

        System.out.println("현재 아이디 : "+username);
        System.out.println("전달된 키 : "+page.getKey());
        System.out.println("전달된 쪽 : "+page.getPage());

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
}
