package com.renchiiks.spring6restmvcmaven.service;

import com.renchiiks.spring6restmvcmaven.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CustomerService {
    Optional<CustomerDTO> getCustomerById(UUID id);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO createCustomer(CustomerDTO customer);

    void updateCustomer(UUID uuid, CustomerDTO customer);

    void deleteCustomer(UUID uuid);

    void patchCustomer(UUID uuid, CustomerDTO customer);
}
