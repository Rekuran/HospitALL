package com.hospitall.server.controller;

import com.hospitall.server.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
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
                .map(u -> ResponseEntity.ok(com.hospitall.server.config.JwtAuthenticationFilter.generateToken(u.getUsername(), u.getRole())))
                .orElse(ResponseEntity.status(401).body("Invalid credentials"));
    }
}
