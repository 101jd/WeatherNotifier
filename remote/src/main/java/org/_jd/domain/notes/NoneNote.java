package org._jd.domain.notes;

import lombok.Data;
import org._jd.domain.weatherobj.inter.Notes;

@Data
public class NoneNote implements Notes {
    private String note;
    public NoneNote(String location){
        this.note = "There is no incidents in " + location;
    }
}
