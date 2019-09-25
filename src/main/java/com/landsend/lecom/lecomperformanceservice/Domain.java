package com.landsend.lecom.lecomperformanceservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Domain {
    private String name;
    private String value;
    private Boolean checked;
}
