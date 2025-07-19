package com.example.securityflow.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterResponse {
    private final Long id;
    private final String username;
    private final String role;

    public static RegisterResponse of(Long id, String username, String role) {
        return RegisterResponse.builder()
                               .id(id)
                               .username(username)
                               .role(role)
                               .build();
    }
}