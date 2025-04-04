package com.example.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.ForgotPasswordRequest;
import com.example.demo.model.OtpToken;
import com.example.demo.model.Users;
import com.example.demo.repo.OtpTokenRepository;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.EmailService;
import com.example.demo.service.JWTService;

import jakarta.annotation.PostConstruct;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/auth")
public class ForgotPasswordController {

    @Autowired
    private OtpTokenRepository userRepository;
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OtpTokenRepository otpTokenRepository;

    @Autowired
    private EmailService emailService;
    
    @Autowired 
    private JWTService jwt;

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        Users user = userRepo.findByEmail(request.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email not found!"));
        }

        // Generate OTP (6-digit code)
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Store OTP with expiration time (5 minutes)
        OtpToken otpToken = new OtpToken(user.getId(), user.getEmail(), otp, LocalDateTime.now().plusMinutes(5));
        otpTokenRepository.save(otpToken);

        // Send OTP email
        emailService.sendOtpEmail(user.getEmail(), otp);

        // Generate short-lived token
        String token = jwt.generateShortLivedToken(otpToken.getEmail(), 10);

        // Create a response map
        Map<String, String> response = new HashMap<>();
       
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

}
