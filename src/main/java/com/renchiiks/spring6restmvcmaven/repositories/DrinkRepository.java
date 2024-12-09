package com.renchiiks.spring6restmvcmaven.repositories;

import com.renchiiks.spring6restmvcmaven.entities.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DrinkRepository extends JpaRepository<Drink, UUID> {
}
