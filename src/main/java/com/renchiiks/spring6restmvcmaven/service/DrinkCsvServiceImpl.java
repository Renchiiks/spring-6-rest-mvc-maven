package com.renchiiks.spring6restmvcmaven.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.renchiiks.spring6restmvcmaven.entities.DrinkCSVRecord;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
@Service
public//@Import(DrinkServiceImpl.class)
class DrinkCsvServiceImpl implements DrinkCsvService {
    @Override
    public List<DrinkCSVRecord> convertCSV(File file) {

        try {
            return new CsvToBeanBuilder<DrinkCSVRecord>(new FileReader(file))
                    .withType(DrinkCSVRecord.class)
                    .build().parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
