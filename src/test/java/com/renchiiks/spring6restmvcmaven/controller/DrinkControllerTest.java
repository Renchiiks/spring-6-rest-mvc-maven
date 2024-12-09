package com.renchiiks.spring6restmvcmaven.controller;

import com.renchiiks.spring6restmvcmaven.model.Drink;
import com.renchiiks.spring6restmvcmaven.service.DrinkService;
import com.renchiiks.spring6restmvcmaven.service.DrinkServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;


//@SpringBootTest
@WebMvcTest(DrinkController.class)
class DrinkControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    DrinkService drinkService;

    DrinkServiceImpl drinkServiceImpl = new DrinkServiceImpl();

    @Test
    void testGetAllDrinks() throws Exception{
        given(drinkService.getAllDrinks()).willReturn(drinkServiceImpl.getAllDrinks());

        mockMvc.perform(get("/api/v1/drinks/all")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(4)));
    }

    @Test
    void getDrinkByUUID() throws Exception {

        Drink drink = drinkServiceImpl.getAllDrinks().getFirst();
        UUID uuid = drink.getUUID();

        given(drinkService.getDrinkByUUID(uuid)).willReturn(drink);

        mockMvc.perform(get("/api/v1/drinks/{uuid}", uuid)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid", is(uuid.toString())))
                .andExpect(jsonPath("$.drinkName", is(drink.getDrinkName())));

    }
}

