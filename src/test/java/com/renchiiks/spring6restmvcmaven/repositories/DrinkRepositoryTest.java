package com.renchiiks.spring6restmvcmaven.repositories;

import com.renchiiks.spring6restmvcmaven.entities.Drink;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class DrinkRepositoryTest {
    @Autowired
 DrinkRepository drinkRepository;

    @Test
    void testSaveDrink() {
        Drink drink = drinkRepository.save(Drink.builder().drinkName("Water").build());

        assertNotNull(drink.getUUID());
        assertThat(drink.getUUID()).isNotNull();
    }
}