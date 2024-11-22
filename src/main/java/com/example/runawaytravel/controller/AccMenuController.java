package com.example.runawaytravel.controller;

import com.example.runawaytravel.dto.PageDTO;
import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.Dayoff;
import com.example.runawaytravel.entity.User;
import com.example.runawaytravel.jwt.JWTUtil;
import com.example.runawaytravel.repository.AccomImageRepository;
import com.example.runawaytravel.repository.AccomRepository;
import com.example.runawaytravel.repository.DayoffRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin(origins="http://localhost:5173", allowedHeaders = "*", exposedHeaders="Authorization", allowCredentials = "true")
public class AccMenuController {
    @Autowired
    AccomRepository acr;
    @Autowired
    AccomImageRepository air;
    @Autowired
    DayoffRepository dor;

    @PostMapping("/myaccomtable")
    public ResponseEntity<Map<String, Object>> myaccomtable(@RequestBody PageDTO page, Principal principal) {
        if (principal == null || principal.getName() == null || principal.getName().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = principal.getName();
        System.out.println("Logged in user: " + username);

        try {
            PageRequest pr = PageRequest.of(page.getPage(), 10);
            Page<Accom> mylist = acr.searchmine(username, page.getKey(), pr);
            Map<String, Object> response = new HashMap<>();
            response.put("content", mylist.getContent()); // Accom 리스트
            response.put("currentPage", mylist.getNumber());
            response.put("totalPages", mylist.getTotalPages());
            response.put("totalElements", mylist.getTotalElements());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PostMapping(value = "/changeonsale")
    @PreAuthorize("hasAnyRole('USER')")
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
//    @DeleteMapping(value = "/deleteacclist")
//    public ResponseEntity<String>deleteacclist(@RequestParam List<Integer>accomNumList){
//        List<Accom>accomlist =acr.manyacc(accomNumList);
//        for(Accom accom : accomlist){
//            accom.setOnSale(1);
//        }
//        acr.saveAll(accomlist);
//        dor.deleteAll(dor.manyacc(accomNumList));
//        return new ResponseEntity<>("성공",HttpStatus.OK);
//    }
}
