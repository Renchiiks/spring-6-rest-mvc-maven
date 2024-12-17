package com.renchiiks.spring6restmvcmaven.bootstrap;

import com.renchiiks.spring6restmvcmaven.repositories.CustomerRepository;
import com.renchiiks.spring6restmvcmaven.repositories.DrinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
@DataJpaTest
class BootstrapDataTest {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DrinkRepository drinkRepository;

    private BootstrapData bootstrapData = new BootstrapData(customerRepository, drinkRepository);


    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(customerRepository, drinkRepository);
    }


    @Test
    void TestRun() {
        bootstrapData.run((String) null);

        assertThat(customerRepository.count()).isEqualTo(3);
        assertThat(drinkRepository.count()).isEqualTo(3);
    }
}