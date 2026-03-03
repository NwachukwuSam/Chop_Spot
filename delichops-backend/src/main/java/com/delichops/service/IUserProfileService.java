package com.delichops.service;

import com.delichops.dto.UserProfileDTO;
import com.delichops.model.UserProfile;


public interface IUserProfileService {
    
    UserProfile getOrCreateProfile(String keycloakId, String username, String email);
    
    UserProfile updateProfile(String keycloakId, UserProfileDTO dto);
    
    UserProfile getProfileByKeycloakId(String keycloakId);
    
    void deleteProfile(String keycloakId);
}
