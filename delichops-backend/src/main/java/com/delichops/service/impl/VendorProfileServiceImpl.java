package com.delichops.service.impl;

import com.delichops.model.VendorProfile;
import com.delichops.model.VendorStatus;
import com.delichops.repository.VendorProfileRepository;
import com.delichops.service.IVendorProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VendorProfileServiceImpl implements IVendorProfileService {

    private final VendorProfileRepository repository;

    @Override
    public VendorProfile createVendorProfile(VendorProfile profile) {
        log.info("Creating vendor profile for keycloakId: {}", profile.getKeycloakId());
        profile.onCreate();
        return repository.save(profile);
    }

    @Override
    public VendorProfile getVendorByKeycloakId(String keycloakId) {
        return repository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
    }

    @Override
    public VendorProfile updateVendorProfile(String keycloakId, VendorProfile profile) {
        VendorProfile existing = getVendorByKeycloakId(keycloakId);
        existing.onUpdate();
        // Update fields...
        return repository.save(existing);
    }

    @Override
    public List<VendorProfile> getVendorsByStatus(VendorStatus status) {
        return repository.findByStatus(status);
    }

    @Override
    public VendorProfile approveVendor(String keycloakId) {
        VendorProfile vendor = getVendorByKeycloakId(keycloakId);
        vendor.setStatus(VendorStatus.APPROVED);
        vendor.setApprovedAt(LocalDateTime.now());
        return repository.save(vendor);
    }

    @Override
    public VendorProfile rejectVendor(String keycloakId) {
        VendorProfile vendor = getVendorByKeycloakId(keycloakId);
        vendor.setStatus(VendorStatus.REJECTED);
        return repository.save(vendor);
    }

    @Override
    public VendorProfile suspendVendor(String keycloakId) {
        VendorProfile vendor = getVendorByKeycloakId(keycloakId);
        vendor.setStatus(VendorStatus.SUSPENDED);
        return repository.save(vendor);
    }
}
