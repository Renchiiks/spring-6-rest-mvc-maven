package com.renchiiks.spring6restmvcmaven.controller;

import com.renchiiks.spring6restmvcmaven.model.Drink;
import com.renchiiks.spring6restmvcmaven.service.DrinkService;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/drinks")
public class DrinkController {

    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteDrink(@PathVariable("uuid") UUID uuid) {
        drinkService.deleteDrink(uuid);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity updateDrink(@PathVariable("uuid") UUID uuid, @RequestBody Drink drink) {

        drinkService.updateDrink(uuid, drink);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/drinks/" + uuid);
        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);

    }

    @PostMapping("/create")
    public ResponseEntity createDrink(@RequestBody Drink drink) {

        Drink newDrink = drinkService.createDrink(drink);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/drinks/" + newDrink.getUUID());


        return new ResponseEntity(headers, HttpStatus.CREATED);
    }


    private final DrinkService drinkService;
    @GetMapping("{uuid}")
    public Drink getDrinkByUUID(@PathVariable("uuid") UUID uuid) {
       log.debug("Getting drink by uuid: {} in DrinkController testing devtool", uuid);
        return drinkService.getDrinkByUUID(uuid);
    }

    @GetMapping("all")
    public List<Drink> getAllDrinks() {
        log.debug("Getting all drinks in DrinkController");
        return drinkService.getAllDrinks();
    }

}
