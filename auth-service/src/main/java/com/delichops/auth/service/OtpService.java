package com.delichops.auth.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {
    
    private final Map<String, OtpData> otpStore = new ConcurrentHashMap<>();
    private final Random random = new Random();
    
    // OTP expires after 10 minutes
    private static final int OTP_EXPIRY_MINUTES = 10;
    
    public String generateOtp(String email) {
        // Generate 4-digit OTP
        String otp = String.format("%04d", random.nextInt(10000));
        
        OtpData otpData = new OtpData(otp, LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
        otpStore.put(email.toLowerCase(), otpData);
        
        return otp;
    }
    
    public boolean verifyOtp(String email, String otp) {
        OtpData otpData = otpStore.get(email.toLowerCase());
        
        if (otpData == null) {
            return false;
        }
        
        // Check if OTP is expired
        if (LocalDateTime.now().isAfter(otpData.expiryTime)) {
            otpStore.remove(email.toLowerCase());
            return false;
        }
        
        // Check if OTP matches
        return otpData.otp.equals(otp);
    }
    
    public void removeOtp(String email) {
        otpStore.remove(email.toLowerCase());
    }
    
    private static class OtpData {
        String otp;
        LocalDateTime expiryTime;
        
        OtpData(String otp, LocalDateTime expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }
    }
}
