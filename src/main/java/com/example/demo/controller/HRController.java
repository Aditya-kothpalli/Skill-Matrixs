package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.JWTService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
public class HRController {

	@Autowired
	private JWTService jwt;
	
	@GetMapping("/hr")
	public ResponseEntity<Map<String, String>> getEmployeeWelcomeMessage(HttpServletRequest request) {
	    // Extract JWT from Authorization header
	    String authorizationHeader = request.getHeader("Authorization");
	    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
	        Map<String, String> error = new HashMap<>();
	        error.put("error", "Invalid token!");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	    }

	    String token = authorizationHeader.replace("Bearer ", "");

	    // Extract name from token
	    String name = jwt.extractClaim(token, claims -> claims.get("name", String.class));

	    // Create response map
	    Map<String, String> response = new HashMap<>();
	    
	    response.put("name", name);

	    return ResponseEntity.ok(response);
	}

}
