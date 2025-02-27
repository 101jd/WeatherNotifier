package org._jd.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NotRemResponse {
    @JsonProperty(value = "request", required = false)
    protected Request request;
    @JsonProperty(value = "location", required = false)
    protected Location location;
    @JsonProperty(value = "current", required = false)
    protected Current current;

    @JsonProperty(value = "success", required = true, defaultValue = "true")
    private boolean success;
    @JsonProperty(value = "error", required = false, defaultValue = "null")
    private WeatherError error;
}
