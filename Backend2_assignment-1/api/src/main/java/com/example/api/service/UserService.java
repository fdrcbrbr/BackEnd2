package com.example.api.service;

import com.example.api.model.Customer;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Customer saveUser(Customer user);

}
