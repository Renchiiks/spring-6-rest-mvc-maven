package com.renchiiks.spring6restmvcmaven.controller;

import com.renchiiks.spring6restmvcmaven.model.Drink;
import com.renchiiks.spring6restmvcmaven.service.DrinkService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DrinkController {

    public static final String DRINK_PATH = "/api/v1/drinks";
    public static final String DRINK_PATH_ALL = DRINK_PATH + "/all";
    public static final String DRINK_PATH_UUID = DRINK_PATH + "/{uuid}";
    public static final String DRINK_PATH_CREATE = DRINK_PATH + "/create";


    private final DrinkService drinkService;

    @PatchMapping(DRINK_PATH_UUID)
    public ResponseEntity patchDrink(@PathVariable("uuid") UUID uuid, @RequestBody Drink drink) {
        drinkService.patchDrink(uuid, drink);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(DRINK_PATH_UUID)
    public ResponseEntity deleteDrink(@PathVariable("uuid") UUID uuid) {
        drinkService.deleteDrink(uuid);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(DRINK_PATH_UUID)
    public ResponseEntity updateDrink(@PathVariable("uuid") UUID uuid, @RequestBody Drink drink) {

        drinkService.updateDrink(uuid, drink);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/drinks/" + uuid);
        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);

    }

    @PostMapping(DRINK_PATH_CREATE)
    public ResponseEntity createDrink(@RequestBody Drink drink) {

        Drink newDrink = drinkService.createDrink(drink);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/drinks/" + newDrink.getUUID());


        return new ResponseEntity(headers, HttpStatus.CREATED);
    }



    @GetMapping(DRINK_PATH_UUID)
    public Drink getDrinkByUUID(@PathVariable("uuid") UUID uuid) {
       log.debug("Getting drink by uuid: {} in DrinkController testing devtool", uuid);
        return drinkService.getDrinkByUUID(uuid);
    }

    @GetMapping(DRINK_PATH_ALL)
    public List<Drink> getAllDrinks() {
        log.debug("Getting all drinks in DrinkController");
        return drinkService.getAllDrinks();
    }

}
