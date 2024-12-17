package com.renchiiks.spring6restmvcmaven.controller;

import com.renchiiks.spring6restmvcmaven.entities.Drink;
import com.renchiiks.spring6restmvcmaven.model.DrinkDTO;
import com.renchiiks.spring6restmvcmaven.repositories.DrinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DrinkControllerIT {
    @Autowired
    DrinkController drinkController;
    @Autowired
    DrinkRepository drinkRepository;

    @Test
    void testGetAllDrinks() {
        List<DrinkDTO> drinks = drinkController.getAllDrinks();
        assertThat(drinks.size()).isEqualTo(3);
    }
    @Rollback
    @Transactional
    @Test
    void testEmptyGetAllDrinks() {
        drinkRepository.deleteAll();
        List<DrinkDTO> drinks = drinkController.getAllDrinks();
        assertThat(drinks.size()).isEqualTo(0);
    }

    @Test
    void testGetDrinkByUUID() {
        Drink drink = drinkRepository.findAll().getFirst();
        DrinkDTO drinkDTO = drinkController.getDrinkByUUID(drink.getUUID());

        assertThat(drinkDTO).isNotNull();
    }

    @Test
    void testGetDrinkByUUIDNotFound() {

        assertThrows(NotFoundException.class, () -> drinkController.getDrinkByUUID(UUID.randomUUID()));

    }
}