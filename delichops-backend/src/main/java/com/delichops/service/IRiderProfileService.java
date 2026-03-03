package com.delichops.service;

import com.delichops.model.RiderProfile;
import com.delichops.model.RiderStatus;
import com.delichops.model.RiderAvailability;

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
