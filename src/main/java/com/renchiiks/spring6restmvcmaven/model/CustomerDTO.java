package com.renchiiks.spring6restmvcmaven.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Builder
@Data

public class CustomerDTO {

    private String name;
    private UUID uuid;
    private Integer version;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
