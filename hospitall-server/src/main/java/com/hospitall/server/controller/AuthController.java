package com.hospitall.server.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospitall.server.config.JwtAuthenticationFilter;
import com.hospitall.server.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public AuthController(UserService userService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userService = userService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> credentials) {
        if (userService.register(credentials.get("username"), credentials.get("password"))) {
            return ResponseEntity.ok("User registered successfully");
        }
        return ResponseEntity.badRequest().body("Registration failed");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        return userService.findByUsername(credentials.get("username"))
                .filter(u -> userService.authenticate(credentials.get("username"), credentials.get("password")))
                .map(u -> ResponseEntity.ok(jwtAuthenticationFilter.generateToken(u.getUsername(), u.getRole())))
                .orElse(ResponseEntity.status(401).body("Invalid credentials"));
    }
}
