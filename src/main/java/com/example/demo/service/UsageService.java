package com.example.demo.service;

import com.example.demo.entity.Subscription;
import com.example.demo.entity.UsageRecord;
import com.example.demo.exception.QuotaExceededException; // Import custom exception
import com.example.demo.repository.SubscriptionRepository;
import com.example.demo.repository.UsageRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsageService {

    private final UsageRecordRepository usageRecordRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public UsageRecord trackUsage(Long tenantId, String metricName, Long quantity) {
        // 1. Check if tenant has an active subscription
        Subscription subscription = subscriptionRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new RuntimeException("No active subscription found for tenant ID: " + tenantId));

        if (!"ACTIVE".equals(subscription.getStatus())) {
            throw new RuntimeException("Subscription is not active. Status: " + subscription.getStatus());
        }

        // 2. Calculate current total usage for this tenant
        List<UsageRecord> records = usageRecordRepository.findByTenantId(tenantId);
        long totalUsed = records.stream().mapToLong(UsageRecord::getQuantity).sum();

        // 3. Enforce Quota Limit using custom exception
        if (totalUsed + quantity > subscription.getPlan().getApiLimit()) {
            throw new QuotaExceededException("Quota exceeded! Plan limit is " + subscription.getPlan().getApiLimit() + ". Current usage: " + totalUsed);
        }

        // 4. Save Usage Record
        UsageRecord record = UsageRecord.builder()
                .tenantId(tenantId)
                .metricName(metricName)
                .quantity(quantity)
                .build();

        return usageRecordRepository.save(record);
    }
}