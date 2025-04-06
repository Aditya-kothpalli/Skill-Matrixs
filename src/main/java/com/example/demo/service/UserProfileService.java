package com.example.demo.service;


import com.example.demo.DTO.UserDetailsDTO;
import com.example.demo.model.UserProfile;
import com.example.demo.model.Users;
import com.example.demo.repo.UserProfileRepository;
import com.example.demo.repo.UserRepo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    @Autowired
    private UserRepo userRepository;
    
    @Autowired 
    private JWTService jwt;

    @Autowired
    private UserProfileRepository userProfileRepository;

    public  ResponseEntity<Map<String, String>> saveUserProfile(String email, UserDetailsDTO dto) {
    	  Map<String, String> response = new HashMap<>();
        Users user = userRepository.findByEmail(email);

        UserProfile profile = new UserProfile();
        profile.setEducation(dto.getEducation());
        profile.setExperience(dto.getExperience());
        profile.setSummary(dto.getProfileSummary());
        profile.setUser(user);
        profile.setProfileSubmitted(true);

         userProfileRepository.save(profile);
         response.put("token", jwt.generateToken(user.getEmail(), user.getUserType()));
         return ResponseEntity.ok(response);
    }
}
