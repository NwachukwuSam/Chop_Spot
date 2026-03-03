package com.delichops.user.service;

import com.delichops.user.model.RiderProfile;
import com.delichops.user.model.RiderStatus;
import com.delichops.user.model.RiderAvailability;

import java.util.List;


public interface IRiderProfileService {
    
    RiderProfile createRiderProfile(RiderProfile profile);
    
    RiderProfile getRiderByKeycloakId(String keycloakId);
    
    RiderProfile updateRiderProfile(String keycloakId, RiderProfile profile);
    
    List<RiderProfile> getRidersByStatus(RiderStatus status);
    
    List<RiderProfile> getAvailableRiders();
    
    RiderProfile approveRider(String keycloakId);
    
    RiderProfile rejectRider(String keycloakId);
    
    RiderProfile updateAvailability(String keycloakId, RiderAvailability availability);
}
