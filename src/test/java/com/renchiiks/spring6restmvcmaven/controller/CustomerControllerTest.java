package com.renchiiks.spring6restmvcmaven.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renchiiks.spring6restmvcmaven.model.Customer;
import com.renchiiks.spring6restmvcmaven.service.CustomerService;
import com.renchiiks.spring6restmvcmaven.service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
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

    @Captor
            ArgumentCaptor<UUID> uuidCaptor;
    @Captor
            ArgumentCaptor<Customer> customerCaptor;

    @BeforeEach
    void setUp() throws Exception {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testPatchCustomer() throws Exception {
        Customer customer = customerServiceImpl.getAllCustomers().getFirst();
        mockMvc.perform(MockMvcRequestBuilders.patch(CustomerController.CUSTOMER_PATH_UUID, customer.getUuid())
                                              .accept(MediaType.APPLICATION_JSON)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(customer)))
               .andExpect(status().isNoContent());

        verify(customerService).patchCustomer(uuidCaptor.capture(), customerCaptor.capture());

        assertThat(customer.getUuid()).isEqualTo(uuidCaptor.getValue());
        assertThat(customer.getVersion()).isEqualTo(customerCaptor.getValue().getVersion());
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Customer customer = customerServiceImpl.getAllCustomers().getFirst();
        mockMvc.perform(MockMvcRequestBuilders.delete(CustomerController.CUSTOMER_PATH_UUID, customer.getUuid())
                                              .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());

        verify(customerService).deleteCustomer(uuidCaptor.capture());

        assertThat(customer.getUuid()).isEqualTo(uuidCaptor.getValue());
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Customer customer = customerServiceImpl.getAllCustomers().getFirst();

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_UUID, customer.getUuid())
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

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH_CREATE)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(customer)))
                                .andExpect(status().isCreated())
                                .andExpect(header().exists("Location"));

    }

    @Test
    void testGetAllCustomers() throws Exception {
        given(customerService.getAllCustomers()).willReturn(customerServiceImpl.getAllCustomers());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ALL)
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

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_UUID, uuid)
                .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.uuid", is(uuid.toString())))
                .andExpect(jsonPath("$.name", is(customer.getName())));
    }


}
