package com.example.demo.controller;

import com.example.demo.entity.Tenant;
import com.example.demo.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping("/register")
    public ResponseEntity<Tenant> registerTenant(
            @RequestParam String name,
            @RequestParam String subdomain,
            @RequestParam String planName) {
        
        Tenant tenant = tenantService.registerTenant(name, subdomain, planName);
        return ResponseEntity.ok(tenant);
    }
}