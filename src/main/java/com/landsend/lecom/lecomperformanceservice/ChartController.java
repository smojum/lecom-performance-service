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
import java.util.*;
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
            result.stream()
                    .forEach(performanceMetrics -> performanceMetrics.setHtml(null));
            model.addAttribute("data", result);
        }
        model.addAttribute("constData", getChartData());
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
    public String chartAll(Model model) {
        List<PerformanceMetrics> result = repository.findAll();
        result.stream()
                .forEach(performanceMetrics -> performanceMetrics.setHtml(null));
        Map<String, List<PerformanceMetrics>> resultGroupByBaseUrl = result
                .stream()
                .collect(Collectors.groupingBy(PerformanceMetrics::getBaseUrl));
        model.addAttribute("data", resultGroupByBaseUrl);
        model.addAttribute("constData", getChartData());
        model.addAttribute("urls", getUrls());

        return "chart-all";
    }

    private Map<String, Map<String, Map<String, List<List<String>>>>> getChartData() {
        List<String> datapoint1_1 = new ArrayList<>();
        datapoint1_1.add("2019-09-19T16:44:18.473");
        datapoint1_1.add("156");
        datapoint1_1.add(UUID.randomUUID().toString());
        List<String> datapoint1_2 = new ArrayList<>();
        datapoint1_2.add("2019-09-19T16:54:18.473");
        datapoint1_2.add("166");
        datapoint1_1.add(UUID.randomUUID().toString());
        List<String> datapoint1_3 = new ArrayList<>();
        datapoint1_3.add("2019-09-19T17:04:18.473");
        datapoint1_3.add("176");
        datapoint1_1.add(UUID.randomUUID().toString());
        List<List<String>> datapoint_1 = new ArrayList<>();
        datapoint_1.add(datapoint1_1);
        datapoint_1.add(datapoint1_2);
        datapoint_1.add(datapoint1_3);
        List<String> datapoint2_1 = new ArrayList<>();
        datapoint2_1.add("2019-09-19T16:45:18.473");
        datapoint2_1.add("159");
        datapoint1_1.add(UUID.randomUUID().toString());
        List<String> datapoint2_2 = new ArrayList<>();
        datapoint2_2.add("2019-09-19T16:55:18.473");
        datapoint2_2.add("169");
        datapoint1_1.add(UUID.randomUUID().toString());
        List<String> datapoint2_3 = new ArrayList<>();
        datapoint2_3.add("2019-09-19T17:05:18.473");
        datapoint2_3.add("179");
        datapoint1_1.add(UUID.randomUUID().toString());
        List<List<String>> datapoint_2 = new ArrayList<>();
        datapoint_2.add(datapoint2_1);
        datapoint_2.add(datapoint2_2);
        datapoint_2.add(datapoint2_3);

        Map<String, List<List<String>>> group = new HashMap<>();

        group.put("le-int-b.landsend.com", datapoint_1);
        group.put("le-int-c.landsend.com", datapoint_2);
        Map<String, Map<String, List<List<String>>>> type = new HashMap();
        Map<String, List<List<String>>> group2 = (Map<String, List<List<String>>>) ((HashMap) group).clone();
        Map<String, List<List<String>>> group3 = (Map<String, List<List<String>>>) ((HashMap) group).clone();
        type.put("FCP", group);
        type.put("Interactive", group2);
        type.put("SI", group3);
        Map<String, Map<String, Map<String, List<List<String>>>>> allMap = new HashMap<>();
        Map<String, Map<String, List<List<String>>>> type2 = (Map<String, Map<String, List<List<String>>>>) ((HashMap)type).clone();
        Map<String, Map<String, List<List<String>>>> type3 = (Map<String, Map<String, List<List<String>>>>) ((HashMap)type).clone();
        allMap.put("PDP", type);
        allMap.put("PLP", type2);
        allMap.put("Other", type3);
        return allMap;
    }
    private String[] getUrls() {
        String[] urls = new String[3];
        urls[0] = "";
        urls[1] = "le-dev-b.landsend.com";
        urls[2] = "le-int-c.landsend.com";
        return urls;
    }
}
