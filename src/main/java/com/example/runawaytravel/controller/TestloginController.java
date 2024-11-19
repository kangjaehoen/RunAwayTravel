package com.example.runawaytravel.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class TestloginController {

    @RequestMapping(value = "/sellerPage")
    public ModelAndView sellpage(HttpSession session, String username) {
        ModelAndView mav = new ModelAndView();
        session.setAttribute("id", username);
        session.setAttribute("username",username);
        mav.addObject("id", session.getAttribute("id"));
        mav.setViewName("forward:/myacclist");
        return mav;
    }
    
    /*이거 안됨
    @PostMapping(value = "/testlogin")
    public ResponseEntity<String>testlogin(HttpSession session, @RequestBody Map<String,String>request){
        session.setAttribute("username",request.get("username"));
        System.out.println("=".repeat(50)+request.get("username"));//잘됨
        return new ResponseEntity<>("로그인성공", HttpStatus.OK);
    }
    @PostMapping(value = "/testlogout")
    public ResponseEntity<String>testlogout(HttpSession session){
        session.removeAttribute("username");
        return new ResponseEntity<>("로그아웃성공", HttpStatus.OK);
    }*/
}