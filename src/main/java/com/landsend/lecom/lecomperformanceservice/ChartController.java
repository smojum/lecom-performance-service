package com.landsend.lecom.lecomperformanceservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
@Slf4j
public class ChartController {

    @Autowired
    private PerformanceMetricsRepository repository;
    @GetMapping("/chart")
    public String chart(Model model) {
        model.addAttribute("message", "Testing");
        model.addAttribute("data", repository.findAll());
        return "chart";
    }
}
