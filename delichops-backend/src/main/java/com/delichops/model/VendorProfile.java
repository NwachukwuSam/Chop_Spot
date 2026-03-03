package com.delichops.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Document(collection = "vendor_profiles")
@Data
public class VendorProfile {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String keycloakId;
    
    private String restaurantName;
    private String restaurantDescription;
    private String restaurantAddress;
    private String restaurantPhone;
    private String restaurantEmail;
    
    private String businessLicenseNumber;
    private String taxId;
    
    private VendorStatus status = VendorStatus.PENDING;
    
    private Double rating = 0.0;
    private Integer totalOrders = 0;
    
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
