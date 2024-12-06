package com.renchiiks.spring6restmvcmaven.service;

import com.renchiiks.spring6restmvcmaven.model.Drink;

import java.util.List;
import java.util.UUID;


public interface DrinkService {
    List<Drink> getAllDrinks();

    Drink getDrinkByUUID(UUID UUID);

    void createDrink(Drink drink);
}
