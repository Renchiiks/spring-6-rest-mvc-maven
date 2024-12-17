package com.renchiiks.spring6restmvcmaven.controller;

import com.renchiiks.spring6restmvcmaven.entities.Drink;
import com.renchiiks.spring6restmvcmaven.mappers.DrinkMapper;
import com.renchiiks.spring6restmvcmaven.model.DrinkDTO;
import com.renchiiks.spring6restmvcmaven.repositories.DrinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    DrinkMapper drinkMapper;

    @Test
    void testDeleteDrinkNotFound() {
        assertThrows(NotFoundException.class, () -> drinkController.deleteDrink(UUID.randomUUID()));

    }

    @Transactional
    @Rollback
    @Test
    void testDeleteDrink() {
        Drink drink = drinkRepository.findAll().getFirst();
        ResponseEntity response = drinkController.deleteDrink(drink.getUUID());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        assertThat(drinkRepository.findById(drink.getUUID())).isEmpty();

    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () ->
                drinkController.updateDrink(UUID.randomUUID(), DrinkDTO.builder().build()));
    }

    @Transactional
    @Rollback
    @Test
    void testUpdateDrink() {
        Drink drink = drinkRepository.findAll().getFirst();

        DrinkDTO drinkDTO = drinkMapper.drinkToDrinkDTO(drink);
        drinkDTO.setUUID(null);
        drinkDTO.setVersion(null);

        final String name = "UPDATED";
        drinkDTO.setDrinkName(name);

        ResponseEntity responseEntity = drinkController.updateDrink(drink.getUUID(), drinkDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Drink updatedDrink = drinkRepository.findById(drink.getUUID()).get();
        assertThat(updatedDrink.getDrinkName()).isEqualTo(name);

    }

    @Rollback
    @Transactional
    @Test
    void testCreateDrink() {
        DrinkDTO drinkDTO = DrinkDTO.builder().drinkName("test")
                                    .build();
        ResponseEntity response = drinkController.createDrink(drinkDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = response.getHeaders().getLocation().getPath().split("/");

        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Drink drink = drinkRepository.findById(savedUUID).get();//.orNull();

        assertThat(drink).isNotNull();


    }

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