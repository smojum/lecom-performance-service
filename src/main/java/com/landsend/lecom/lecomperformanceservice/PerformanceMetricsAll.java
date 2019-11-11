package com.landsend.lecom.lecomperformanceservice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PerformanceMetricsAll {
    private String url;
    private String baseUrl;
    private String urlType;
    private Integer firstContentfulPaint;
    private Integer interactive;
    private Integer speedIndex;
    @JsonFormat(timezone = "CST")
    private LocalDateTime runTime;
    private String html;
}
