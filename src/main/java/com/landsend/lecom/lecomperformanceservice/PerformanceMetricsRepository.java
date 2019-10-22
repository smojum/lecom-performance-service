package com.landsend.lecom.lecomperformanceservice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PerformanceMetricsRepository extends MongoRepository<PerformanceMetrics, String> {
    @Query("{runTime : {$gte: ?0}}, { url: 1, baseUrl: 1, urlType: 1, firstContentfulPaint: 1, interactive: 1, speedIndex: 1, runTime: 1 }")
    List<PerformanceMetrics> findByRunTimeGte(LocalDateTime time);

    @Query("{runTime : {$lte: ?0}}, { url: 1, baseUrl: 1, urlType: 1, firstContentfulPaint: 1, interactive: 1, speedIndex: 1, runTime: 1 }")
    List<PerformanceMetrics> findByRunTimeLte(LocalDateTime time);

}
