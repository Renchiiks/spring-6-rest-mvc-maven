package com.renchiiks.spring6restmvcmaven.service;

import com.renchiiks.spring6restmvcmaven.mappers.CustomerMapper;
import com.renchiiks.spring6restmvcmaven.model.CustomerDTO;
import com.renchiiks.spring6restmvcmaven.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(customerMapper.customerToCustomerDTO(customerRepository.findById(id)
                                                                                          .orElse(null)));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::customerToCustomerDTO).toList();
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public void updateCustomer(UUID uuid, CustomerDTO customer) {

    }

    @Override
    public void deleteCustomer(UUID uuid) {

    }

    @Override
    public void patchCustomer(UUID uuid, CustomerDTO customer) {

    }
}
