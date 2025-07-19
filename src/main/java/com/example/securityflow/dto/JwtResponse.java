package com.example.securityflow.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtResponse {
    private final String token;

    public static JwtResponse of(String token) {
        return JwtResponse.builder()
                          .token(token)
                          .build();
    }
}