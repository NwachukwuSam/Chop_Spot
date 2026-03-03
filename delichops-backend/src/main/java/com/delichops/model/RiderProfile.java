package com.delichops.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.LocalDateTime;

/**
 * Additional profile information for riders (delivery agents)
 */
@Document(collection = "rider_profiles")
@Data
public class RiderProfile {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String keycloakId;
    
    private String vehicleType; // Bike, Motorcycle, Car
    private String vehicleNumber;
    private String driverLicenseNumber;
    
    private RiderStatus status = RiderStatus.PENDING;
    private RiderAvailability availability = RiderAvailability.OFFLINE;
    
    private Double rating = 0.0;
    private Integer totalDeliveries = 0;
    
    // Current location for delivery assignment (MongoDB GeoJSON)
    @Indexed
    private GeoJsonPoint currentLocation;
    
    private LocalDateTime approvedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
