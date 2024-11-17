package com.example.runawaytravel.controller;

import com.example.runawaytravel.dto.JoinDTO;
import com.example.runawaytravel.service.JoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:5173", allowedHeaders = "*", exposedHeaders="Authorization", allowCredentials = "true")
public class JoinController {

    private final JoinService joinService;
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }
    @PostMapping("/join")
    public ResponseEntity<String> joinProcess(@RequestBody JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);
        return ResponseEntity.ok("ok");
    }
}
