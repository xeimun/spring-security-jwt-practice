package com.example.securityflow.service;

import com.example.securityflow.domain.User;
import com.example.securityflow.dto.LoginRequest;
import com.example.securityflow.dto.RegisterResponse;
import com.example.securityflow.repository.UserRepository;
import com.example.securityflow.security.CustomUserDetails;
import com.example.securityflow.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    public RegisterResponse registerUser(String username, String password, String role) {
        User saved = userRepository.save(
                new User(null, username, passwordEncoder.encode(password), role)
        );

        return new RegisterResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getRole()
        );
    }

    // 로그인
    public String loginUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return jwtTokenProvider.generateToken(authentication);
    }
}
