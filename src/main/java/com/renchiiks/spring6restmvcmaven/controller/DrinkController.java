package com.renchiiks.spring6restmvcmaven.controller;

import com.renchiiks.spring6restmvcmaven.model.Drink;
import com.renchiiks.spring6restmvcmaven.service.DrinkService;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class DrinkController {

    private final DrinkService drinkService;
    @RequestMapping("/api/v1/{id}")
    public Drink getDrinkByUUID(UUID UUID) {
       log.debug("Getting drink by UUID: {} in DrinkController", UUID);
        return drinkService.getDrinkByUUID(UUID);
    }

    @GetMapping("/api/v1/drinks")
    public List<Drink> getAllDrinks() {
        log.debug("Getting all drinks in DrinkController");
        return drinkService.getAllDrinks();
    }

}
