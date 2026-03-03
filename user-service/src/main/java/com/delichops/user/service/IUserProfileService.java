package com.delichops.user.service;

import com.delichops.user.dto.UserProfileDTO;
import com.delichops.user.model.UserProfile;


public interface IUserProfileService {
    
    UserProfile getOrCreateProfile(String keycloakId, String username, String email);
    
    UserProfile updateProfile(String keycloakId, UserProfileDTO dto);
    
    UserProfile getProfileByKeycloakId(String keycloakId);
    
    void deleteProfile(String keycloakId);
}
