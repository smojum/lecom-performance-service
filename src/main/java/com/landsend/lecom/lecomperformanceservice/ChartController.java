package com.landsend.lecom.lecomperformanceservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Controller()
@Slf4j
public class ChartController {

    @Autowired
    private PerformanceMetricsRepository repository;

    private List<String> staticDomains = Arrays.asList("www", "origin-m1-www", "origin-m1-www", "le-qas-a", "le-int-c");

    @GetMapping("/chart")
    public String chart(@RequestParam(value = "selectedDays", required = false, defaultValue = "3") Integer selectedDays,
                        @RequestParam(value = "selectedHours", required = false, defaultValue = "0") Integer selectedHours,
                        @RequestParam(value = "domains", required = false) List<String> domains, Model model) {

        if (domains == null) {
            domains = staticDomains;
        }
        model.addAttribute("allDomains", getDomains(domains));
        model.addAttribute("domains", domains);
        model.addAttribute("selectedDays", selectedDays);
        model.addAttribute("selectedHours", selectedHours);
        model.addAttribute("chartData", getChartData(selectedDays, selectedHours, domains));
        return "chart";
    }

    public List<Domain> getDomains(List<String> domains) {
        return staticDomains
                .stream()
                .map(staticDomain -> new Domain(staticDomain, domains.stream().anyMatch(domain -> domain.equals(staticDomain))))
                .collect(Collectors.toList());
    }

    public Map<String, Map<String, Map<String, List<List<String>>>>> getChartData(Integer days, Integer hours, List<String> domains) {
        List<PerformanceMetrics> result = repository.findByRunTimeGte(LocalDateTime.now(Clock.systemUTC()).minusDays(days).minusHours(hours));
        Map<String, Map<String, Map<String, List<List<String>>>>> output = result.stream()
                .filter(performanceMetrics -> domains.contains(performanceMetrics.getBaseUrl().split("\\.")[0]))
                .flatMap(performanceMetrics -> normalizedList(performanceMetrics).stream())
                .collect(
                        groupingBy(PerformanceMetricsNormalized::getUrlType,
                                groupingBy(PerformanceMetricsNormalized::getPerfType,
                                        groupingBy(
                                                performanceMetricsNormalized -> performanceMetricsNormalized.getBaseUrl(),
                                                Collectors.mapping(PerformanceMetricsNormalized::getArray, Collectors.toList())))));
        return output;
    }

    private List<PerformanceMetricsNormalized> normalizedList(PerformanceMetrics performanceMetrics) {
        List<PerformanceMetricsNormalized> performanceMetricsNormalizedList = new ArrayList<>();
        PerformanceMetricsNormalized base = new PerformanceMetricsNormalized();
        base.setId(performanceMetrics.getId());
        base.setBaseUrl(performanceMetrics.getBaseUrl().split("\\.")[0]);
        base.setRunTime(convertUTCtoLocal(performanceMetrics.getRunTime()));
        if ("Other".equals(performanceMetrics.getUrlType())) {
            base.setUrlType("Search");
        } else {
            base.setUrlType(performanceMetrics.getUrlType());
        }
        PerformanceMetricsNormalized fcp = (PerformanceMetricsNormalized) base.clone();
        fcp.setPerfType("FCP");
        fcp.setPerfValue(performanceMetrics.getFirstContentfulPaint());
        PerformanceMetricsNormalized interactive = (PerformanceMetricsNormalized) base.clone();
        interactive.setPerfType("Interactive");
        interactive.setPerfValue(performanceMetrics.getInteractive());
        PerformanceMetricsNormalized si = (PerformanceMetricsNormalized) base.clone();
        si.setPerfType("SI");
        si.setPerfValue(performanceMetrics.getSpeedIndex());
        performanceMetricsNormalizedList.add(fcp);
        performanceMetricsNormalizedList.add(interactive);
        performanceMetricsNormalizedList.add(si);
        return performanceMetricsNormalizedList;
    }

