package com.delichops.auth.service;

import com.delichops.auth.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import jakarta.ws.rs.core.Response;
import java.util.*;

@Service
public class AuthService {

    private final Keycloak keycloak;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final OtpService otpService;
    private final EmailService emailService;

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public AuthService(Keycloak keycloak, OtpService otpService, EmailService emailService) {
        this.keycloak = keycloak;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.otpService = otpService;
        this.emailService = emailService;
    }

    public Map<String, String> register(RegisterRequest request) {
        try {
            // Validate passwords match
            if (!request.getPassword().equals(request.getConfirmPassword())) {
                throw new RuntimeException("Passwords do not match");
            }
            
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();

            // Auto-generate username from email if not provided
            String username = request.getUsername();
            if (username == null || username.trim().isEmpty()) {
                username = request.getEmail().split("@")[0];
            }
            
            // Auto-generate first/last name from email if not provided
            String firstName = request.getFirstName();
            if (firstName == null || firstName.trim().isEmpty()) {
                firstName = username;
            }
            
            String lastName = request.getLastName();
            if (lastName == null || lastName.trim().isEmpty()) {
                lastName = "User";
            }

            // Create user representation
            UserRepresentation user = new UserRepresentation();
            user.setUsername(username);
            user.setEmail(request.getEmail());
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEnabled(true);
            user.setEmailVerified(false);

            // Create password credential
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(request.getPassword());
            credential.setTemporary(false);
            user.setCredentials(Collections.singletonList(credential));

            // Create user
            Response response = usersResource.create(user);
            
            if (response.getStatus() == 201) {
                // Get the created user's ID from location header
                String locationHeader = response.getHeaderString("Location");
                String userId = locationHeader.substring(locationHeader.lastIndexOf('/') + 1);
                
                // Assign role to user
                assignRoleToUser(realmResource, userId, request.getUserType());
                
                return Map.of(
                    "message", "User registered successfully with role: " + request.getUserType(),
                    "email", request.getEmail(),
                    "username", username,
                    "role", request.getUserType()
                );
            } else {
                throw new RuntimeException("Failed to create user: " + response.getStatusInfo());
            }
        } catch (Exception e) {
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }
    
    private void assignRoleToUser(RealmResource realmResource, String userId, String roleName) {
        try {
            // Get the role by name
            var roleRepresentation = realmResource.roles().get(roleName).toRepresentation();
            
            // Assign role to user
            realmResource.users().get(userId).roles().realmLevel()
                    .add(Collections.singletonList(roleRepresentation));
        } catch (Exception e) {
            throw new RuntimeException("Failed to assign role: " + e.getMessage());
        }
    }

    public AuthResponse login(LoginRequest request) {
        try {
            String tokenUrl = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "password");
            body.add("client_id", clientId);
            body.add("client_secret", clientSecret);
            // Support both email and username login
            body.add("username", request.getEmail());
            body.add("password", request.getPassword());

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, entity, Map.class);

            Map<String, Object> responseBody = response.getBody();
            String accessToken = (String) responseBody.get("access_token");
            
            // Decode JWT to extract user info
            Map<String, Object> userInfo = decodeJwtPayload(accessToken);
            
            return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken((String) responseBody.get("refresh_token"))
                .expiresIn((Integer) responseBody.get("expires_in"))
                .tokenType((String) responseBody.get("token_type"))
                .userId((String) userInfo.get("sub"))
                .username((String) userInfo.get("preferred_username"))
                .email((String) userInfo.get("email"))
                .firstName((String) userInfo.get("given_name"))
                .lastName((String) userInfo.get("family_name"))
                .roles(extractRoles(userInfo))
                .build();
        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }
    
    private Map<String, Object> decodeJwtPayload(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                return new HashMap<>();
            }
            
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            return objectMapper.readValue(payload, Map.class);
        } catch (Exception e) {
            // Fallback: return empty map
            return new HashMap<>();
        }
    }
    
    @SuppressWarnings("unchecked")
    private List<String> extractRoles(Map<String, Object> userInfo) {
        try {
            Map<String, Object> realmAccess = (Map<String, Object>) userInfo.get("realm_access");
            if (realmAccess != null && realmAccess.containsKey("roles")) {
                List<String> allRoles = (List<String>) realmAccess.get("roles");
                
                // Filter to only return application-specific roles
                List<String> appRoles = new ArrayList<>();
                Set<String> validRoles = Set.of("CUSTOMER", "VENDOR", "RIDER", "ADMIN");
                
                for (String role : allRoles) {
                    if (validRoles.contains(role)) {
                        appRoles.add(role);
                    }
                }
                
                return appRoles;
            }
        } catch (Exception e) {
            // Ignore
        }
        return new ArrayList<>();
    }
    
    /**
     * Initiate password reset by sending OTP to user's email
     */
    public Map<String, String> forgotPassword(String email) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();
            
            // Search for user by email
            List<UserRepresentation> users = usersResource.search(null, null, null, email, 0, 1);
            
            if (users.isEmpty()) {
                // Don't reveal if email exists or not (security best practice)
                return Map.of(
                    "message", "If the email exists, an OTP has been sent",
                    "email", email
                );
            }
            
            // Generate and store OTP
            String otp = otpService.generateOtp(email);
            
            // Send OTP via email
            emailService.sendOtpEmail(email, otp);
            
            return Map.of(
                "message", "OTP sent to your email",
                "email", email,
                "expiresIn", "10 minutes"
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to process password reset request: " + e.getMessage());
        }
    }
    
    /**
     * Verify OTP
     */
    public Map<String, String> verifyOtp(String email, String otp) {
        boolean isValid = otpService.verifyOtp(email, otp);
        
        if (isValid) {
            return Map.of(
                "message", "OTP verified successfully",
                "email", email,
                "status", "verified"
            );
        } else {
            throw new RuntimeException("Invalid or expired OTP");
        }
    }
    
    /**
     * Reset password after OTP verification
     */
    public Map<String, String> resetPassword(String email, String otp, String newPassword, String confirmPassword) {
        try {
            // Validate passwords match
            if (!newPassword.equals(confirmPassword)) {
                throw new RuntimeException("Passwords do not match");
            }
            
            // Verify OTP
            boolean isValid = otpService.verifyOtp(email, otp);
            if (!isValid) {
                throw new RuntimeException("Invalid or expired OTP");
            }
            
            // Find user by email
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();
            List<UserRepresentation> users = usersResource.search(null, null, null, email, 0, 1);
            
            if (users.isEmpty()) {
                throw new RuntimeException("User not found");
            }
            
            UserRepresentation user = users.get(0);
            
            // Update password
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(newPassword);
            credential.setTemporary(false);
            
            usersResource.get(user.getId()).resetPassword(credential);
            
            // Remove OTP after successful password reset
            otpService.removeOtp(email);
            
            // Send confirmation email
            emailService.sendPasswordResetConfirmation(email);
            
            return Map.of(
                "message", "Password reset successfully",
                "email", email
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset password: " + e.getMessage());
        }
    }
}
