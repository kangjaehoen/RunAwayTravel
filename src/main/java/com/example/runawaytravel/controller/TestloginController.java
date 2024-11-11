package com.example.runawaytravel.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestloginController {

    @RequestMapping(value = "/sellerPage")
    @ResponseBody
    public ModelAndView sellpage(HttpSession session, String username) {
        ModelAndView mav = new ModelAndView();
        session.setAttribute("id", username);
        session.setAttribute("username",username);
        mav.addObject("id", session.getAttribute("id"));
        mav.setViewName("forward:/myacclist");
        return mav;
    }
}