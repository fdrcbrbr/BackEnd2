package com.example.api.service;


import com.example.api.error.CustomizedNotFoundException;
import com.example.api.model.Customer;
import com.example.api.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class CustomerServiceImplTest {

    @Autowired
    private CustomerServiceImpl customerService;

    @MockBean
    private CustomerRepository mockRepository;

    @BeforeEach
    void setUp() {
        Customer customer1 = Customer.builder().name("Lennart Skoglund")
                .address("Stockholm")
                .username("lennart")
                .password("1234")
                .id(1L).build();

        Mockito.when(mockRepository.findById(1L)).thenReturn(Optional.of(customer1));
        Mockito.when(mockRepository.findAll()).thenReturn(List.of(customer1));
    }

    @Test
    @DisplayName("Get all customers")
    void getAllCustomers() {
        List<Customer> actual = List.of(Customer.builder().name("Lennart Skoglund")
                .address("Stockholm")
                .username("lennart")
                .password("1234")
                .id(1L).build());
        List<Customer> notActual = List.of(Customer.builder().name("Ali Moteirek")
                .address("Stockholm")
                .username("ali")
                .password("1234")
                .id(1L).build());
        List<Customer> expected = customerService.getAllCustomers();
        assertThat(actual, is(expected));
        assertThat(notActual, is(not(expected)));
        assertThat(actual, hasSize(1));
        assertThat(actual.size(), is(1));
    }

    @Test
    @DisplayName("Get a specific customer with customerid")
    void getCustomerByIdTest() throws CustomizedNotFoundException {
        long id = 1L;
        Customer found = customerService.getCustomerById(id);
        assertEquals(id, found.getId());
    }

}