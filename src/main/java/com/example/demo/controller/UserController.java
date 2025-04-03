package com.example.demo.controller;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Login;
import com.example.demo.model.Users;
import com.example.demo.service.JWTService;
import com.example.demo.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JWTService jwtService;
	
	private BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody Login user) {
	    return userService.verify(user);
	}
	  @PostMapping("/register")
	  public Map<String, String> register(@RequestBody Users user) {
		 
		   
	      Map<String, String> response = new HashMap<>();
	      user.setPassword(encoder.encode(user.getPassword()));
	      
	      
	      Users us = userService.register(user);

	      // Generate JWT token after registration
	      String token = jwtService.generateToken(user.getEmail(),user.getUserType());
	      response.put("message", "User registered successfully");
	      response.put("data", token);
	      return response;
	  }

}
