package com.renchiiks.spring6restmvcmaven.controller;

import com.renchiiks.spring6restmvcmaven.model.Customer;
import com.renchiiks.spring6restmvcmaven.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/customers")
@RestController
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") UUID id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

}
