package com.example.api.controller;

import com.example.api.model.Customer;
import com.example.api.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerRepository mockRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        Customer customer1 = Customer.builder().name("Jamal Robinson").address("Stockholm")
                .username("jamal").password(passwordEncoder.encode("1234")).id(1L).build();
        Customer customer2 = Customer.builder().name("David Beckham").username("david")
                .password(passwordEncoder.encode("1234")).address("Manchester").id(2L).build();
        Customer customer3 = Customer.builder().name("Koke").address("Madrid").username("koke")
                .password(passwordEncoder.encode("1234")).id(3L).build();

        when(mockRepository.findById(1L)).thenReturn(Optional.of(customer1));
        when(mockRepository.findCustomerByUsername("jamal")).thenReturn(customer1);
        when(mockRepository.findAll()).thenReturn(List.of(customer1, customer2, customer3));
    }

    private String token() throws Exception {
        return mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "username": "jamal",
                                            "password": "1234"
                                        }
                                        """
                        ))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @DisplayName("Get all customers with valid token")
    void getAllCustomersTest() throws Exception {
        mockMvc.perform(get("/customers")
                        .header("Authorization", "Bearer " + token())).andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                [{"id":1,"name":"Jamal Robinson","address":"Stockholm","username":"jamal"},
                                {"id":2,"name":"David Beckham","address":"Manchester","username":"david"},
                                {"id":3,"name":"Koke","address":"Madrid","username":"koke"}]
                                """
                ));
    }
    @Test
    @DisplayName("Get all customers with invalid token (can not access)")
    void getAllCustomersTestWithOutJwtToken_Fail() throws Exception {
        mockMvc.perform(get("/customers")).andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Get a customer with valid token and customer id")
    void getCustomerByIdTest() throws Exception {
        mockMvc.perform(get("/customers/1")
                        .header("Authorization", "Bearer " + token())).andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {"id":1,"name":"Jamal Robinson","address":"Stockholm","username":"jamal"}
                                """
                ));
    }

    @Test
    @DisplayName("Add a customer with valid token")
    void addTest() throws Exception {
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name" : "Jamal Robinson",
                                    "address" : "New York"
                                }""")
                        .header("Authorization", "Bearer " + token())).andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Saved")));
    }

}