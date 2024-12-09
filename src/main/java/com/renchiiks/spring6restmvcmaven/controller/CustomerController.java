package com.renchiiks.spring6restmvcmaven.controller;

import com.renchiiks.spring6restmvcmaven.model.Customer;
import com.renchiiks.spring6restmvcmaven.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/customers")
@RestController
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PatchMapping("/{uuid}")
    public ResponseEntity patchCustomer(@PathVariable("uuid") UUID uuid, @RequestBody Customer customer) {
        customerService.patchCustomer(uuid, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteCustomer(@PathVariable("uuid") UUID uuid) {
        customerService.deleteCustomer(uuid);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity updateCustomer(@PathVariable("uuid") UUID uuid, @RequestBody Customer customer) {
        customerService.updateCustomer(uuid, customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customers/" + uuid);
        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create")
    public ResponseEntity createCustomer(@RequestBody Customer customer) {
        Customer newCustomer = customerService.createCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customers/" + newCustomer.getUuid());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") UUID id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

}
