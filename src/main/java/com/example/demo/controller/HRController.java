package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String getEmployeeWelcomeMessage(HttpServletRequest request) {
	    // Extract the JWT token from the Authorization header
	    String authorizationHeader = request.getHeader("Authorization");
	    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
	        return "Invalid token!";
	    }
	    String token = authorizationHeader.replace("Bearer ", "");

	    // Extract the name from the token using JWTService
	    String name = jwt.extractClaim(token, claims -> claims.get("name", String.class));

	    // Return a welcome message
	    return "Welcome " + name;
	}
}
