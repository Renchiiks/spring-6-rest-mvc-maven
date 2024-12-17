package com.renchiiks.spring6restmvcmaven.service;

import com.renchiiks.spring6restmvcmaven.entities.Drink;
import com.renchiiks.spring6restmvcmaven.mappers.DrinkMapper;
import com.renchiiks.spring6restmvcmaven.model.DrinkDTO;
import com.renchiiks.spring6restmvcmaven.repositories.DrinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
@Primary
@RequiredArgsConstructor
public class DrinkServiceJPA implements DrinkService {

    private final DrinkRepository drinkRepository;
    private final DrinkMapper drinkMapper;

    @Override
    public List<DrinkDTO> getAllDrinks() {
        return drinkRepository.findAll().stream().map(drinkMapper::drinkToDrinkDTO).toList();
    }

    @Override
    public Optional<DrinkDTO> getDrinkByUUID(UUID UUID) {
        return Optional.ofNullable(drinkMapper.drinkToDrinkDTO(drinkRepository.findById(UUID).orElse(null)));
    }

    @Override
    public DrinkDTO createDrink(DrinkDTO drink) {
        return drinkMapper.drinkToDrinkDTO(drinkRepository.save(drinkMapper.drinkDTOToDrink(drink)));
    }

    @Override
    public Optional<DrinkDTO> updateDrink(UUID uuid, DrinkDTO drink) {
            Drink foundDrink = drinkRepository.findById(uuid).orElse(null);
            if (foundDrink == null) {
                return Optional.empty();
            }
            foundDrink.setDrinkName(drink.getDrinkName());
            foundDrink.setDrinkStyle(drink.getDrinkStyle());
            foundDrink.setUpc(drink.getUpc());
            foundDrink.setPrice(drink.getPrice());
            drinkRepository.save(foundDrink);

        return  Optional.of(drinkMapper.drinkToDrinkDTO(foundDrink));
    }

    @Override
    public Boolean deleteDrink(UUID uuid) {
        if(drinkRepository.existsById(uuid)) {
            drinkRepository.deleteById(uuid);
            return true;
        }
        return false;
    }

    @Override
    public void patchDrink(UUID uuid, DrinkDTO drink) {

    }
}
