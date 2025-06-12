package org._jd.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org._jd.domain.inter.Notes;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Note {
    private String email;
//    @JsonProperty(value = "note", required = true)
    private String note;

}
