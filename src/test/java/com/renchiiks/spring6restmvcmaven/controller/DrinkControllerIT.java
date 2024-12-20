package com.renchiiks.spring6restmvcmaven.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renchiiks.spring6restmvcmaven.entities.Drink;
import com.renchiiks.spring6restmvcmaven.mappers.DrinkMapper;
import com.renchiiks.spring6restmvcmaven.model.DrinkDTO;
import com.renchiiks.spring6restmvcmaven.repositories.DrinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class DrinkControllerIT {
    @Autowired
    DrinkController drinkController;
    @Autowired
    DrinkRepository drinkRepository;

    @Autowired
    DrinkMapper drinkMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void testGetDrinksByName() throws Exception {
        mockMvc.perform(get(DrinkController.DRINK_PATH_ALL)
                .queryParam("drinkName", "Water"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));
    }
    @Test
    void testPatchDrinkNotFound() {
        assertThrows(NotFoundException.class, () -> drinkController.patchDrink(UUID.randomUUID(), DrinkDTO.builder().build()));
    }

    @Test
    void testPatchDrinkBadName() throws Exception {
        Drink drink = drinkRepository.findAll().getFirst();

        Map<String, Object> drinkMap = new HashMap<>();
        drinkMap.put("drinkName", "Waterqwertyuioppasdfghjklzxcvbnm qwertyuiosdfghjkxcvbnmqwertyuiopsdfgWaterqwertyuioppasdfghjklzxcvbnm qwertyuiosdfghjkxcvbnmqwertyuiopsdfg");

         mockMvc.perform(MockMvcRequestBuilders.patch(DrinkController.DRINK_PATH_UUID, drink.getUUID())
                                                                 .accept(MediaType.APPLICATION_JSON)
                                                                 .contentType(MediaType.APPLICATION_JSON)
                                                                 .content(objectMapper.writeValueAsString(drinkMap)))
                                  .andExpect(status().isBadRequest());


    }


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
        List<DrinkDTO> drinks = drinkController.getAllDrinks(null);
        assertThat(drinks.size()).isGreaterThan(100);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyGetAllDrinks() {
        drinkRepository.deleteAll();
        List<DrinkDTO> drinks = drinkController.getAllDrinks(null);
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