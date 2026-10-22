package com.hospitall.client.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class AuthService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String authUrl = "http://localhost:8080/api/auth";

    public boolean register(String username, String password) {
        try {
            restTemplate.postForEntity(authUrl + "/register", Map.of("username", username, "password", password), String.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean login(String username, String password) {
        try {
            restTemplate.postForEntity(authUrl + "/login", Map.of("username", username, "password", password), String.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
