package org._jd.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class CheckKeys {
    private String email;
    private UUID uuid;
}
