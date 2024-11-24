package com.example.runawaytravel.controller;

import com.example.runawaytravel.dto.JoinDTO;
import com.example.runawaytravel.service.JoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final JoinService joinService;

    public UserController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinDTO joinDTO) {
        try {
            // 서비스 호출
            joinService.joinProcess(joinDTO);

            // 성공 시 응답
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
        } catch (IllegalArgumentException e) {
            // 중복된 사용자 이름 등 특정 예외 처리
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 사용자 이름입니다.");
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류가 발생했습니다.");
        }
    }
}