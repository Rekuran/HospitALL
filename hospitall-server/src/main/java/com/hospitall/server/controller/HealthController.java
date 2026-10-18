package com.hospitall.server.controller;

import com.hospitall.shared.HealthStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public HealthStatus getHealth() {
        return new HealthStatus("UP", "1.0.0-SNAPSHOT");
    }
}
