package com.landsend.lecom.lecomperformanceservice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
    private String id;
    private String url;
    private String baseUrl;
    private String urlType;
    private Integer firstContentfulPaint;
    private Integer interactive;
    private Integer speedIndex;
    @JsonFormat(timezone = "CST")
    @Indexed
    private LocalDateTime runTime;
}
