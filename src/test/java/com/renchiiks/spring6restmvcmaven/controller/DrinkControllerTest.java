package com.renchiiks.spring6restmvcmaven.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;


@SpringBootTest
class DrinkControllerTest {

    @Autowired
    DrinkController drinkController;

    @Test
    void getDrinkByUUID() {
        System.out.println(drinkController.getDrinkByUUID(UUID.randomUUID()));
    }
}