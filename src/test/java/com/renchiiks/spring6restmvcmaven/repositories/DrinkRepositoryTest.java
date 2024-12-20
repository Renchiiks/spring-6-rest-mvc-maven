package com.renchiiks.spring6restmvcmaven.repositories;

import com.renchiiks.spring6restmvcmaven.bootstrap.BootstrapData;
import com.renchiiks.spring6restmvcmaven.entities.Drink;
import com.renchiiks.spring6restmvcmaven.model.DrinkStyle;
import com.renchiiks.spring6restmvcmaven.service.DrinkCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({BootstrapData.class, DrinkCsvServiceImpl.class})
class DrinkRepositoryTest {
    @Autowired
    DrinkRepository drinkRepository;

    @Test
    void testGetDrinksByName(){
        List<Drink> list = drinkRepository.findAllByDrinkNameContaining("ang");

        assertThat(list.size()).isGreaterThan(0);
    }

    @Test
    void testSaveDrinkNameTooLong() {

        assertThrows(ConstraintViolationException.class, () -> {
                         Drink drink =
                                 drinkRepository.save(Drink.builder()
                                                                 .drinkName("Waterqwertyuioppasdfghjklzxcvbnm qwertyuiosdfghjkxcvbnmqwertyuiopsdfg")
                                                                 .drinkStyle(DrinkStyle.NON_ALCOHOLIC)
                                                                 .price(BigDecimal.valueOf(1.5))
                                                                 .upc("123456789012")
                                                                 .build());

                         drinkRepository.flush();
                     });
    }

    @Test
    void testSaveDrink() {
        Drink drink = drinkRepository.save(Drink.builder()
                                                .drinkName("Water")
                                                .drinkStyle(DrinkStyle.NON_ALCOHOLIC)
                                                .price(BigDecimal.valueOf(1.5))
                                                .upc("123456789012")
                                                .build());

                                           drinkRepository.flush();

        assertNotNull(drink.getUUID());
        assertThat(drink.getUUID()).isNotNull();
    }
}