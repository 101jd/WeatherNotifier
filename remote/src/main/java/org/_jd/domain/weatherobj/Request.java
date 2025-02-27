package org._jd.domain.weatherobj;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Request {
        @JsonProperty(value = "type", required = false, defaultValue = "null")
        private String type;
        @JsonProperty(value = "query", required = false, defaultValue = "null")
        private String query;
        @JsonProperty(value = "language", required = false, defaultValue = "null")
        private String language;
        @JsonProperty(value = "unit", required = false, defaultValue = "null")
        private String unit;
}
