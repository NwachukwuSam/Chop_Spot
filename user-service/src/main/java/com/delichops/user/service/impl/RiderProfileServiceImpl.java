package com.delichops.user.service.impl;

import com.delichops.user.model.RiderProfile;
import com.delichops.user.model.RiderStatus;
import com.delichops.user.model.RiderAvailability;
import com.delichops.user.repository.RiderProfileRepository;
import com.delichops.user.service.IRiderProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderProfileServiceImpl implements IRiderProfileService {

    private final RiderProfileRepository repository;

    @Override
    public RiderProfile createRiderProfile(RiderProfile profile) {
        log.info("Creating rider profile for keycloakId: {}", profile.getKeycloakId());
        profile.onCreate();
        return repository.save(profile);
    }

    @Override
    public RiderProfile getRiderByKeycloakId(String keycloakId) {
        return repository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new RuntimeException("Rider not found"));
    }

    @Override
    public RiderProfile updateRiderProfile(String keycloakId, RiderProfile profile) {
        RiderProfile existing = getRiderByKeycloakId(keycloakId);
        existing.onUpdate();
        return repository.save(existing);
    }

    @Override
    public List<RiderProfile> getRidersByStatus(RiderStatus status) {
        return repository.findByStatus(status);
    }

    @Override
    public List<RiderProfile> getAvailableRiders() {
        return repository.findByStatusAndAvailability(
            RiderStatus.APPROVED, 
            RiderAvailability.ONLINE
        );
    }

    @Override
    public RiderProfile approveRider(String keycloakId) {
        RiderProfile rider = getRiderByKeycloakId(keycloakId);
        rider.setStatus(RiderStatus.APPROVED);
        rider.setApprovedAt(LocalDateTime.now());
        return repository.save(rider);
    }

    @Override
    public RiderProfile rejectRider(String keycloakId) {
        RiderProfile rider = getRiderByKeycloakId(keycloakId);
        rider.setStatus(RiderStatus.REJECTED);
        return repository.save(rider);
    }

    @Override
    public RiderProfile updateAvailability(String keycloakId, RiderAvailability availability) {
        RiderProfile rider = getRiderByKeycloakId(keycloakId);
        rider.setAvailability(availability);
        rider.onUpdate();
        return repository.save(rider);
    }
}
