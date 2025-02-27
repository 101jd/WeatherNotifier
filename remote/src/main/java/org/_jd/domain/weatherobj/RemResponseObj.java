package org._jd.domain.weatherobj;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org._jd.domain.weatherobj.DTO.WeatherError;

@Data
public class RemResponseObj {

    @JsonProperty(value = "success", required = true, defaultValue = "true")
    private boolean success;
    @JsonProperty(value = "error", required = false, defaultValue = "null")
    private WeatherError error;

    @JsonProperty(value = "request", required = false, defaultValue = "null")
    private Request request;
    @JsonProperty(value = "location", required = false, defaultValue = "null")
    private Location location;
    @JsonProperty(value = "current", required = false, defaultValue = "null")
    private Current current;
}
