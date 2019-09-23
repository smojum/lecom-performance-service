package com.landsend.lecom.lecomperformanceservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PerformanceMetricsNormalized implements Cloneable {
    private String id;
    private String baseUrl;
    private String urlType;
    private String perfType;
    private Integer perfValue;
    private LocalDateTime runTime;

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    public List<String> getArray() {
        List<String> array = new ArrayList<>();
        array.add(runTime.toString());
        array.add(String.valueOf(perfValue));
        array.add(id);
        return array;
    }
}
