package com.renchiiks.spring6restmvcmaven.repositories;

import com.renchiiks.spring6restmvcmaven.entities.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface DrinkRepository extends JpaRepository<Drink, UUID> {

    List<Drink> findAllByDrinkNameContaining(String name);
}
