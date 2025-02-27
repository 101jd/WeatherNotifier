package org._jd.domain.weatherobj;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Location {
   @JsonProperty(value = "name", required = false, defaultValue = "null")
   protected String name;
   @JsonProperty(value = "country", required = false, defaultValue = "null")
   protected String country;
   @JsonProperty(value = "region", required = false, defaultValue = "null")
   protected String region;
   @JsonProperty(value = "lat", required = false, defaultValue = "null")
   protected String lat;
   @JsonProperty(value = "lon", required = false, defaultValue = "null")
   protected String lon;
   @JsonProperty(value = "timezone_id", required = false, defaultValue = "null")
   protected String timezone_id;
   @JsonProperty(value = "localtime", required = false, defaultValue = "null")
   protected String localtime;
   @JsonProperty(value = "localtime_epoch", required = false, defaultValue = "null")
   protected Long localtime_epoch;
   @JsonProperty(value = "utc_offset", required = false, defaultValue = "null")
   protected String utc_offset;
}
