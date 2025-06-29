package com.runtimerebels.authservice.dtos;

import lombok.Data;

@Data
public class UserRegistrationResponse {
    private String userId;
    private String username;
    private String email;
    private String token;
}
