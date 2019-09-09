package com.landsend.lecom.lecomperformanceservice;

import java.util.Arrays;
import java.util.TimeZone;

public class TimeZoneTest {
    public static void main(String args[]) {
        Arrays.stream(TimeZone.getAvailableIDs()).forEach(System.out::println);
    }
}
