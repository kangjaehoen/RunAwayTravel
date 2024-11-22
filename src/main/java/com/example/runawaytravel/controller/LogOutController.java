package com.example.runawaytravel.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogOutController {

    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader(value = "Authorization", required = false) String token, HttpServletResponse res) {
        log.info("logout 실행");
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("Authorization", "delete");
        return new ResponseEntity<>("로그아웃 성공", header, HttpStatus.OK);
    }
}
