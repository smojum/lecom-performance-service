package com.landsend.lecom.lecomperformanceservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
@Slf4j
public class PerformanceMetricsController {
    @Autowired
    private PerformanceMetricsRepository repository;

    @PostMapping("metrics")
    public void addData(@RequestBody PerformanceMetrics performanceMetrics) {
        setBaseUrl(performanceMetrics);
        setUrlType(performanceMetrics);
        log.info(performanceMetrics.toString());
        repository.save(performanceMetrics);
    }

    private void setBaseUrl(PerformanceMetrics metrics) {
        String urlString = metrics.getUrl();
        try {
            URL url = new URL(urlString);
            metrics.setBaseUrl(url.getHost());
        } catch (MalformedURLException e) {
            //let the Base url be null.
        }
    }

    private void setUrlType(PerformanceMetrics metrics) {
        String url = metrics.getUrl();
        if (url.toLowerCase().contains("/shop/")) {
            metrics.setUrlType("PLP");
        } else if (url.toLowerCase().contains("/products/")) {
            metrics.setUrlType("PDP");
        } else if (url.toLowerCase().contains("/products/")) {
            metrics.setUrlType("Search");
        } else {
            metrics.setUrlType("Other");
        }
    }


}
