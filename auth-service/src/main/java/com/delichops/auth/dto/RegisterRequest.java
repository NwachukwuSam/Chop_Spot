package com.delichops.auth.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class RegisterRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;
    
    // Optional fields - will be auto-generated if not provided
    private String username;
    private String firstName;
    private String lastName;
    
    // Default to CUSTOMER role
    private String userType = "CUSTOMER";
}
