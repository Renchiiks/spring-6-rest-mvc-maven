package com.renchiiks.spring6restmvcmaven.service;

import com.renchiiks.spring6restmvcmaven.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final Map<UUID, Customer> customers;

    public CustomerServiceImpl() {
        this.customers = new HashMap<>();

        Customer customer1 = Customer.builder()
                .uuid(UUID.randomUUID())
                .version(1)
                .name("John Doe")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
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
    public Customer getCustomerById(UUID id) {
        return this.customers.get(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
                return new ArrayList<>(this.customers.values());
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer newCustomer = Customer.builder()
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
    public void updateCustomer(UUID uuid, Customer customer) {
        Customer existingCustomer = this.customers.get(uuid);
        existingCustomer.setName(customer.getName());
        existingCustomer.setUpdateTime(LocalDateTime.now());

        this.customers.put(uuid, existingCustomer);
    }

    @Override
    public void deleteCustomer(UUID uuid) {
        this.customers.remove(uuid);
    }

    @Override
    public void patchCustomer(UUID uuid, Customer customer) {
        Customer existingCustomer = this.customers.get(uuid);

        if(StringUtils.hasText(customer.getName())) {
            existingCustomer.setName(customer.getName());
        }

        if(customer.getVersion() != existingCustomer.getVersion()) {
            existingCustomer.setVersion(customer.getVersion());
        }
    }
}
