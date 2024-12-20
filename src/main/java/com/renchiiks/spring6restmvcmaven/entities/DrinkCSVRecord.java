package com.renchiiks.spring6restmvcmaven.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrinkCSVRecord {
    @CsvBindByName
    private Integer row;
    @CsvBindByName
    private String strDrink;
    @CsvBindByName
    private String dateModified;
    @CsvBindByName
    private Long idDrink;
    @CsvBindByName
    private String strAlcoholic;
    @CsvBindByName
    private String strCategory;
    @CsvBindByName
    private String strDrinkThumb;
    @CsvBindByName
    private String strGlass;
    @CsvBindByName
    private String strIBA;
    @CsvBindByName
    private String strIngredient1;
    @CsvBindByName
    private String strIngredient10;
    @CsvBindByName
    private String strIngredient11;
    @CsvBindByName
    private String strIngredient12;
    @CsvBindByName
    private String strIngredient13;
    @CsvBindByName
    private String strIngredient14;
    @CsvBindByName
    private String strIngredient15;
    @CsvBindByName
    private String strIngredient2;
    @CsvBindByName
    private String strIngredient3;
    @CsvBindByName
    private String strIngredient4;
    @CsvBindByName
    private String strIngredient5;
    @CsvBindByName
    private String strIngredient6;
    @CsvBindByName
    private String strIngredient7;
    @CsvBindByName
    private String strIngredient8;
    @CsvBindByName
    private String strIngredient9;
    @CsvBindByName
    private String strInstructions;

    @CsvBindByName
    private String strMeasure1;
    @CsvBindByName
    private String strMeasure10;
    @CsvBindByName
    private String strMeasure11;
    @CsvBindByName
    private String strMeasure12;
    @CsvBindByName
    private String strMeasure13;
    @CsvBindByName
    private String strMeasure14;
    @CsvBindByName
    private String strMeasure15;
    @CsvBindByName
    private String strMeasure2;
    @CsvBindByName
    private String strMeasure3;
    @CsvBindByName
    private String strMeasure4;
    @CsvBindByName
    private String strMeasure5;
    @CsvBindByName
    private String strMeasure6;
    @CsvBindByName
    private String strMeasure7;
    @CsvBindByName
    private String strMeasure8;
    @CsvBindByName
    private String strMeasure9;
    @CsvBindByName
    private String strVideo;

}
