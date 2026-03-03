package com.delichops.repository;

import com.delichops.model.VendorProfile;
import com.delichops.model.VendorStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorProfileRepository extends MongoRepository<VendorProfile, String> {
    Optional<VendorProfile> findByKeycloakId(String keycloakId);
    List<VendorProfile> findByStatus(VendorStatus status);
    List<VendorProfile> findByRatingGreaterThanEqual(Double rating);
}
