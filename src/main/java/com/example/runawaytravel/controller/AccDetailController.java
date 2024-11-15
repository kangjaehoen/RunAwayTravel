package com.example.runawaytravel.controller;

import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.repository.AccomRepository;
import com.example.runawaytravel.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/accDetail")
public class AccDetailController {
    @Autowired
    AccomRepository accomRep;

    @Autowired
    ReservationRepository resRep;

    @GetMapping("/{accomnum}")
    public ResponseEntity<Map<String,Object>> accDetail(@PathVariable("accomnum") int accomnum) {

        Optional<Accom> accom= accomRep.findById(accomnum);
        long revCnt=resRep.countReview(accomnum);
        String revRate=resRep.reviewRating(accomnum);

        Map<String,Object> response=new HashMap<>();
        response.put("accom",accom);
        response.put("revCnt", revCnt);
        response.put("revRate", revRate );

        ResponseEntity entity= new ResponseEntity(response, HttpStatus.OK);

        return entity;
    }


}
