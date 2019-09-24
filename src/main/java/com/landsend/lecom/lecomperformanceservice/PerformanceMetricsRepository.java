package com.landsend.lecom.lecomperformanceservice;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PerformanceMetricsRepository extends MongoRepository<PerformanceMetrics, String> {
    List<PerformanceMetrics> findByUrlContaining(String url);
    List<PerformanceMetrics> findByRunTimeGreaterThan(LocalDateTime time);
}
