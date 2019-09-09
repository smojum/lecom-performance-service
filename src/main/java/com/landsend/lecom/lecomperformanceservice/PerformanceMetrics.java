package com.landsend.lecom.lecomperformanceservice;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "metrics")
public class PerformanceMetrics {
    @Id
    @Generated
    private String id;
    private String url;
    private String baseUrl;
    private String urlType;
    private String firstContentfulPaint;
    private String interactive;
    private String speedIndex;
    private LocalDateTime runTime;

}
