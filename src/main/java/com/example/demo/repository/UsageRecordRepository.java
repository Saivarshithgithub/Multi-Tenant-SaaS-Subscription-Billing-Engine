package com.example.demo.repository;

import com.example.demo.entity.UsageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsageRecordRepository extends JpaRepository<UsageRecord, Long> {
    List<UsageRecord> findByTenantId(Long tenantId);
}