    private Map<String, Map<String, Map<String, List<List<String>>>>> getChartDataConst() {
        List<String> datapoint1_1 = new ArrayList<>();
        datapoint1_1.add("2019-09-19T16:44:18.473");
        datapoint1_1.add("156");
        datapoint1_1.add(UUID.randomUUID().toString());
        List<String> datapoint1_2 = new ArrayList<>();
        datapoint1_2.add("2019-09-19T16:54:18.473");
        datapoint1_2.add("166");
        datapoint1_2.add(UUID.randomUUID().toString());
        List<String> datapoint1_3 = new ArrayList<>();
        datapoint1_3.add("2019-09-19T17:04:18.473");
        datapoint1_3.add("176");
        datapoint1_3.add(UUID.randomUUID().toString());
        List<List<String>> datapoint_1 = new ArrayList<>();
        datapoint_1.add(datapoint1_1);
        datapoint_1.add(datapoint1_2);
        datapoint_1.add(datapoint1_3);
        List<String> datapoint2_1 = new ArrayList<>();
        datapoint2_1.add("2019-09-19T16:45:18.473");
        datapoint2_1.add("159");
        datapoint2_1.add(UUID.randomUUID().toString());
        List<String> datapoint2_2 = new ArrayList<>();
        datapoint2_2.add("2019-09-19T16:55:18.473");
        datapoint2_2.add("169");
        datapoint2_2.add(UUID.randomUUID().toString());
        List<String> datapoint2_3 = new ArrayList<>();
        datapoint2_3.add("2019-09-19T17:05:18.473");
        datapoint2_3.add("179");
        datapoint2_3.add(UUID.randomUUID().toString());
        List<List<String>> datapoint_2 = new ArrayList<>();
        datapoint_2.add(datapoint2_1);
        datapoint_2.add(datapoint2_2);
        datapoint_2.add(datapoint2_3);

        List<String> datapoint3_1 = new ArrayList<>();
        datapoint3_1.add("2019-09-19T16:45:18.473");
        datapoint3_1.add("154");
        datapoint3_1.add(UUID.randomUUID().toString());
        List<String> datapoint3_2 = new ArrayList<>();
        datapoint3_2.add("2019-09-19T16:55:18.473");
        datapoint3_2.add("164");
        datapoint3_2.add(UUID.randomUUID().toString());
        List<String> datapoint3_3 = new ArrayList<>();
        datapoint3_3.add("2019-09-19T17:05:18.473");
        datapoint3_3.add("174");
        datapoint3_3.add(UUID.randomUUID().toString());
        List<List<String>> datapoint_3 = new ArrayList<>();
        datapoint_3.add(datapoint3_1);
        datapoint_3.add(datapoint3_2);
        datapoint_3.add(datapoint3_3);

        Map<String, List<List<String>>> group = new HashMap<>();

        group.put("le-int-b.landsend.com", datapoint_1);
        group.put("le-int-c.landsend.com", datapoint_2);
        group.put("le-qas-a.landsend.com", datapoint_3);

        Map<String, Map<String, List<List<String>>>> type = new HashMap();
        Map<String, List<List<String>>> group2 = (Map<String, List<List<String>>>) ((HashMap) group).clone();
        Map<String, List<List<String>>> group3 = (Map<String, List<List<String>>>) ((HashMap) group).clone();
        type.put("FCP", group);
        type.put("Interactive", group2);
        type.put("SI", group3);
        Map<String, Map<String, Map<String, List<List<String>>>>> allMap = new HashMap<>();
        Map<String, Map<String, List<List<String>>>> type2 = (Map<String, Map<String, List<List<String>>>>) ((HashMap) type).clone();
        Map<String, Map<String, List<List<String>>>> type3 = (Map<String, Map<String, List<List<String>>>>) ((HashMap) type).clone();
        allMap.put("PDP", type);
        allMap.put("PLP", type2);
        allMap.put("Search", type3);
        return allMap;
    }

    private LocalDateTime convertUTCtoLocal(LocalDateTime utc) {
        return utc.plus(TimeZone.getDefault().getRawOffset() + TimeZone.getDefault().getDSTSavings(), ChronoField.MILLI_OF_DAY.getBaseUnit());
    }

    private LocalDateTime convertLocaltoUTC(LocalDateTime local) {
        return local.minus(TimeZone.getDefault().getRawOffset() + TimeZone.getDefault().getDSTSavings(), ChronoField.MILLI_OF_DAY.getBaseUnit());
    }
}
