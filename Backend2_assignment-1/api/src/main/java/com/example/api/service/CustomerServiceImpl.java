package com.example.api.service;

import com.example.api.dto.AuthenticationRequest;
import com.example.api.dto.CustomerResponse;
import com.example.api.dto.Mapper;
import com.example.api.error.CustomizedNotFoundException;
import com.example.api.model.Customer;
import com.example.api.repository.CustomerRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record CustomerServiceImpl(CustomerRepository customerRepository,
                                  AuthenticationManager authenticationManager)
        implements CustomerService {

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<CustomerResponse> getAllCustomersResponses() {
        return Mapper.customersToCustomerResponses(getAllCustomers());
    }

    @Override
    public CustomerResponse getCustomerResponseFromCustomerById(Long customerId) throws CustomizedNotFoundException {
        return Mapper.customerToCustomerResponse(getCustomerById(customerId));
    }

    @Override
    public Customer getCustomerById(Long id)
            throws CustomizedNotFoundException {
        return customerRepository.findById(id).orElseThrow(() ->
                new CustomizedNotFoundException(
                        "Customer with id: " + id + " could not be found"));
    }

    @Override
    public String add(Customer c) {
        customerRepository.save(c);
        return "Saved";
    }

    @Override
    public void authenticatingRequest(AuthenticationRequest authenticationRequest) throws CustomizedNotFoundException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new CustomizedNotFoundException("INVALID_CREDENTIALS");
        }
    }
}
