package com.example.runawaytravel.controller;

import com.example.runawaytravel.entity.Reservation;
import com.example.runawaytravel.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SellCalendar {
    @Autowired
    ReservationRepository rr;
    @GetMapping(value = "/resmonth")
    public ResponseEntity<Map<String,Object>>resmonth(@RequestParam LocalDate start, @RequestParam LocalDate end){
        String username = "testID";
        Map<String ,Object> response = new HashMap<>();
        response.put("resmonth", rr.resMonth(username, start,end));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
