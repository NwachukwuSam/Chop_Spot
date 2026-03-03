package com.delichops.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    /**
     * Send OTP email to user
     * TODO: Replace with actual email service (SendGrid, AWS SES, etc.)
     * For now, just logs the OTP
     */
    public void sendOtpEmail(String email, String otp) {
        // TODO: Implement actual email sending
        // For development, we'll just log it
        logger.info("===========================================");
        logger.info("SENDING OTP EMAIL");
        logger.info("To: {}", email);
        logger.info("OTP: {}", otp);
        logger.info("This OTP will expire in 10 minutes");
        logger.info("===========================================");
        
        // In production, use a real email service:
        // sendGridService.send(email, "Password Reset OTP", "Your OTP is: " + otp);
    }
    
    public void sendPasswordResetConfirmation(String email) {
        logger.info("Password reset successful for: {}", email);
        // TODO: Send confirmation email
    }
}
