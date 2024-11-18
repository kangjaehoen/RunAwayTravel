package com.example.runawaytravel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:5175", allowedHeaders = "*", exposedHeaders="Authorization", allowCredentials = "true")
public class AdminController {

    @GetMapping("/admin") //responseEntity
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("admin controller");
    }
}
