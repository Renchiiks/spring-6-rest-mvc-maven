package com.renchiiks.spring6restmvcmaven.service;

import com.renchiiks.spring6restmvcmaven.entities.DrinkCSVRecord;

import java.io.File;
import java.util.List;

public interface DrinkCsvService {
    List<DrinkCSVRecord> convertCSV(File file);
}
