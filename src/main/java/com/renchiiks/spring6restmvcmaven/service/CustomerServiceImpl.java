package com.renchiiks.spring6restmvcmaven.service;

import com.renchiiks.spring6restmvcmaven.model.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final Map<UUID, CustomerDTO> customers;

    public CustomerServiceImpl() {
        this.customers = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                                           .uuid(UUID.randomUUID())
                                           .version(1)
                                           .name("John Doe")
                                           .createTime(LocalDateTime.now())
                                           .updateTime(LocalDateTime.now())
                                           .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                                           .uuid(UUID.randomUUID())
                                           .version(1)
                                           .name("Jane Doe")
                                           .createTime(LocalDateTime.now())
                                           .updateTime(LocalDateTime.now())
                                           .build();

        this.customers.put(customer1.getUuid(), customer1);
        this.customers.put(customer2.getUuid(), customer2);
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.of(this.customers.get(id));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
                return new ArrayList<>(this.customers.values());
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customer) {
        CustomerDTO newCustomer = CustomerDTO.builder()
                                             .uuid(UUID.randomUUID())
                                             .version(1)
                                             .name(customer.getName())
                                             .createTime(LocalDateTime.now())
                                             .updateTime(LocalDateTime.now())
                                             .build();

        this.customers.put(newCustomer.getUuid(), newCustomer);
        return newCustomer;
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(UUID uuid, CustomerDTO customer) {
        CustomerDTO existingCustomer = this.customers.get(uuid);
        existingCustomer.setName(customer.getName());
        existingCustomer.setUpdateTime(LocalDateTime.now());

        this.customers.put(uuid, existingCustomer);
        return Optional.of(existingCustomer);
    }

    @Override
    public Boolean deleteCustomer(UUID uuid) {

        this.customers.remove(uuid);

        return true;
    }

    @Override
    public Optional<CustomerDTO> patchCustomer(UUID uuid, CustomerDTO customer) {
        CustomerDTO existingCustomer = this.customers.get(uuid);

        if(StringUtils.hasText(customer.getName())) {
            existingCustomer.setName(customer.getName());
        }

        if(!Objects.equals(customer.getVersion(), existingCustomer.getVersion())) {
            existingCustomer.setVersion(customer.getVersion());
        }

        return Optional.of(existingCustomer);
    }
}
