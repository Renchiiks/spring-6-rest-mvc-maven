package com.renchiiks.spring6restmvcmaven.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.renchiiks.spring6restmvcmaven.entities.DrinkCSVRecord;
import com.renchiiks.spring6restmvcmaven.model.DrinkDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface DrinkService {
    List<DrinkDTO> getAllDrinks();

    Optional<DrinkDTO> getDrinkByUUID(UUID UUID);

    DrinkDTO createDrink(DrinkDTO drink);

    Optional<DrinkDTO> updateDrink(UUID uuid, DrinkDTO drink);

    Boolean deleteDrink(UUID uuid);

    Optional<DrinkDTO> patchDrink(UUID uuid, DrinkDTO drink);


}
