package com.delichops.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;  // Can be email or username
    private String password;
}
