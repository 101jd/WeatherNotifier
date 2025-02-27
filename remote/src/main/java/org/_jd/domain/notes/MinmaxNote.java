package org._jd.domain.notes;

import lombok.Data;
import lombok.NoArgsConstructor;
import org._jd.domain.weatherobj.inter.Notes;

@Data
@NoArgsConstructor
public class MinmaxNote implements Notes {
    private String note;

    public MinmaxNote(int difference, String direction, String city, String bound) {
        this.note = "There is " + difference + " dergeese " + direction + " in " + city + " than " + bound;
    }
}
