package com.example.runawaytravel.controller;

import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.AccomImage;
import com.example.runawaytravel.repository.AccomImageRepository;
import com.example.runawaytravel.repository.AccomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin(origins="*", allowedHeaders = "*")
public class MainController {
    @Autowired
    AccomRepository acr;
    @Autowired
    AccomImageRepository air;

    @GetMapping("/main")
    public ResponseEntity<String> mainP() {
        return ResponseEntity.ok("main controller");
    }

    @GetMapping(value = "/getaccominformation")
    public ResponseEntity<Accom>getaccominformation(@RequestParam int accomNum){
        Accom response = acr.oneacc(accomNum);
        return new ResponseEntity<>(response,HttpStatus.OK );
    }
    @GetMapping(value = "/getaccomimage")
    public ResponseEntity<AccomImage>getaccomimage(@RequestParam int accomNum){
        return new ResponseEntity<>(air.findTopByAccom_AccomNum(accomNum),HttpStatus.OK);
    }
    @GetMapping(value = "/getrandom")
    public ResponseEntity<Map<String,Object>>getrandomAccom(@RequestParam(defaultValue = "") int page){
        Page<Accom>randomAcc = acr.randomAccom( PageRequest.of(page,6));
        Map<String,Object>response=new HashMap<>();
        response.put("getContent",randomAcc.getContent());
        response.put("getTotalPages",randomAcc.getTotalPages());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping(value = "/search")
    public ResponseEntity<Map<String,Object>>searchAccom(@RequestParam(defaultValue = "") String key, @RequestParam(defaultValue = "0") int page){
        Page<Accom>acc = acr.searchAccom(key, PageRequest.of(page,6));
        Map<String,Object>response=new HashMap<>();
        response.put("getContent",acc.getContent());
        response.put("getTotalPages",acc.getTotalPages());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}