package com.landsend.lecom.lecomperformanceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LecomPerformanceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LecomPerformanceServiceApplication.class, args);
    }

}
