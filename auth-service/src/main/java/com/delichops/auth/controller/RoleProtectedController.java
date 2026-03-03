package com.delichops.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Example controller demonstrating role-based access control
 */
@RestController
@RequestMapping("/api/auth")
public class RoleProtectedController {

    @GetMapping("/customer/dashboard")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> customerDashboard(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(Map.of(
            "message", "Welcome to Customer Dashboard",
            "user", jwt.getClaim("preferred_username"),
            "role", "CUSTOMER"
        ));
    }

    @GetMapping("/vendor/dashboard")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> vendorDashboard(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(Map.of(
            "message", "Welcome to Vendor Dashboard",
            "user", jwt.getClaim("preferred_username"),
            "role", "VENDOR"
        ));
    }

    @GetMapping("/rider/dashboard")
    @PreAuthorize("hasRole('RIDER')")
    public ResponseEntity<?> riderDashboard(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(Map.of(
            "message", "Welcome to Rider Dashboard",
            "user", jwt.getClaim("preferred_username"),
            "role", "RIDER"
        ));
    }

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminDashboard(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(Map.of(
            "message", "Welcome to Admin Dashboard",
            "user", jwt.getClaim("preferred_username"),
            "role", "ADMIN"
        ));
    }
}
