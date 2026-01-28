package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "timestamp", Instant.now().toString()
        ));
    }

    @GetMapping("/api/greeting")
    public ResponseEntity<Map<String, String>> greeting(
            @RequestParam(defaultValue = "World") String name) {
        return ResponseEntity.ok(Map.of(
            "message", "Hello, " + name + "!"
        ));
    }

    @GetMapping("/api/info")
    public ResponseEntity<Map<String, String>> info() {
        return ResponseEntity.ok(Map.of(
            "application", "gha-demo",
            "version", getClass().getPackage().getImplementationVersion() != null 
                ? getClass().getPackage().getImplementationVersion() 
                : "development",
            "java", System.getProperty("java.version")
        ));
    }
}
