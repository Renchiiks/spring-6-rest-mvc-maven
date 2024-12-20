package com.renchiiks.spring6restmvcmaven.bootstrap;

import com.renchiiks.spring6restmvcmaven.entities.Customer;
import com.renchiiks.spring6restmvcmaven.entities.Drink;
import com.renchiiks.spring6restmvcmaven.entities.DrinkCSVRecord;
import com.renchiiks.spring6restmvcmaven.model.DrinkStyle;
import com.renchiiks.spring6restmvcmaven.repositories.CustomerRepository;
import com.renchiiks.spring6restmvcmaven.repositories.DrinkRepository;
import com.renchiiks.spring6restmvcmaven.service.DrinkCsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final DrinkRepository drinkRepository;
    private final DrinkCsvService drinkCSVService;

    @Transactional
    @Override
    public void run(String... args) {
        loadCustomers();
        loadDrinks();
        loadCSVData();
    }


    private void loadCSVData() {

        int min = 12;
        int max = 300;
        int randomNum = (int) (Math.random() * (max - min + 1)) + min;
        if (drinkRepository.count() < 50) {

            File file = null;
            try {
                file = ResourceUtils.getFile("classpath:csvdata/all_drinks.csv");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            //AtomicInteger count = new AtomicInteger();

            List<DrinkCSVRecord> recs = drinkCSVService.convertCSV(file);
            recs.forEach(drinkCSVRecord ->
                             {
                                 DrinkStyle style = switch (drinkCSVRecord.getStrAlcoholic()) {
                                     case "Alcoholic" -> DrinkStyle.ALCOHOLIC;
                                     case "Non alcoholic" -> DrinkStyle.NON_ALCOHOLIC;
                                     default -> DrinkStyle.NO_STYLE;
                                 };
                                 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                 String updateDate = LocalDateTime.parse(drinkCSVRecord.getDateModified(), formatter).toString();

                                 if (updateDate.isEmpty() || updateDate.equals(" ")){
                                     updateDate = LocalDateTime.now().toString();
                                 }
                                 if (updateDate.contains("T")) {
                                     updateDate = updateDate.replace("T", " ");
                                 }
                                 if (updateDate.length()<= 16){
                                     updateDate = updateDate.concat(":00");
                                 }
                                 //count.getAndIncrement();
                                 //System.out.println(count.get());
                                 drinkRepository.save(Drink.builder()
                                                           .drinkName(drinkCSVRecord.getStrDrink())
                                                           .price(BigDecimal.valueOf(10.0))
                                                           .drinkStyle(style)
                                                           .version(1)
                                                           .quantityOnHand(randomNum)
                                                           .upc(drinkCSVRecord.getIdDrink().toString())
                                                           .createTime(LocalDateTime.parse(updateDate, formatter).minusYears(5))

                                                           .updateTime(LocalDateTime.parse(updateDate, formatter))
                                                           .build());
                             });
        }

    }

    private void loadDrinks() {
        Drink tee = Drink.builder()
                         .drinkName("Tee")
                         .drinkStyle(DrinkStyle.NON_ALCOHOLIC)
                         .version(1)
                         .upc("123456789012")
                         .price(BigDecimal.valueOf(1.0))
                         .quantityOnHand(100)
                         .createTime(LocalDateTime.now())
                         .updateTime(LocalDateTime.now())
                         .build();

        Drink coffee = Drink.builder()
                            .drinkName("Coffee")
                            .drinkStyle(DrinkStyle.NON_ALCOHOLIC)
                            .version(1)
                            .upc("123456789013")
                            .price(BigDecimal.valueOf(2.0))
                            .quantityOnHand(200)
                            .createTime(LocalDateTime.now())
                            .updateTime(LocalDateTime.now())
                            .build();

        Drink beer = Drink.builder()
                          .drinkName("Beer")
                          .drinkStyle(DrinkStyle.ALCOHOLIC)
                          .upc("123456789014")
                          .version(1)
                          .price(BigDecimal.valueOf(3.0))
                          .quantityOnHand(300)
                          .createTime(LocalDateTime.now())
                          .updateTime(LocalDateTime.now())
                          .build();

        drinkRepository.save(tee);
        drinkRepository.save(coffee);
        drinkRepository.save(beer);
    }

    private void loadCustomers() {
        Customer customer1 = Customer.builder()
                                     .name("John")
                                     .version(1)
                                     .createTime(LocalDateTime.now())
                                     .updateTime(LocalDateTime.now())
                                     .build();

        Customer customer2 = Customer.builder()
                                     .name("Jane")
                                     .version(1)
                                     .createTime(LocalDateTime.now())
                                     .updateTime(LocalDateTime.now())
                                     .build();

        Customer customer3 = Customer.builder()
                                     .name("Jack")
                                     .version(1)
                                     .createTime(LocalDateTime.now())
                                     .updateTime(LocalDateTime.now())
                                     .build();

        customerRepository.save(customer3);
        customerRepository.save(customer2);
        customerRepository.save(customer1);
    }
}
