package com.landsend.lecom.lecomperformanceservice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PerformanceMetricsHtmlRepository extends MongoRepository<PerformanceMetricsHtml, String> {
}
