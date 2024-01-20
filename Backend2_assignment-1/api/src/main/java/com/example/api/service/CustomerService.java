package com.example.api.service;

import com.example.api.dto.AuthenticationRequest;
import com.example.api.dto.CustomerResponse;
import com.example.api.error.CustomizedNotFoundException;
import com.example.api.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();
    List<CustomerResponse> getAllCustomersResponses();
    Customer getCustomerById(Long id) throws CustomizedNotFoundException;
    CustomerResponse getCustomerResponseFromCustomerById(Long customerId) throws CustomizedNotFoundException;
    String add(Customer c);
    void authenticatingRequest(AuthenticationRequest authenticationRequest) throws CustomizedNotFoundException;
}
