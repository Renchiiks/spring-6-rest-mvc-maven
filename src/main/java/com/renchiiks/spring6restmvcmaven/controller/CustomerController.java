package com.renchiiks.spring6restmvcmaven.controller;

import com.renchiiks.spring6restmvcmaven.model.CustomerDTO;
import com.renchiiks.spring6restmvcmaven.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@AllArgsConstructor
public class CustomerController {

    public static final String CUSTOMER_PATH_ALL = "/api/v1/customers";
    public static final String CUSTOMER_PATH_UUID = CUSTOMER_PATH_ALL + "/{uuid}";
    public static final String CUSTOMER_PATH_CREATE = CUSTOMER_PATH_ALL + "/create";


    private final CustomerService customerService;

    @PatchMapping(CUSTOMER_PATH_UUID)
    public ResponseEntity patchCustomer(@PathVariable("uuid") UUID uuid, @RequestBody CustomerDTO customer) {
        customerService.patchCustomer(uuid, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_UUID)
    public ResponseEntity deleteCustomer(@PathVariable("uuid") UUID uuid) {
        customerService.deleteCustomer(uuid);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(CUSTOMER_PATH_UUID)
    public ResponseEntity updateCustomer(@PathVariable("uuid") UUID uuid, @RequestBody CustomerDTO customer) {
        customerService.updateCustomer(uuid, customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customers/" + uuid);
        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

    @PostMapping(CUSTOMER_PATH_CREATE)
    public ResponseEntity createCustomer(@RequestBody CustomerDTO customer) {
        CustomerDTO newCustomer = customerService.createCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customers/" + newCustomer.getUuid());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }



    @GetMapping(CUSTOMER_PATH_UUID)
    public Optional<CustomerDTO> getCustomerById(@PathVariable("uuid") UUID uuid) {
        return Optional.ofNullable(customerService.getCustomerById(uuid).orElseThrow(NotFoundException::new));
    }

    @GetMapping(CUSTOMER_PATH_ALL)
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

}
