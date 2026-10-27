package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "subscription_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String planName; // BASIC, PRO, ENTERPRISE

    @Column(nullable = false)
    private BigDecimal monthlyPrice;

    @Column(nullable = false)
    private Integer apiLimit; // e.g., 1000 requests/month
}