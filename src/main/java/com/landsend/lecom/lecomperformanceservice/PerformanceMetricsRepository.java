package com.landsend.lecom.lecomperformanceservice;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PerformanceMetricsRepository extends MongoRepository<PerformanceMetrics, String> {
    List<PerformanceMetrics> findByUrlContaining(String url);
}
