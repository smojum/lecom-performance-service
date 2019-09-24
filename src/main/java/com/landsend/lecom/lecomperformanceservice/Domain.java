package com.landsend.lecom.lecomperformanceservice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Domain {
    private String name;
    private String value;
    private Boolean checked;
}
