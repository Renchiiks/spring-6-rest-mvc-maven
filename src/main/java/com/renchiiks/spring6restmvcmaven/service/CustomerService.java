package com.renchiiks.spring6restmvcmaven.service;

import com.renchiiks.spring6restmvcmaven.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CustomerService {
    Optional<CustomerDTO> getCustomerById(UUID id);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO createCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomer(UUID uuid, CustomerDTO customer);

    Boolean deleteCustomer(UUID uuid);

    Optional<CustomerDTO> patchCustomer(UUID uuid, CustomerDTO customer);
}
