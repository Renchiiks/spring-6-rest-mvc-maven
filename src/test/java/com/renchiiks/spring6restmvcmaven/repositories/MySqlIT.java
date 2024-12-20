package com.renchiiks.spring6restmvcmaven.repositories;

import com.renchiiks.spring6restmvcmaven.entities.Drink;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ActiveProfiles("localmysql")
public class MySqlIT {
    @Container
    //@ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.22");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
    }
    @Autowired
    DataSource dataSource;
    @Autowired
    DrinkRepository drinkRepository;

    @Test
    public void testGetAllDrinks() {
        List<Drink> drinkList = drinkRepository.findAll();

        assertThat(drinkList.size()).isGreaterThan(2);
    }
}
