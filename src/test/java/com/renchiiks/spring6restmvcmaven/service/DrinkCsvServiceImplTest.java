package com.renchiiks.spring6restmvcmaven.service;

import com.renchiiks.spring6restmvcmaven.entities.DrinkCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class DrinkCsvServiceImplTest {

    DrinkCsvService drinkCsvService = new DrinkCsvServiceImpl();

    @Test
    void convertCSV() throws FileNotFoundException{
        File file = ResourceUtils.getFile("classpath:csvdata/all_drinks.csv");

        List<DrinkCSVRecord> recs = drinkCsvService.convertCSV(file);
        System.out.println(recs.size());

        assertThat(recs.size()).isGreaterThan(0);
    }
}
