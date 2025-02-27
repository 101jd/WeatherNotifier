package org._jd.domain.weatherobj.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherError {

    @JsonProperty(value = "code")
    private int code;
    @JsonProperty(value = "type")
    private String type;
    @JsonProperty(value = "info")
    private String info;
}
