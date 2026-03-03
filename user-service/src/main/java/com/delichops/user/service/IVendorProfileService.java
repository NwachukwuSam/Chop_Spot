package com.delichops.user.service;

import com.delichops.user.model.VendorProfile;
import com.delichops.user.model.VendorStatus;

import java.util.List;

public interface IVendorProfileService {
    
    VendorProfile createVendorProfile(VendorProfile profile);
    
    VendorProfile getVendorByKeycloakId(String keycloakId);
    
    VendorProfile updateVendorProfile(String keycloakId, VendorProfile profile);
    
    List<VendorProfile> getVendorsByStatus(VendorStatus status);
    
    VendorProfile approveVendor(String keycloakId);
    
    VendorProfile rejectVendor(String keycloakId);
    
    VendorProfile suspendVendor(String keycloakId);
}
