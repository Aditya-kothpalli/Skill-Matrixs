package com.example.demo.controller;

import com.example.demo.DTO.SkillAssessmentDTO;
import com.example.demo.DTO.UserDetailsDTO;
import com.example.demo.model.SkillAssessment;
import com.example.demo.model.UserProfile;
import com.example.demo.service.SkillAssessmentService;
import com.example.demo.service.UserProfileService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private UserProfileService profileService;

    

    // Submit user profile details
    @PostMapping("/profile")
    public ResponseEntity<Map<String, String>> addProfile(@RequestBody UserDetailsDTO dto) {
    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	    String email = auth.getName(); 
           return profileService.saveUserProfile(email, dto);
    }

    
    
}
