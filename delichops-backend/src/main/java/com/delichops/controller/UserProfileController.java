package com.delichops.controller;

import com.delichops.dto.UserProfileDTO;
import com.delichops.model.UserProfile;
import com.delichops.service.IUserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final IUserProfileService userProfileService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getProfile(@AuthenticationPrincipal Jwt jwt) {
        String keycloakId = jwt.getSubject();
        String username = jwt.getClaim("preferred_username");
        String email = jwt.getClaim("email");
        
        UserProfile profile = userProfileService.getOrCreateProfile(keycloakId, username, email);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfile> updateProfile(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody UserProfileDTO dto) {
        String keycloakId = jwt.getSubject();
        UserProfile updated = userProfileService.updateProfile(keycloakId, dto);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteProfile(@AuthenticationPrincipal Jwt jwt) {
        String keycloakId = jwt.getSubject();
        userProfileService.deleteProfile(keycloakId);
        return ResponseEntity.noContent().build();
    }
}

