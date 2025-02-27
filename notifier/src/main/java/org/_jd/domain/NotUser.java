package org._jd.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class NotUser {
    private UUID id;
    private String email;
    private NotifyParameter parameter;
    private String city;
}
