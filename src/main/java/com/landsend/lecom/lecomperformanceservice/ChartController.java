package com.landsend.lecom.lecomperformanceservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller()
@Slf4j
public class ChartController {

    @Autowired
    private PerformanceMetricsRepository repository;

    @GetMapping("/chart")
    public String chart(@RequestParam(value = "baseUrl", required = false) String baseUrl, Model model) {
        model.addAttribute("urls", getUrls());
        if (baseUrl == null) {
            model.addAttribute("data", null);
        } else {
            List<PerformanceMetrics> result = repository.findByUrlContaining(baseUrl);
            model.addAttribute("data", result);
        }
        model.addAttribute("baseUrl", baseUrl);
        return "chart";
    }

    @GetMapping("/report")
    public String getHtmlData(@RequestParam(value = "id") String id) throws IOException {
        Optional<PerformanceMetrics> repositoryResponse = repository.findById(id);
        if (repositoryResponse.isPresent()) {
            String html = URLDecoder.decode(repositoryResponse.get().getHtml(), StandardCharsets.UTF_8.name());
            String reportFolder = new File("reports").getAbsolutePath();
            Path path = Paths.get(Paths.get(reportFolder) + "/" + id + ".html");
            Files.write(path, html.getBytes());
            return "reports/report.html";
        } else {
            return "reports/report.html";
        }
    }



    @GetMapping("/chart-all")
    public String chartall(Model model) {
        List<PerformanceMetrics> result = repository.findAll();
        Map<String, List<PerformanceMetrics>> resultGroupByBaseUrl = result
                .stream()
                .collect(Collectors.groupingBy(PerformanceMetrics::getBaseUrl));
        model.addAttribute("data", resultGroupByBaseUrl);
        return "chart-all";
    }

    private String[] getUrls() {
        String[] urls = new String[3];
        urls[0] = "";
        urls[1] = "le-dev-b.landsend.com";
        urls[2] = "le-int-c.landsend.com";
        return urls;
    }
}
