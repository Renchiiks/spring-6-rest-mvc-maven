package com.renchiiks.spring6restmvcmaven.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String name;
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID uuid;
    @Version
    private Integer version;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
