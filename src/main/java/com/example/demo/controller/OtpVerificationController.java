package com.example.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.OtpToken;
import com.example.demo.model.OtpVerificationRequest;
import com.example.demo.model.Users;
import com.example.demo.repo.OtpTokenRepository;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.JWTService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class OtpVerificationController {

    @Autowired
    private OtpTokenRepository otpTokenRepository;

    @Autowired
    private JWTService jwtService;
    
    
   @Autowired 
   private UserRepo userRepo;

    @PostMapping("/verify-otp")
    public Map<String,String> verifyOtp(@RequestBody OtpVerificationRequest request) {
 	   
	      Map<String, String> response = new HashMap<>();
        OtpToken otpToken = otpTokenRepository.findByOtp(request.getOtp());     
        //Users us= userRepo.findByUsername(request.getEmail());

//////        if (otpToken == null || !otpToken.getOtp().equals(request.getOtp())) {
//////            return ResponseEntity.badRequest().body("Invalid OTP.");
//////        }
//////       
//////        if (otpToken.getExpirationTime().isBefore(LocalDateTime.now())) 
//////            return ResponseEntity.badRequest().body("OTP expired.");
//////        }
//
//        // Generate short-span JWT token (valid for 10 minutes)
//        
        String token = jwtService.generateShortLivedToken(otpToken.getEmail(),10);
	      response.put("message", "otp Verification is Successful");
	      response.put("data", token);
	      return response;

      
    }
}

