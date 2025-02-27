package org._jd.domain;

import lombok.Data;

@Data
public class Location {
   protected String name;
   protected String country;
   protected String region;
   protected String lat;
   protected String lon;
   protected String timezone_id;
   protected String localtime;
   protected Long localtime_epoch;
   protected String utc_offset;
}
