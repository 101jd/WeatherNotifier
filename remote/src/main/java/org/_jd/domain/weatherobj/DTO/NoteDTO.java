package org._jd.domain.weatherobj.DTO;

import lombok.Data;
import org._jd.domain.notes.Note;
import org._jd.domain.weatherobj.inter.Notes;

@Data
public class NoteDTO extends Note implements Notes {

    public NoteDTO(String email){
        this.note = "ERROR";
        this.email = email;
    }
}
