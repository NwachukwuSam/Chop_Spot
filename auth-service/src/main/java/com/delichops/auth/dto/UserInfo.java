package com.delichops.auth.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class UserInfo {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles;
}
