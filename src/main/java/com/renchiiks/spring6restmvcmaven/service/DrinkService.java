package com.renchiiks.spring6restmvcmaven.service;

import com.renchiiks.spring6restmvcmaven.model.DrinkDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface DrinkService {
    List<DrinkDTO> getAllDrinks();

    Optional<DrinkDTO> getDrinkByUUID(UUID UUID);

    DrinkDTO createDrink(DrinkDTO drink);

    void updateDrink(UUID uuid, DrinkDTO drink);

    void deleteDrink(UUID uuid);

    void patchDrink(UUID uuid, DrinkDTO drink);
}
