package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.SkillAssessmentDTO;
import com.example.demo.service.SkillAssessmentService;

@RestController
@RequestMapping("/api/student")
public class SkillAssessmentController {

    @Autowired
    private SkillAssessmentService ass;

    @PostMapping("/submit-assessment")
    public ResponseEntity<String> submitAssessment(@RequestBody SkillAssessmentDTO dto) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String email = auth.getName(); 
	    String response = ass.saveAssessment(email, dto);
	    if (response.contains("successfully")) {
	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.badRequest().body(response);
	    }
    }
}
