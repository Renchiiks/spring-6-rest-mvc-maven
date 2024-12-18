package com.renchiiks.spring6restmvcmaven.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class DrinkDTO {
    private UUID UUID;
    private Integer version;
    @NotNull
    @NotBlank
    private String drinkName;
    @NotNull
    private DrinkStyle drinkStyle;
    @NotNull
    @NotBlank
    private String upc;
    private Integer quantityOnHand;
    @NotNull
    private BigDecimal price;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


}
