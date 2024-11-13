package com.example.runawaytravel.controller;

import com.example.runawaytravel.entity.Dayoff;
import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.AccomImage;
import com.example.runawaytravel.repository.AccomImageRepository;
import com.example.runawaytravel.repository.AccomRepository;
import com.example.runawaytravel.repository.DayoffRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AccRegController {
    @Autowired
    AccomRepository acr;
    @Autowired
    AccomImageRepository air;
    @Autowired
    DayoffRepository dor;

    @PostMapping("/accUpload")
    public ResponseEntity<String> accreg(HttpSession session, @ModelAttribute Accom accom, @RequestParam(required = false) List<MultipartFile> imageUpload, @ModelAttribute AccomImage ai, @RequestParam(value = "day", required = false) LinkedList<Integer> days){
        //session에서 username가지고 오기
        accom.setUserUsername((String)session.getAttribute("username"));
        //accom 테이블에 휴일날짜 넣기
        if (days != null && !days.isEmpty()) {
            String dayoff ="";
            for(int elem : days){
                dayoff += String.valueOf(elem);
            }
            accom.setDayoff(dayoff);
        }
        //테이블에 저장
        Accom savedaccom = acr.save(accom);

        //closed 테이블
        List<LocalDate> oneweekdayoff = new ArrayList<>() ;
        LocalDate start = accom.getRegDate();
        int startdayofweek = start.getDayOfWeek().getValue();
        for(int elem : days){
            if(elem<startdayofweek){
                oneweekdayoff.add(start.plusDays(elem+7));
            } else{
                oneweekdayoff.add(start.plusDays(elem));
            }
        }

        List<Dayoff>dayoffs=new ArrayList<>();
        for(int i = 0 ; i<52 ; i++){
            for(LocalDate elem : oneweekdayoff){
                Dayoff day = new Dayoff();
                day.setAccomNum(savedaccom.getAccomNum());
                day.setDate(elem);
                dayoffs.add(day);
            }
            oneweekdayoff = oneweekdayoff.stream().map(e->e.plusDays(7)).collect(Collectors.toList());
        }
        dor.saveAll(dayoffs);


        //이미지 넣기
        ai.setAccomNum(savedaccom);
        if (imageUpload != null) {
            String path = "c:/kosa/project1/" + ai.getAccomNum().getAccomNum();
            File isDir = new File(path);
            if (!isDir.exists()) {
                isDir.mkdir();
            }
            List<MultipartFile> list = imageUpload;
            if (!list.isEmpty()) {
                for (MultipartFile mfile : list) {
                    try {
                        String originalFileName = mfile.getOriginalFilename();
                        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                        String filePath = String.valueOf(path + "/" + UUID.randomUUID().toString() + extension);
                        ai.setFilePath(filePath);
                        air.save(ai);
                        File imgF = new File(filePath);
                        mfile.transferTo(imgF);
                    } catch (IOException | StringIndexOutOfBoundsException | IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        ResponseEntity<String> entity;
        entity = new ResponseEntity<>("성공", HttpStatus.CREATED);
        return entity;
    }


}
