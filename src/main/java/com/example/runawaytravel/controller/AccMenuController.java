package com.example.runawaytravel.controller;

import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.repository.AccomRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:5173")
public class AccMenuController {
    @Autowired
    AccomRepository acr;
    @RequestMapping("/accregist")
    public String accRegist(){
        return "accRegister";
    }
    @RequestMapping("/myacclist")
    public ModelAndView searchmine(HttpSession session, @RequestParam(defaultValue = "") String key, @RequestParam(defaultValue = "0")int page) {
        ModelAndView mav = new ModelAndView();
        String username = (String) session.getAttribute("username");
        PageRequest pr = PageRequest.of(page,10);
        Page<Accom> mylist =acr.searchmine(username,key,pr);
        mav.addObject("mylist", mylist);

        long maxpage = ( acr.countmylist(username,key) - 1L) / 10L +1L;
        long maxmove = Math.min(maxpage,(long)page + 6L);
        long minmove = Math.max(1L,(long)page-4L);
        List<Long>pageNav = new LinkedList<>();
        for(long i =minmove ; i <= maxmove ; i++){
            pageNav.add(i);
        }
        mav.addObject("page",page);
        mav.addObject("pNav",pageNav);
        mav.setViewName("myAcc");
        return mav;
    }
    @RequestMapping("/myaccomtable")
    @ResponseBody
    public ResponseEntity<Page<Accom>>  myaccomtable(HttpSession session, @RequestParam(defaultValue = "") String key, @RequestParam(defaultValue = "0") int page){
        String username = (String) session.getAttribute("username");
        PageRequest pr = PageRequest.of(page,10);
        Page<Accom> mylist =acr.searchmine(username,key,pr);
        ResponseEntity<Page<Accom>> entity = new ResponseEntity<>(mylist, HttpStatus.OK);
        return entity;
    }

    @RequestMapping("/myacclistonsale")
    public ModelAndView searchmineonsale(HttpSession session, @RequestParam(defaultValue = "0")int page) {
        ModelAndView mav = new ModelAndView();
        String username = (String) session.getAttribute("username");
        PageRequest pr = PageRequest.of(page,10);
        Page<Accom> mylist =acr.searchmineonsale(username,pr);
        mav.addObject("mylist", mylist);

        long maxpage = (acr.countmyonsalelist(username) -1L) / 10L +1L;
        long maxmove = Math.min(maxpage,(long)page + 6L);
        long minmove = Math.max(1L,(long)page-4L);
        List<Long>pageNav = new LinkedList<>();
        for(long i =minmove ; i <= maxmove ; i++){
            pageNav.add(i);
        }
        mav.addObject("pageS",page);
        mav.addObject("pNavS",pageNav);
        mav.setViewName("myAcc");
        return mav;
    }
}