package com.renchiiks.spring6restmvcmaven.repositories;

import com.renchiiks.spring6restmvcmaven.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
