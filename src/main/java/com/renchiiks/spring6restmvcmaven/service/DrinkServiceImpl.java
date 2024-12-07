package com.renchiiks.spring6restmvcmaven.service;

import com.renchiiks.spring6restmvcmaven.model.Drink;
import com.renchiiks.spring6restmvcmaven.model.DrinkStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class DrinkServiceImpl implements DrinkService {
    private final Map<UUID, Drink> drinks;



    public DrinkServiceImpl() {
        this.drinks = new HashMap<>();

        Drink drink1 = Drink.builder()
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

        Drink drink2 = Drink.builder()
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

        Drink drink3 = Drink.builder()
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
    public List<Drink> getAllDrinks() {
        return new ArrayList<>(this.drinks.values());
    }

    @Override
    public Drink getDrinkByUUID(UUID UUID) {
        return this.drinks.get(UUID);
    }

    @Override
    public Drink createDrink(Drink drink) {
        Drink newDrink = Drink.builder()
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
    public void updateDrink(UUID uuid, Drink drink) {
        Drink existingDrink = this.drinks.get(uuid);
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
}
