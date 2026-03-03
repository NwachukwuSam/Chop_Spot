package com.delichops.service.impl;

import com.delichops.dto.UserProfileDTO;
import com.delichops.model.UserProfile;
import com.delichops.repository.UserProfileRepository;
import com.delichops.service.IUserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * User Profile Service Implementation
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileServiceImpl implements IUserProfileService {

    private final UserProfileRepository repository;

    @Override
    public UserProfile getOrCreateProfile(String keycloakId, String username, String email) {
        log.info("Getting or creating profile for keycloakId: {}", keycloakId);
        
        return repository.findByKeycloakId(keycloakId)
                .orElseGet(() -> {
                    log.info("Creating new profile for user: {}", username);
                    UserProfile profile = new UserProfile();
                    profile.setKeycloakId(keycloakId);
                    profile.setUsername(username);
                    profile.setEmail(email);
                    profile.onCreate();
                    return repository.save(profile);
                });
    }

    @Override
    public UserProfile updateProfile(String keycloakId, UserProfileDTO dto) {
        log.info("Updating profile for keycloakId: {}", keycloakId);
        
        UserProfile profile = repository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new RuntimeException("Profile not found for keycloakId: " + keycloakId));
        
        if (dto.getPhoneNumber() != null) profile.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getAddress() != null) profile.setAddress(dto.getAddress());
        if (dto.getCity() != null) profile.setCity(dto.getCity());
        if (dto.getState() != null) profile.setState(dto.getState());
        if (dto.getZipCode() != null) profile.setZipCode(dto.getZipCode());
        if (dto.getCountry() != null) profile.setCountry(dto.getCountry());
        if (dto.getProfileImageUrl() != null) profile.setProfileImageUrl(dto.getProfileImageUrl());
        
        profile.onUpdate();
        
        return repository.save(profile);
    }

    @Override
    public UserProfile getProfileByKeycloakId(String keycloakId) {
        log.info("Getting profile by keycloakId: {}", keycloakId);
        return repository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new RuntimeException("Profile not found for keycloakId: " + keycloakId));
    }

    @Override
    public void deleteProfile(String keycloakId) {
        log.info("Deleting profile for keycloakId: {}", keycloakId);
        UserProfile profile = getProfileByKeycloakId(keycloakId);
        repository.delete(profile);
    }
}
