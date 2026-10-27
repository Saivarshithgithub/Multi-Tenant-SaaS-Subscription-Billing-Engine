package com.example.demo.service;

import com.example.demo.entity.Subscription;
import com.example.demo.entity.SubscriptionPlan;
import com.example.demo.entity.Tenant;
import com.example.demo.repository.SubscriptionPlanRepository;
import com.example.demo.repository.SubscriptionRepository;
import com.example.demo.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;
    private final SubscriptionPlanRepository planRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public Tenant registerTenant(String name, String subdomain, String planName) {
        // 1. Save Tenant
        Tenant tenant = Tenant.builder()
                .name(name)
                .subdomain(subdomain)
                .status("ACTIVE")
                .build();
        Tenant savedTenant = tenantRepository.save(tenant);

        // 2. Find or create default plan if it doesn't exist
        SubscriptionPlan plan = planRepository.findByPlanName(planName)
                .orElseGet(() -> planRepository.save(
                        SubscriptionPlan.builder()
                                .planName(planName)
                                .monthlyPrice(new BigDecimal("29.99"))
                                .apiLimit(1000) // 1000 requests limit
                                .build()
                ));

        // 3. Assign Subscription to Tenant
        Subscription subscription = Subscription.builder()
                .tenant(savedTenant)
                .plan(plan)
                .status("ACTIVE")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .build();
        
        subscriptionRepository.save(subscription);

        return savedTenant;
    }
}