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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class AccRegController {
    @Autowired
    AccomRepository acr;
    @Autowired
    AccomImageRepository air;
    @Autowired
    DayoffRepository dor;

    @PostMapping("/accUpload")
    public ResponseEntity<String> regAcc(HttpSession session, @ModelAttribute Accom accom, @RequestParam(required = false) List<MultipartFile> imageUpload, @ModelAttribute AccomImage ai, @RequestParam(value = "dayoff", required = false) LinkedList<Integer> days) {
        //session에서 username가지고 오기
        String username = (String) session.getAttribute("username");
        if (username == null) {
            username = "testID";
        }
        //저장할 accom 세팅
        accom = setting(accom,username,days);
        //테이블에 저장
        Accom savedAccom = acr.save(accom);
        settingDayoff(days,savedAccom);
        settingImg(savedAccom,ai,imageUpload);
        return new ResponseEntity<>("성공", HttpStatus.CREATED);
    }
    public Accom setting(Accom accom, String username, List<Integer>days){
        accom.setUserUsername(username);
        accom.setRegDate(LocalDate.now());
        accom.setOnSale(1);
        //accom 테이블에 휴일날짜 넣기
        if (days != null && !days.isEmpty()) {
            String dayoff = "";
            for (int elem : days) {
                dayoff += String.valueOf(elem);
            }
            accom.setDayoff(dayoff);
        }
        return accom;
    }
    public void settingDayoff(List<Integer>days, Accom savedAccom){
        dor.deleteAll(dor.oneacc(savedAccom.getAccomNum()));//초기화
        if (days != null && !days.isEmpty()) {
            List<LocalDate> oneweekdayoff = new ArrayList<>();
            LocalDate start = savedAccom.getRegDate();
            int startdayofweek = start.getDayOfWeek().getValue();
            for (int elem : days) {
                if (elem < startdayofweek) {
                    oneweekdayoff.add(start.plusDays(elem + 7));
                } else {
                    oneweekdayoff.add(start.plusDays(elem));
                }
            }

            List<Dayoff> dayoffs = new ArrayList<>();
            for (int i = 0; i < 52; i++) {
                for (LocalDate elem : oneweekdayoff) {
                    Dayoff day = new Dayoff();
                    day.setAccomNum(savedAccom.getAccomNum());
                    day.setDate(elem);
                    dayoffs.add(day);
                }
                oneweekdayoff = oneweekdayoff.stream().map(e -> e.plusDays(7)).collect(Collectors.toList());
            }
            dor.saveAll(dayoffs);
        }
    }
    public void settingImg(Accom savedAccom, AccomImage ai, List<MultipartFile>imageUpload){
        ai.setAccom(savedAccom);
        if (imageUpload != null) {
            //새로운 사진 있을 때만 초기화
            String path = "c:/education/runawaytravel-vue/public/images/" + savedAccom.getAccomNum(); //저장위치
            //이미지 테이블 제거
            //파일 삭제
            File isDir = new File(path);
            if (isDir.exists()) {
                File[] files = isDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        file.delete();
                    }
                }
                isDir.delete();
            }
            air.deleteAll(air.oneacc(savedAccom.getAccomNum()));

            isDir = new File(path);
            if (!isDir.exists()) {
                isDir.mkdir();
            }
            List<MultipartFile> list = imageUpload;
            if (!list.isEmpty()) {
                for (MultipartFile mfile : list) {
                    try {
                        //이름설정
                        String originalFileName = mfile.getOriginalFilename();
                        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                        String filename = String.valueOf(UUID.randomUUID().toString() + extension);//파일이름
                        //테이블 저장
                        AccomImage eachai = new AccomImage();
                        eachai.setAccom(savedAccom);
                        eachai.setFilePath( savedAccom.getAccomNum()+"/"+filename );
                        air.save(eachai);
                        //서버 저장
                        File imgF = new File(path+"/"+filename);//실제 서버 저장위치
                        mfile.transferTo(imgF);
                    } catch (IOException | StringIndexOutOfBoundsException | IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @GetMapping(value = "/accLoad")
    public ResponseEntity<Map<String, Object>> loadAcc(@RequestParam int accomNum) {
        Map response = new HashMap<String, Object>();
        Accom oneAcc = acr.oneacc(accomNum);
        if (oneAcc != null) {
            response.put("oneAcc", oneAcc);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.out.println("해당 숙소번호는 없음");
            response.put("oneAcc", "해당 숙소번호 없음");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/accDel")
    public ResponseEntity<String> deleteAcc(@RequestParam int accomNum) {
        //숙소 테이블 onSale 변경
        Accom trash = acr.oneacc(accomNum);
        trash.setOnSale(2);
        acr.save(trash);
        //쉬는날 테이블 제거
        dor.deleteAll(dor.oneacc(accomNum));
        //이미지 테이블 제거
        //파일 삭제
        String path = "c:/education/runawaytravel-vue/public/images/" + accomNum;
        File isDir = new File(path);
        if (isDir.exists()) {
            File[] files = isDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            isDir.delete();
        }
        air.deleteAll(air.oneacc(accomNum));
        return new ResponseEntity<>("삭제성공", HttpStatus.OK);
    }

    //test1
    @PostMapping("/uploadtest1")
    public ResponseEntity<String> accregtest1() {
        return new ResponseEntity<>("삽입성공", HttpStatus.CREATED);
    }
}
