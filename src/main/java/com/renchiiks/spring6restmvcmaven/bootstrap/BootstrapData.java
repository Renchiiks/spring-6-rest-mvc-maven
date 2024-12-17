package com.renchiiks.spring6restmvcmaven.bootstrap;

import com.renchiiks.spring6restmvcmaven.entities.Customer;
import com.renchiiks.spring6restmvcmaven.entities.Drink;
import com.renchiiks.spring6restmvcmaven.model.DrinkStyle;
import com.renchiiks.spring6restmvcmaven.repositories.CustomerRepository;
import com.renchiiks.spring6restmvcmaven.repositories.DrinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final DrinkRepository drinkRepository;

    @Override
    public void run(String... args) {
        loadCustomers();
        loadDrinks();
    }

    private void loadDrinks() {
        Drink tee = Drink.builder()
                         .drinkName("Tee")
                         .drinkStyle(DrinkStyle.NON_ALCOHOLIC)
                         .version(1)
                         .upc("123456789012")
                         .price(BigDecimal.valueOf(1.0))
                         .quantityOnHand(100)
                         .createTime(LocalDateTime.now())
                         .updateTime(LocalDateTime.now())
                         .build();

        Drink coffee = Drink.builder()
                            .drinkName("Coffee")
                            .drinkStyle(DrinkStyle.NON_ALCOHOLIC)
                            .version(1)
                            .upc("123456789013")
                            .price(BigDecimal.valueOf(2.0))
                            .quantityOnHand(200)
                            .createTime(LocalDateTime.now())
                            .updateTime(LocalDateTime.now())
                            .build();

        Drink beer = Drink.builder()
                          .drinkName("Beer")
                          .drinkStyle(DrinkStyle.ALCOHOLIC)
                          .upc("123456789014")
                          .version(1)
                          .price(BigDecimal.valueOf(3.0))
                          .quantityOnHand(300)
                          .createTime(LocalDateTime.now())
                          .updateTime(LocalDateTime.now())
                          .build();

        drinkRepository.save(tee);
        drinkRepository.save(coffee);
        drinkRepository.save(beer);
    }

    private void loadCustomers() {
        Customer customer1 = Customer.builder()
                                     .name("John")
                                     .version(1)
                                     .createTime(LocalDateTime.now())
                                     .updateTime(LocalDateTime.now())
                                     .build();

        Customer customer2 = Customer.builder()
                                     .name("Jane")
                                     .version(1)
                                     .createTime(LocalDateTime.now())
                                     .updateTime(LocalDateTime.now())
                                     .build();

        Customer customer3 = Customer.builder()
                                     .name("Jack")
                                     .version(1)
                                     .createTime(LocalDateTime.now())
                                     .updateTime(LocalDateTime.now())
                                     .build();

        customerRepository.save(customer3);
        customerRepository.save(customer2);
        customerRepository.save(customer1);
    }
}
