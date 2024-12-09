package com.renchiiks.spring6restmvcmaven.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class DrinkDTO {
    private UUID UUID;
    private Integer version;
    private String drinkName;
    private DrinkStyle drinkStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


}
