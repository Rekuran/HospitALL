package com.hospitall.client.service;

import com.hospitall.shared.PatientDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class PatientService {
    private static final String BASE_URL = "http://localhost:8080/api/patients";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<PatientDTO> getAllPatients() throws Exception {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL)).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new Exception("HTTP " + response.statusCode());
        return objectMapper.readValue(response.body(), new TypeReference<List<PatientDTO>>(){});
    }

    public void addPatient(PatientDTO p) throws Exception {
        String json = objectMapper.writeValueAsString(p);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new Exception("HTTP " + response.statusCode());
    }

    public void updatePatient(PatientDTO p) throws Exception {
        String json = objectMapper.writeValueAsString(p);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/" + p.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new Exception("HTTP " + response.statusCode());
    }

    public void deletePatient(String id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/" + id)).DELETE().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new Exception("HTTP " + response.statusCode());
    }
}
