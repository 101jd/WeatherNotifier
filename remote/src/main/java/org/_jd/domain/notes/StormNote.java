package org._jd.domain.notes;

import lombok.Data;
import org._jd.domain.weatherobj.inter.Notes;

@Data
public class StormNote extends Note implements Notes {

    public StormNote(String email, double wind_speed, String city) {
        this.email = email;
        this.note = "There is wind + " + wind_speed + "m/s in " + city;
    }

}
