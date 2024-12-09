package com.renchiiks.spring6restmvcmaven.service;

import com.renchiiks.spring6restmvcmaven.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CustomerService {
    Optional<Customer> getCustomerById(UUID id);

    List<Customer> getAllCustomers();

    Customer createCustomer(Customer customer);

    void updateCustomer(UUID uuid, Customer customer);

    void deleteCustomer(UUID uuid);

    void patchCustomer(UUID uuid, Customer customer);
}
