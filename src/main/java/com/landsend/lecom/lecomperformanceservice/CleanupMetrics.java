package com.landsend.lecom.lecomperformanceservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
@Slf4j
public class CleanupMetrics {
    @Autowired
    private PerformanceMetricsRepository repository;

    @Value("${cleanup.days}")
    private Integer cleanupDays;

    @Scheduled(fixedRate = 500000)
    public void cleanup() {
        log.info("Cleaning up old data from metrics");
        repository.deleteAll(repository.findByRunTimeLte(LocalDateTime.now(Clock.systemUTC()).minusDays(cleanupDays).minusHours(0)));
        log.info("Cleaned up metrics successfully");
    }
}
