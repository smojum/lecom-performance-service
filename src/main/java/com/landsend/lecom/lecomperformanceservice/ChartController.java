package com.landsend.lecom.lecomperformanceservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller()
@Slf4j
public class ChartController {

    @Autowired
    private PerformanceMetricsRepository repository;
    @GetMapping("/chart")
    public String chart(@RequestParam(value = "baseUrl", required = false) String baseUrl, Model model) {
        model.addAttribute("urls", getUrls());
        if(baseUrl == null) {
            model.addAttribute("data", null);
        } else {
            log.info("Getting data for url: " + baseUrl);
            List<PerformanceMetrics> result = repository.findByUrlContaining(baseUrl);
            log.info("Result: " + result);
            model.addAttribute("data", result);
        }
        model.addAttribute("baseUrl", baseUrl);
        return "chart";
    }
    private String[] getUrls() {
        String[] urls = new String[3];
        urls[0] = "";
        urls[1] = "le-tst-2.landsend.com";
        urls[2] = "le-int-c.landsend.com";
        return urls;
    }
}
