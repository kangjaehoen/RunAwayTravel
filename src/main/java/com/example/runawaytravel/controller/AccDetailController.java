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
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/accDetail")
public class AccDetailController {
    @Autowired
    AccomRepository accomRep;

    @Autowired
    ReservationRepository resRep;

    @Autowired
    AccomImageRepository ai;

    @GetMapping("/{accomnum}")
    public ResponseEntity<Map<String,Object>> accDetail(@PathVariable("accomnum") int accomnum) {

        Optional<Accom> accom= accomRep.findById(accomnum);
        List<AccomImage> images = ai.oneacc(accomnum);
        long revCnt=resRep.countReview(accomnum);
        String revRate=resRep.reviewRating(accomnum);

        List<Reservation> reservation= resRep.findByAccomnum(accomnum);

        Map<String,Object> response=new HashMap<>();
        response.put("accom",accom.orElse(null));
        response.put("revCnt", revCnt );
        response.put("revRate", revRate );
        response.put("reservation", reservation);
        response.put("images", images);

        ResponseEntity entity= new ResponseEntity(response, HttpStatus.OK);

        return entity;
    }


}
