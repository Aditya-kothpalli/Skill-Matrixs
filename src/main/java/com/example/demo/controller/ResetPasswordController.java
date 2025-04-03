package com.example.demo.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.OtpToken;
import com.example.demo.model.ResetPasswordRequest;
import com.example.demo.model.Users;
import com.example.demo.repo.OtpTokenRepository;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.JWTService;

@RestController
@RequestMapping("/auth")
public class ResetPasswordController {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private JWTService jwtUtil;

    private BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();

    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request, 
                                                @RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer "
        }

        String email = jwtUtil.extractEmail(token);

        if (email == null) {
            return ResponseEntity.badRequest().body("Invalid token.");
        }

        Users user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        // Hash the new password before saving
        user.setPassword(encoder.encode(request.getNewPassword())); // Fix: Use request's new password
        userRepository.save(user);

        return ResponseEntity.ok("Password reset successful.");
    }

    }

