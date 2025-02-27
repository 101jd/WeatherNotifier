package org._jd.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class RegUser {
    private UUID id = UUID.randomUUID();
    private String email;
    private NotifyParameter parameter;
    private String city;
}
