package com.delichops.user.dto;

import com.delichops.user.model.UserType;
import lombok.Data;

@Data
public class UserProfileDTO {
    private String keycloakId;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private UserType userType;
    private String profileImageUrl;
}

