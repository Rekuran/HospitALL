package com.hospitall.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class AuthService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String authUrl = "http://localhost:8080/api/auth";
    private String jwtToken;

    public boolean register(String username, String password) {
        try {
            String json = objectMapper.writeValueAsString(Map.of("username", username, "password", password));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(authUrl + "/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean login(String username, String password) {
        try {
            String json = objectMapper.writeValueAsString(Map.of("username", username, "password", password));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(authUrl + "/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                this.jwtToken = response.body();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void logout() {
        this.jwtToken = null;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
