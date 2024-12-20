package com.renchiiks.spring6restmvcmaven.controller;

import com.renchiiks.spring6restmvcmaven.model.DrinkDTO;
import com.renchiiks.spring6restmvcmaven.service.DrinkService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity patchDrink(@PathVariable("uuid") UUID uuid, @RequestBody DrinkDTO drink) {
        Optional<DrinkDTO> drinkDTO = drinkService.patchDrink(uuid, drink);
        if (drinkDTO.isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(DRINK_PATH_UUID)
    public ResponseEntity deleteDrink(@PathVariable("uuid") UUID uuid) {

        if (!drinkService.deleteDrink(uuid)) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(DRINK_PATH_UUID)
    public ResponseEntity updateDrink(@PathVariable("uuid") UUID uuid,@Validated @RequestBody DrinkDTO drink) {
        Optional<DrinkDTO> drinkDTO = drinkService.updateDrink(uuid, drink);
        if (drinkDTO.isEmpty()){
            throw new NotFoundException();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/drinks/" + uuid);
        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);

    }

    @PostMapping(DRINK_PATH_CREATE)
    public ResponseEntity createDrink(@Validated @RequestBody DrinkDTO drink) {

        DrinkDTO newDrink = drinkService.createDrink(drink);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/drinks/" + newDrink.getUUID());


        return new ResponseEntity(headers, HttpStatus.CREATED);
    }


    @GetMapping(DRINK_PATH_UUID)
    public DrinkDTO getDrinkByUUID(@PathVariable("uuid") UUID uuid) {
       log.debug("Getting drink by uuid: {} in DrinkController testing devtool", uuid);
        return drinkService.getDrinkByUUID(uuid).orElseThrow(NotFoundException::new);
    }

//    @GetMapping(DRINK_PATH_ALL)
//    public List<DrinkDTO> getAllDrinks() {
//        log.debug("Getting all drinks in DrinkController");
//        return drinkService.getAllDrinks();
//    }

    @GetMapping(DRINK_PATH_ALL)
    public List<DrinkDTO> getAllDrinks(@RequestParam(required = false) String drinkName) {
        //log.debug("Getting all drinks in DrinkController");
        return drinkService.getAllDrinks();
    }

}
