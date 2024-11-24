package com.example.runawaytravel.controller;

import com.example.runawaytravel.entity.User;
import com.example.runawaytravel.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FindController {

    private final UserRepository userRepository;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 12;

    public FindController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/UsernameFind")
    public ResponseEntity<Map<String, String>> usernameFind(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();

        String name = request.get("name");
        String birth = request.get("birth");
        String email = request.get("email");

        User user = userRepository.findByNameAndBirthAndEmail(name, birth, email);

        if (user != null) {
            response.put("username", user.getUsername());
            return ResponseEntity.ok(response);
        }

        response.put("error", "아이디를 찾을 수 없습니다.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/PwFind")
    public ResponseEntity<Map<String, String>> pwFind(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();

        String username = request.get("username");
        String name = request.get("name");
        String birth = request.get("birth");
        String email = request.get("email");

        User user = userRepository.findByUsernameAndNameAndBirthAndEmail(username, name, birth, email);

        if (user != null) {
            String temporaryPassword = generateTemporaryPassword();

            user.setPassword(temporaryPassword);
            userRepository.save(user);

            response.put("temporaryPassword", temporaryPassword);
            return ResponseEntity.ok(response);
        }

        response.put("error", "비밀번호를 찾을 수 없습니다.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    private static String generateTemporaryPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }
}
