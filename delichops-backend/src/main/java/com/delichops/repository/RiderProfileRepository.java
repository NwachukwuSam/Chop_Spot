package com.delichops.repository;

import com.delichops.model.RiderProfile;
import com.delichops.model.RiderStatus;
import com.delichops.model.RiderAvailability;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RiderProfileRepository extends MongoRepository<RiderProfile, String> {
    Optional<RiderProfile> findByKeycloakId(String keycloakId);
    List<RiderProfile> findByStatus(RiderStatus status);
    List<RiderProfile> findByAvailability(RiderAvailability availability);
    List<RiderProfile> findByStatusAndAvailability(RiderStatus status, RiderAvailability availability);
}
