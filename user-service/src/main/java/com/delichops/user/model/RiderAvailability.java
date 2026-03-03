package com.delichops.user.model;

/**
 * Rider availability status for accepting deliveries
 */
public enum RiderAvailability {
    ONLINE,     // Available for deliveries
    OFFLINE,    // Not available
    BUSY        // Currently on a delivery
}
