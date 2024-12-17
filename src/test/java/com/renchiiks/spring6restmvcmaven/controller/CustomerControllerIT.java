package com.renchiiks.spring6restmvcmaven.controller;

import com.renchiiks.spring6restmvcmaven.entities.Customer;
import com.renchiiks.spring6restmvcmaven.model.CustomerDTO;
import com.renchiiks.spring6restmvcmaven.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CustomerControllerIT {
    @Autowired
    private CustomerController customerController;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        assertThat(customers.size()).isEqualTo(3);
    }
    @Rollback
    @Transactional
    @Test
    void testGetAllCustomersEmpty() {
        customerRepository.deleteAll();
        List<Customer> customers = customerRepository.findAll();

        assertThat(customers.size()).isEqualTo(0);
    }

    @Test
    void testGetCustomerByUUID() {
        Customer customer = customerRepository.findAll().getFirst();
        Optional<CustomerDTO> customerDTO = customerController.getCustomerById(customer.getUuid());

        assertThat(customerDTO).isNotEmpty();
    }

    @Test
    void testGetCustomerByUUIDNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }

}