package org._jd.domain.weatherobj;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.beans.Transient;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class Current {
    @JsonProperty(value = "observation_time", required = false, defaultValue = "null")
    protected String observation_time;
    @JsonProperty(value = "temperature", required = false, defaultValue = "null")
    protected Integer temperature;
    @JsonProperty(value = "weather_code", required = false, defaultValue = "null")
    protected Integer weather_code;
    @JsonProperty(value = "weather_icons", required = false, defaultValue = "null")
    protected List<String> weather_icons;
    @JsonProperty(value = "weather_descriptions", required = false, defaultValue = "null")
    protected List<String> weather_descriptions;
    @JsonProperty(value = "wind_speed", required = false, defaultValue = "null")
    protected Integer wind_speed;
    @JsonProperty(value = "wind_degree", required = false, defaultValue = "null")
    protected Integer wind_degree;
    @JsonProperty(value = "wind_dir", required = false, defaultValue = "null")
    protected String wind_dir;
    @JsonProperty(value = "pressure", required = false, defaultValue = "null")
    protected Integer pressure;
    @JsonProperty(value = "precip", required = false, defaultValue = "null")
    protected Double precip;
    @JsonProperty(value = "humidity", required = false, defaultValue = "null")
    protected Integer humidity;
    @JsonProperty(value = "cloudcover", required = false, defaultValue = "null")
    protected Integer cloudcover;
    @JsonProperty(value = "feelslike", required = false, defaultValue = "null")
    protected Integer feelslike;
    @JsonProperty(value = "uv_index", required = false, defaultValue = "null")
    protected Integer uv_index;
    @JsonProperty(value = "visibility", required = false, defaultValue = "null")
    protected Integer visibility;
    @JsonProperty(value = "is_day", required = false, defaultValue = "null")
    protected String is_day;
















}
