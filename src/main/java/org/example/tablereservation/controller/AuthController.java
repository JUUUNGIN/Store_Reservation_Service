package org.example.tablereservation.controller;

import org.example.tablereservation.model.dto.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
//        로그인 처리는 스프링 시큐리티가 자동으로 수행
        return ResponseEntity.ok("Logged in successful!!!!!");
    }
}
