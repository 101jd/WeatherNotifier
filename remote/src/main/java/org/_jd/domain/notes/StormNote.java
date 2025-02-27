package org._jd.domain.notes;

import lombok.Data;
import org._jd.domain.weatherobj.inter.Notes;

@Data
public class StormNote implements Notes {

    private String note;
    public StormNote(double wind_speed, String city) {
        this.note = "There is wind + " + wind_speed + "m/s in " + city;
    }

}
