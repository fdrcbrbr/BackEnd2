package com.example.api.dto;

import com.example.api.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;

class MapperTest {

    private Customer customer;
    private List<Customer> customers;

    @BeforeEach
    void setUp() {
        customer = Customer.builder().name("Lennart Skoglund")
                .address("Stockholm")
                .username("lennart")
                .password("1234")
                .id(1L).build();

        customers = List.of(customer, Customer.builder().name("Ali Moteirek")
                .address("Stockholm")
                .username("ali")
                .password("1234")
                .id(2L).build());
    }

    @Test
    @DisplayName("Customer to CustomerResponse")
    void customerToCustomerResponse() {
        CustomerResponse customerResponse = Mapper.customerToCustomerResponse(customer);

        String actualName = customer.getName();
        String expectedName = customerResponse.getName();
        String actualAddress = customer.getAddress();
        String expectedAddress = customerResponse.getAddress();
        String actualUsername = customer.getUsername();
        String expectedUsername = customerResponse.getUsername();

        assertThat(actualName, is(expectedName));
        assertThat(actualAddress, is(expectedAddress));
        assertThat(actualUsername, is(expectedUsername));
    }

    @Test
    @DisplayName("List of Customer to List of CustomerResponse")
    void customersToCustomerResponses() {
        List<CustomerResponse> customerResponses = Mapper.customersToCustomerResponses(customers);

        assertThat(customers, hasSize(2));
        assertThat(customerResponses, hasSize(2));
        assertThat(customers.size(), is(customerResponses.size()));
    }
}