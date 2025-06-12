package org._jd.domain.notes;

import lombok.Data;
import lombok.NoArgsConstructor;
import org._jd.domain.weatherobj.inter.Notes;

@Data
@NoArgsConstructor
public class MinmaxNote extends Note implements Notes {

    public MinmaxNote(String email, int difference, String direction, String city, String bound) {
        this.email = email;
        this.note = "There is " + difference + " dergeese " + direction + " in " + city + " than " + bound;
    }
}
