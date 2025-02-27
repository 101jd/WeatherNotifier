package org._jd.domain;

import lombok.Data;

import java.util.List;

@Data
public class Current {
    protected String observation_time;
    protected Integer temperature;
    protected Integer weather_code;
    protected List<String> weather_icons;
    protected List<String> weather_descriptions;
    protected Integer wind_speed;
    protected Integer wind_degree;
    protected String wind_dir;
    protected Integer pressure;
    protected Double precip;
    protected Integer humidity;
    protected Integer cloudcover;
    protected Integer feelslike;
    protected Integer uv_index;
    protected Integer visibility;
    protected String is_day;

















}
