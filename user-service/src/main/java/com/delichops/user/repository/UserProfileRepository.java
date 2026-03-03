package com.delichops.user.repository;

import com.delichops.user.model.UserProfile;
import com.delichops.user.model.UserType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfile, String> {
    Optional<UserProfile> findByKeycloakId(String keycloakId);
    Optional<UserProfile> findByUsername(String username);
    Optional<UserProfile> findByEmail(String email);
    List<UserProfile> findByUserType(UserType userType);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
