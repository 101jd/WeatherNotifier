package org._jd.domain.weatherobj.DTO;

import lombok.Data;
import org._jd.domain.weatherobj.inter.Notes;

@Data
public class NoteDTO implements Notes {

    private final String note = "ERROR";
}
