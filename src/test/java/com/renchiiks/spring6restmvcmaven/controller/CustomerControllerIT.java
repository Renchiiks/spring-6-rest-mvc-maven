package com.renchiiks.spring6restmvcmaven.controller;

import com.renchiiks.spring6restmvcmaven.entities.Customer;
import com.renchiiks.spring6restmvcmaven.mappers.CustomerMapper;
import com.renchiiks.spring6restmvcmaven.model.CustomerDTO;
import com.renchiiks.spring6restmvcmaven.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private CustomerMapper customerMapper;

    @Test
    void testPatchCustomer() {
        Customer customer = customerRepository.findAll().getFirst();

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        customerDTO.setUuid(null);
        customerDTO.setVersion(null);

        final String name = "UPDATED";
        customerDTO.setName(name);

        ResponseEntity responseEntity = customerController.patchCustomer(customer.getUuid(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Customer updatedCustomer = customerRepository.findById(customer.getUuid()).get();
        assertThat(updatedCustomer.getName()).isEqualTo(name);
    }

    @Test
    void testDeleteCustomerNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.deleteCustomer(UUID.randomUUID()));
    }
    @Transactional
    @Rollback
    @Test
    void testDeleteCustomer() {
        Customer customer = customerRepository.findAll().getFirst();

        ResponseEntity response = customerController.deleteCustomer(customer.getUuid());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        assertThat(customerRepository.findById(customer.getUuid())).isEmpty();
    }

    @Test
    void testUpdateCustomerNotFound() {
        assertThrows(NotFoundException.class, () ->
                customerController.updateCustomer(UUID.randomUUID(), CustomerDTO.builder().build()));
    }

    @Test
    void testUpdateCustomer() {
        Customer customer = customerRepository.findAll().getFirst();

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        customerDTO.setUuid(null);
        customerDTO.setVersion(null);

        final String name = "UPDATED";
        customerDTO.setName(name);

        ResponseEntity responseEntity = customerController.updateCustomer(customer.getUuid(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Customer updatedCustomer = customerRepository.findById(customer.getUuid()).get();
        assertThat(updatedCustomer.getName()).isEqualTo(name);
    }

    @Transactional
    @Rollback
    @Test
    void testCreateCustomer() {
        CustomerDTO customer = CustomerDTO.builder()
                                           .name("John")
                                           .build();

        ResponseEntity response = customerController.createCustomer(customer);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = response.getHeaders().getLocation().getPath().split("/");

        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Customer savedCustomer = customerRepository.findById(savedUUID).orElse(null);
        assertThat(savedCustomer).isNotNull();
    }

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