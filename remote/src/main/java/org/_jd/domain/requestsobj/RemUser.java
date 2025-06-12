package org._jd.domain.requestsobj;

import lombok.Data;

import java.util.UUID;

@Data
public class RemUser {
    private UUID id;
    private String email;
    private org._jd.domain.requestsobj.NotifyParameter parameter;
    private String city;
}
