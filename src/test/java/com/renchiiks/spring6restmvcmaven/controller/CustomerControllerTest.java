package com.renchiiks.spring6restmvcmaven.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.renchiiks.spring6restmvcmaven.model.Customer;
import com.renchiiks.spring6restmvcmaven.service.CustomerService;
import com.renchiiks.spring6restmvcmaven.service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() throws Exception {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Customer customer = customerServiceImpl.getAllCustomers().getFirst();

        mockMvc.perform(put("/api/v1/customers/{uuid}", customer.getUuid())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomer(any(UUID.class), any(Customer.class));
    }

    @Test
    void testCreateCustomer() throws Exception {

        Customer customer = customerServiceImpl.getAllCustomers().getFirst();

        customer.setUuid(null);
        customer.setVersion(null);

        given(customerService.createCustomer(any(Customer.class)))
                .willReturn(customerServiceImpl.getAllCustomers().get(1));

        mockMvc.perform(post("/api/v1/customers/create")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(customer)))
                                .andExpect(status().isCreated())
                                .andExpect(header().exists("Location"));

    }

    @Test
    void testGetAllCustomers() throws Exception {
        given(customerService.getAllCustomers()).willReturn(customerServiceImpl.getAllCustomers());

        mockMvc.perform(get("/api/v1/customers")
                .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()", is(2)));


    }

    @Test
    void testGetCustomerByUUID() throws Exception {
        Customer customer = customerServiceImpl.getAllCustomers().getFirst();
        UUID uuid = customer.getUuid();

        given(customerService.getCustomerById(uuid)).willReturn(customer);

        mockMvc.perform(get("/api/v1/customers/{uuid}", uuid)
                .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.uuid", is(uuid.toString())))
                .andExpect(jsonPath("$.name", is(customer.getName())));
    }


}
