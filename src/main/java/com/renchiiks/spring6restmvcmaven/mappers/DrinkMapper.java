package com.renchiiks.spring6restmvcmaven.mappers;

import com.renchiiks.spring6restmvcmaven.entities.Drink;
import com.renchiiks.spring6restmvcmaven.model.DrinkDTO;
import org.mapstruct.Mapper;

@Mapper
public interface DrinkMapper {

    DrinkDTO drinkToDrinkDTO(Drink drink);

    Drink drinkDTOToDrink(DrinkDTO drinkDTO);
}
