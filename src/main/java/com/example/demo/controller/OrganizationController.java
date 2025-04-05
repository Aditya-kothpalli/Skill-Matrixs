package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.JWTService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
public class OrganizationController {
	
	@Autowired
	private JWTService jwt;
	
	@GetMapping("/organization")
	public ResponseEntity<Map<String,String>> getEmployeeWelcomeMessage(HttpServletRequest request) {
	    // Extract the JWT token from the Authorization header
		Map<String, String> response = new HashMap<>();
	    String authorizationHeader = request.getHeader("Authorization");
	    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
	         response.put("IN", "INVALID TOKEN");
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	    }
	    String token = authorizationHeader.replace("Bearer ", "");

	    // Extract the name from the token using JWTService
	    String name = jwt.extractClaim(token, claims -> claims.get("name", String.class));

	    // Return a welcome message
	   
	    response.put("name", name);
	    return ResponseEntity.ok(response);
	}

}
