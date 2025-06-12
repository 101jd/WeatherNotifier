package org._jd.domain.notes;

import lombok.Data;
import org._jd.domain.weatherobj.inter.Notes;

@Data
public class NoneNote extends Note implements Notes {

    public NoneNote(String email, String location){
        this.email = email;
        this.note = "There is no incidents in " + location;
    }
}
