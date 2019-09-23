package com.landsend.lecom.lecomperformanceservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LecomPerformanceServiceApplicationTests {

    @Autowired
    ChartController chartController;
    @Test
    public void testController() {
        chartController.getChartData();
    }

}
