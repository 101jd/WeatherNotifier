package org._jd.domain.notes;

import lombok.Data;
import lombok.NoArgsConstructor;
import org._jd.domain.weatherobj.inter.Notes;

@Data
@NoArgsConstructor
public class TempdiffNote extends Note implements Notes {

    public TempdiffNote(String email, int difference, String city, int current) {
        this.email = email;
        this.note = "There is more than " + difference + " degreese difference in " + city + ". Now is " + current;

    }

}
