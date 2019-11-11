package com.landsend.lecom.lecomperformanceservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "metrics_html")
public class PerformanceMetricsHtml {
    @Id
    private String id;
    private String html;
}
