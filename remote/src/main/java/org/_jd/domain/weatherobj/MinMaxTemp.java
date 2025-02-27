package org._jd.domain.weatherobj;

import lombok.Data;

import java.util.UUID;

@Data
public class MinMaxTemp {
    private String location;
    private int min;
    private int max;
}
