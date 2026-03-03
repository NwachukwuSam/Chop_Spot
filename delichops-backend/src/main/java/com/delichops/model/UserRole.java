package com.delichops.model;

/**
 * User roles in the system
 * These should match the roles configured in Keycloak
 */
public enum UserRole {
    CUSTOMER,   // Regular users who order food
    VENDOR,     // Restaurant owners
    RIDER,      // Delivery agents
    ADMIN       // Platform administrators
}
