package com.renchiiks.spring6restmvcmaven.entities;

import com.renchiiks.spring6restmvcmaven.model.DrinkStyle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Drink {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID UUID;
    @Version
    private Integer version;
    private String drinkName;
    private DrinkStyle drinkStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
