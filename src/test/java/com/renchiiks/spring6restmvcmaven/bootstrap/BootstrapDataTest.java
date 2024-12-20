package com.renchiiks.spring6restmvcmaven.bootstrap;

import com.renchiiks.spring6restmvcmaven.repositories.CustomerRepository;
import com.renchiiks.spring6restmvcmaven.repositories.DrinkRepository;
import com.renchiiks.spring6restmvcmaven.service.DrinkCsvService;
import com.renchiiks.spring6restmvcmaven.service.DrinkCsvServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@Import(DrinkCsvServiceImpl.class)
class BootstrapDataTest {


    @Autowired
    CustomerRepository customerRepository;

    @Autowired
     DrinkRepository drinkRepository;
@Autowired
    DrinkCsvService drinkCsvService;


    private BootstrapData bootstrapData = new BootstrapData(customerRepository, drinkRepository, drinkCsvService);


    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(customerRepository, drinkRepository, drinkCsvService);
    }


    @Test
    void TestRun() {
        bootstrapData.run((String) null);

        assertThat(customerRepository.count()).isEqualTo(3);
        assertThat(drinkRepository.count()).isGreaterThan(50);
    }
}