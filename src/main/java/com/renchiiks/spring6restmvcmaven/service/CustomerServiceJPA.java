package com.renchiiks.spring6restmvcmaven.service;

import com.renchiiks.spring6restmvcmaven.entities.Customer;
import com.renchiiks.spring6restmvcmaven.mappers.CustomerMapper;
import com.renchiiks.spring6restmvcmaven.model.CustomerDTO;
import com.renchiiks.spring6restmvcmaven.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
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
        return customerMapper.customerToCustomerDTO(
                customerRepository.save(
                        customerMapper.customerDTOToCustomer(customer)));
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(UUID uuid, CustomerDTO customer) {
        Customer customerToUpdate = customerRepository.findById(uuid).orElse(null);
        if (customerToUpdate == null) {
            return Optional.empty();
        }

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setUpdateTime(LocalDateTime.now());
        customerRepository.save(customerToUpdate);

        return Optional.of(customerMapper.customerToCustomerDTO(customerToUpdate));
    }

    @Override
    public Boolean deleteCustomer(UUID uuid) {
        if (customerRepository.existsById(uuid)) {
            customerRepository.deleteById(uuid);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CustomerDTO> patchCustomer(UUID uuid, CustomerDTO customer) {
        Customer customerToUpdate = customerRepository.findById(uuid).orElse(null);
        if (customerToUpdate == null) {
            return Optional.empty();
        }

        if (StringUtils.hasText(customer.getName())) {
            customerToUpdate.setName(customer.getName());
        }

        customerToUpdate.setUpdateTime(LocalDateTime.now());
        customerRepository.save(customerToUpdate);
        return Optional.of(customerMapper.customerToCustomerDTO(customerToUpdate));
    }
}
