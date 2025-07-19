package com.example.securityflow.service;

import com.example.securityflow.domain.User;
import com.example.securityflow.dto.JwtResponse;
import com.example.securityflow.dto.LoginRequest;
import com.example.securityflow.dto.RegisterRequest;
import com.example.securityflow.dto.RegisterResponse;
import com.example.securityflow.repository.UserRepository;
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

    public RegisterResponse registerUser(RegisterRequest request) {
        User newUser = new User(null,
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getRole() == null ? "ROLE_USER" : request.getRole()
        );
        User saved = userRepository.save(newUser);
        return RegisterResponse.of(saved.getId(), saved.getUsername(), saved.getRole());
    }

    public JwtResponse loginUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token = jwtTokenProvider.generateToken(authentication);
        return JwtResponse.of(token);
    }
}
