package com.renchiiks.spring6restmvcmaven.service;

import com.renchiiks.spring6restmvcmaven.model.DrinkDTO;
import com.renchiiks.spring6restmvcmaven.model.DrinkStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class DrinkServiceImpl implements DrinkService {
    private final Map<UUID, DrinkDTO> drinks;



    public DrinkServiceImpl() {
        this.drinks = new HashMap<>();

        DrinkDTO drink1 = DrinkDTO.builder()
                                  .UUID(UUID.randomUUID())
                                  .version(1)
                                  .drinkName("Beer")
                                  .drinkStyle(DrinkStyle.ALCOHOLIC)
                                  .upc("123456789012")
                                  .quantityOnHand(10)
                                  .price(new BigDecimal("10.00"))
                                  .createTime(LocalDateTime.now())
                                  .updateTime(LocalDateTime.now())
                                  .build();

        DrinkDTO drink2 = DrinkDTO.builder()
                                  .UUID(UUID.randomUUID())
                                  .version(1)
                                  .drinkName("Wine")
                                  .drinkStyle(DrinkStyle.NON_ALCOHOLIC)
                                  .upc("123456789")
                                  .quantityOnHand(10)
                                  .price(new BigDecimal("10.00"))
                                  .createTime(LocalDateTime.now())
                                  .updateTime(LocalDateTime.now())
                                  .build();

        DrinkDTO drink3 = DrinkDTO.builder()
                                  .UUID(UUID.randomUUID())
                                  .version(1)
                                  .drinkName("Juice")
                                  .drinkStyle(DrinkStyle.ALCOHOLIC)
                                  .upc("1234567890")
                                  .quantityOnHand(10)
                                  .price(new BigDecimal("10.00"))
                                  .createTime(LocalDateTime.now())
                                  .updateTime(LocalDateTime.now())
                                  .build();

        this.drinks.put(drink1.getUUID(), drink1);
        this.drinks.put(drink2.getUUID(), drink2);
        this.drinks.put(drink3.getUUID(), drink3);
    }

    @Override
    public List<DrinkDTO> getAllDrinks() {
        return new ArrayList<>(this.drinks.values());
    }

    @Override
    public Optional<DrinkDTO> getDrinkByUUID(UUID UUID) {
        return Optional.of(this.drinks.get(UUID));
    }

    @Override
    public DrinkDTO createDrink(DrinkDTO drink) {
        DrinkDTO newDrink = DrinkDTO.builder()
                                    .UUID(UUID.randomUUID())
                                    .version(1)
                                    .drinkName(drink.getDrinkName())
                                    .drinkStyle(drink.getDrinkStyle())
                                    .upc(drink.getUpc())
                                    .quantityOnHand(drink.getQuantityOnHand())
                                    .price(drink.getPrice())
                                    .createTime(LocalDateTime.now())
                                    .updateTime(LocalDateTime.now())
                                    .build();


        this.drinks.put(newDrink.getUUID(), newDrink);
        return newDrink;
    }

    @Override
    public void updateDrink(UUID uuid, DrinkDTO drink) {
        DrinkDTO existingDrink = this.drinks.get(uuid);
        existingDrink.setDrinkName(drink.getDrinkName());
        existingDrink.setDrinkStyle(drink.getDrinkStyle());
        existingDrink.setUpc(drink.getUpc());
        existingDrink.setQuantityOnHand(drink.getQuantityOnHand());
        existingDrink.setPrice(drink.getPrice());
        existingDrink.setUpdateTime(LocalDateTime.now());

        this.drinks.put(uuid, existingDrink);

    }

    @Override
    public void deleteDrink(UUID uuid) {
        this.drinks.remove(uuid);
    }

    @Override
    public void patchDrink(UUID uuid, DrinkDTO drink) {
        DrinkDTO existingDrink = this.drinks.get(uuid);

        if(StringUtils.hasText(drink.getDrinkName())) {
            existingDrink.setDrinkName(drink.getDrinkName());
        }

        if(StringUtils.hasText(drink.getUpc())) {
            existingDrink.setUpc(drink.getUpc());
        }

        if(drink.getQuantityOnHand() != null) {
            existingDrink.setQuantityOnHand(drink.getQuantityOnHand());
        }

        if(drink.getPrice() != null) {
            existingDrink.setPrice(drink.getPrice());
        }

        if(!Objects.equals(drink.getVersion(), existingDrink.getVersion())) {
            existingDrink.setVersion(drink.getVersion());
        }

        existingDrink.setUpdateTime(LocalDateTime.now());
    }
}
