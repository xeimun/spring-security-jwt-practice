package com.example.securityflow.controller;

import com.example.securityflow.dto.JwtResponse;
import com.example.securityflow.dto.LoginRequest;
import com.example.securityflow.dto.RegisterRequest;
import com.example.securityflow.dto.RegisterResponse;
import com.example.securityflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = userService.registerUser(
                request.getUsername(),
                request.getPassword(),
                request.getRole() == null ? "ROLE_USER" : request.getRole()
        );

        return ResponseEntity.ok(response);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        String token = userService.loginUser(request);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
