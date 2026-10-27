package com.example.demo.controller;

import com.example.demo.entity.UsageRecord;
import com.example.demo.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usage")
@RequiredArgsConstructor
public class UsageController {

    private final UsageService usageService;

    @PostMapping("/track")
    public ResponseEntity<UsageRecord> trackUsage(
            @RequestParam Long tenantId,
            @RequestParam String metricName,
            @RequestParam Long quantity) {
        
        UsageRecord record = usageService.trackUsage(tenantId, metricName, quantity);
        return ResponseEntity.ok(record);
    }
}