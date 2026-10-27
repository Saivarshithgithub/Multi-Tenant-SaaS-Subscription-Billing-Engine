package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usage_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsageRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tenantId;

    @Column(nullable = false)
    private String metricName; // e.g., "API_CALLS"

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private LocalDateTime recordedAt;

    @PrePersist
    protected void onCreate() {
        this.recordedAt = LocalDateTime.now();
    }
}