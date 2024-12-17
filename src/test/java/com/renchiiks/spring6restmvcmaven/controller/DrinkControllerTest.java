package com.renchiiks.spring6restmvcmaven.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renchiiks.spring6restmvcmaven.model.DrinkDTO;
import com.renchiiks.spring6restmvcmaven.service.DrinkService;
import com.renchiiks.spring6restmvcmaven.service.DrinkServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;


//@SpringBootTest
@WebMvcTest(DrinkController.class)
class DrinkControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    DrinkService drinkService;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
            ArgumentCaptor<UUID> uuidCaptor;
    @Captor
            ArgumentCaptor<DrinkDTO> drinkCaptor;

    DrinkServiceImpl drinkServiceImpl;

    @BeforeEach
    void setUp() {
        drinkServiceImpl = new DrinkServiceImpl();
    }

    @Test void testPatchDrink() throws Exception {
        DrinkDTO drink = drinkServiceImpl.getAllDrinks().getFirst();

        given((drinkService.patchDrink(any(UUID.class), any(DrinkDTO.class))))
                .willReturn(Optional.of(drink));

        mockMvc.perform(MockMvcRequestBuilders.patch(DrinkController.DRINK_PATH_UUID, drink.getUUID())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(drink)))
                .andExpect(status().isNoContent());

        verify(drinkService).patchDrink(uuidCaptor.capture(), drinkCaptor.capture());

        assertThat(drink.getUUID()).isEqualTo(uuidCaptor.getValue());
        assertThat(drink.getVersion()).isEqualTo(drinkCaptor.getValue().getVersion());
    }

    @Test
    void testDeleteDrink() throws Exception {
        DrinkDTO drink = drinkServiceImpl.getAllDrinks().getFirst();

        given(drinkService.deleteDrink(any())).willReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete(DrinkController.DRINK_PATH_UUID, drink.getUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        verify(drinkService).deleteDrink(uuidCaptor.capture());

        assertThat(drink.getUUID()).isEqualTo(uuidCaptor.getValue());
    }

    @Test
    void testUpdateDrink() throws Exception {
        DrinkDTO drink = drinkServiceImpl.getAllDrinks().getFirst();

        given((drinkService.updateDrink(any(), any()))).willReturn(Optional.of(drink));

        mockMvc.perform(MockMvcRequestBuilders.put(DrinkController.DRINK_PATH_UUID, drink.getUUID())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(drink)))
                .andExpect(status().isNoContent());

        verify(drinkService).updateDrink(any(UUID.class), any(DrinkDTO.class));

    }

    @Test
    void testCreateDrink() throws Exception {

        DrinkDTO drink = drinkServiceImpl.getAllDrinks().getFirst();

        drink.setUUID(null);
        drink.setVersion(null);

        given(drinkService.createDrink(any(DrinkDTO.class)))
                .willReturn(drinkServiceImpl.getAllDrinks().get(1));

        mockMvc.perform(post(DrinkController.DRINK_PATH_CREATE)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(drink)))
                                .andExpect(status().isCreated())
                                .andExpect(header().exists("Location"));
    }

    @Test
    void testGetAllDrinks() throws Exception{
        given(drinkService.getAllDrinks()).willReturn(drinkServiceImpl.getAllDrinks());

        mockMvc.perform(get(DrinkController.DRINK_PATH_ALL)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void testGetDrinkByUUIDNotFound() throws Exception {

        given(drinkService.getDrinkByUUID(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(DrinkController.DRINK_PATH_UUID, UUID.randomUUID()))
                                .andExpect(status().isNotFound());
    }

    @Test
    void getDrinkByUUID() throws Exception {

        DrinkDTO drink = drinkServiceImpl.getAllDrinks().getFirst();
        UUID uuid = drink.getUUID();

        given(drinkService.getDrinkByUUID(uuid)).willReturn(Optional.of(drink));

        mockMvc.perform(get(DrinkController.DRINK_PATH_UUID, uuid)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid", is(uuid.toString())))
                .andExpect(jsonPath("$.drinkName", is(drink.getDrinkName())));

    }
}

