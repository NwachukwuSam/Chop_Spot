package com.delichops.user.model;

/**
 * Rider approval status
 */
public enum RiderStatus {
    PENDING,    // Waiting for admin approval
    APPROVED,   // Approved and can deliver
    REJECTED,   // Application rejected
    SUSPENDED   // Temporarily suspended
}
