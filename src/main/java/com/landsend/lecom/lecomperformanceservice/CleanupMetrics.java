package com.landsend.lecom.lecomperformanceservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
@Slf4j
public class CleanupMetrics {
    @Autowired
    private PerformanceMetricsRepository repository;

    @Scheduled(fixedRate = 5000)
    public void cleanup() {
        log.info("Cleaning up old data from metrics");
        repository.deleteAll(repository.findByRunTimeLte(LocalDateTime.now(Clock.systemUTC()).minusDays(3).minusHours(0)));
        log.info("Cleaned up metrics successfully");
    }
}
