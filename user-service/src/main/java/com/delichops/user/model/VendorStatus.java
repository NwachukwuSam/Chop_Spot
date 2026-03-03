package com.delichops.user.model;

public enum VendorStatus {
    PENDING,    // Waiting for admin approval
    APPROVED,   // Approved and can operate
    REJECTED,   // Application rejected
    SUSPENDED   // Temporarily suspended
}
